package concurrent;

public class Producer extends Thread {
    MyBlockingQueue<String> queue;
    public Producer(MyBlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            int num = 0;
            while (true) {
                String task = String.valueOf(num);
                queue.put(task);
                System.out.println("produce task : " + task);
                num++;
                Thread.sleep((int) (Math.random() * 100));
            }
        } catch (InterruptedException e) {

        }
    }
}
