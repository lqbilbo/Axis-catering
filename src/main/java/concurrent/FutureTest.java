package concurrent;

import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class FutureTest {

    private static Random rnd = new Random();
    static int delayRandom(int min, int max) {
        int milli = max > min ? rnd.nextInt(max - min) : 0;
        try{
            Thread.sleep(max - min);
        } catch (InterruptedException e) {

        }
        return milli;
    }

    //第一个组件：异步计算任务
    static Callable<Integer> externalTask = () -> {
        int time = delayRandom(20, 2000);
        return time;
    };

    //第二个组件：ExecutorSerivce异步任务执行服务
    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    //第三个组件：执行结果
    public static Future<Integer> callExternalService() {
//        return CompletableFuture.supplyAsync(externalTask, executor);

        return executor.submit(externalTask);
    }

    public static Future<Integer> callExternalService2() {
        FutureTask<Integer> future = new FutureTask<>(externalTask);
        new Thread(() -> future.run()).start();
        return future;
    }

    public static void master() {
        Future<Integer> asyncRet = callExternalService();

        try {
            Integer ret = asyncRet.get();
            System.out.println(ret);
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

        }
    }

    public static void main(String[] args) {
        master();
    }
}
