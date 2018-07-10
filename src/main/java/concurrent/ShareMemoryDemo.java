package concurrent;

import java.util.ArrayList;
import java.util.List;

public class ShareMemoryDemo {
    private static int shared = 0;
    private static void increShared() {
        shared ++;
    }

    static class ChildThread extends Thread {

        List<String> list;
        public ChildThread(List<String> list) {
            this.list = list;
        }
        @Override
        public void run() {
            increShared();
            list.add(Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<>();
        Thread t1 = new ChildThread(list);
        Thread t2 = new ChildThread(list);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(shared);
        System.out.println(list);

    }

}
