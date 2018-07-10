package nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程版本的Handler
 * 思路就是把耗时的操作（非IO操作）放到其他线程里面跑，
 * 使得Handler只关注与Channel之间的IO操作；
 * Handler快速地从Channel中读或写，可以使Channel及时地、更快地响应其他请求
 * 耗时的操作完成后，产生一个事件（改变state），再通知（由Handler轮询这个状态是否有改变）
 * Handler执行Channel的读写操作
 */
public class HandlerWithThreadPool extends Handler {

    static ExecutorService pool = Executors.newFixedThreadPool(2);
    static final int PROCESSING = 2;

    HandlerWithThreadPool(Selector selector, SocketChannel c) throws IOException {
        super(selector, c);
    }

    //Handler从SocketChannel中读到数据后，把"数据的处理"这个工作扔到线程池里面执行
    void read() throws IOException {
        int readCount = socketChannel.read(input);
        if (readCount > 0) {
            state = PROCESSING;

            //execute是非阻塞的，所以要新增一个state(PROCESSION),表示数据在处理之中，Handler还不能执行send操作
            pool.execute(new Processer(readCount));
        }
        selectionKey.interestOps(SelectionKey.OP_WRITE);
    }

    synchronized void processAndHandOff(int readCount) {
        readProcess(readCount);
        state = SENDING;
    }

    class Processer implements Runnable {

        int readCount;
        Processer(int readCount) {
            this.readCount = readCount;
        }
        @Override
        public void run() {
            processAndHandOff(readCount);
        }
    }
}
