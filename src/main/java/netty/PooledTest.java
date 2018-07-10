package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

public class PooledTest {

    public static void main(String[] args) {
        int loop = 300000;
        long startTime = System.currentTimeMillis();
        ByteBuf poolBuffer = null;
        for (int i = 0; i < loop; i++) {
            poolBuffer = PooledByteBufAllocator.DEFAULT.directBuffer(1024);
            poolBuffer.writeBytes("hello world".getBytes());
        }
        poolBuffer.release();

        long startTime2 = System.currentTimeMillis();

        System.out.println("The PooledByteBuf execute 300W times writing operation cost time is : " + (startTime2 - startTime) + "ms");
        ByteBuf buffer = null;
        for (int i = 0; i < loop; i++) {
            buffer = Unpooled.directBuffer(1024);
            buffer.writeBytes("hello world".getBytes());
        }

        System.out.println("The UnpooledByteBuf execute 300W times writing operation cost time is : " + (System.currentTimeMillis() - startTime2) + "ms");
    }
}
