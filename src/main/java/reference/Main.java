package reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class Main {

    public static void main(String[] args) {
        Object counter = new Object();

        ReferenceQueue refQueue = new ReferenceQueue<>();

        PhantomReference<Object> p = new PhantomReference<>(counter, refQueue);

        counter = null;

        System.gc();

        try {
            Reference<Object> ref = refQueue.remove(1000L);

            if (ref!=null) {
                //do something
            }
        } catch (InterruptedException e) {
            //handle it
        }
    }
}
