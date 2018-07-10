package concurrent;

public class Worker extends Thread {
    MyLatch myLatch;
    public Worker(MyLatch myLatch) {
        this.myLatch = myLatch;
    }

    @Override
    public void run() {
        try{
            System.out.println(Thread.currentThread().getName());
            Thread.sleep((int) (Math.random() * 100));
            myLatch.countDown();
        } catch (InterruptedException e) {

        }
    }

    public static void main(String[] args) throws InterruptedException {
        int workerNum = 100;
        MyLatch myLatch = new MyLatch(workerNum);
        Worker[] workers = new Worker[workerNum];
        for (int i = 0; i < workerNum; i++) {
            workers[i] = new Worker(myLatch);
            workers[i].start();
        }
        myLatch.await();
        System.out.println("collect worker results");
    }
}
