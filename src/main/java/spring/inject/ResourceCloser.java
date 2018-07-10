package spring.inject;

import org.apache.http.annotation.GuardedBy;
import org.apache.http.annotation.ThreadSafe;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class ResourceCloser implements Runnable {

    @GuardedBy("lock")
    public final List<Closeable> toClose = new ArrayList<>();

    private final Object lock = new Object();

    @Override
    public void run() {
        synchronized (lock) {
            for (Closeable resource : toClose) {
                try {
                    resource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void schedule(Closeable resource) {
        synchronized (lock) {
            toClose.add(resource);
        }
    }
}
