package concurrent;

public class AssemblePoint {
    private int n;
    public AssemblePoint(int n) {
        this.n = n;
    }

    public synchronized void await() throws InterruptedException {
        while(n>0) {
            n--;
            if (n==0) {
                notifyAll();
            } else {
                wait();
            }
        }
    }
}
