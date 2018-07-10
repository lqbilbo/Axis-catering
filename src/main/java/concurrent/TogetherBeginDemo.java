package concurrent;

public class TogetherBeginDemo {

    static class FireFlag {
        private volatile boolean fired = false;

        public synchronized void waitForFired() throws InterruptedException {
            while (!fired) {
                wait();
            }
        }

        public synchronized void fired() {
            this.fired = true;
            notifyAll();
        }
    }

    static class Racer extends Thread {
        FireFlag fireFlag;
        public Racer(FireFlag fireFlag) {
            this.fireFlag = fireFlag;
        }

        @Override
        public void run() {
            try {
                this.fireFlag.waitForFired();
                System.out.println("start run " + Thread.currentThread().getName());
            } catch (InterruptedException e) {

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int num = 10;
        FireFlag fireFlag = new FireFlag();
        Racer[] racers = new Racer[num];
        for (int i = 0; i < num; i++) {
            racers[i] = new Racer(fireFlag);
            racers[i].start();
        }
        Thread.sleep(1000);
        fireFlag.fired();
    }
}
