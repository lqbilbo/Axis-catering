package concurrent;

public class Consumer extends Thread {
    MyBlockingQueue<String> queue;
    public Consumer(MyBlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String task = queue.take();
                System.out.println("handle task : " + task);
                Thread.sleep((int) (Math.random() * 100));
            }
        } catch (InterruptedException e) {

        }
    }

    public static void main(String[] args) {
        MyBlockingQueue<String> queue = new MyBlockingQueue<>(10);
        new Producer(queue).start();
        new Consumer(queue).start();
    }
}
