package concurrent;

import javax.annotation.concurrent.GuardedBy;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}

public class ExpensiveFunction implements Computable<String, BigInteger> {

    @Override
    public BigInteger compute(String arg) {
        return new BigInteger(arg);
    }
}

class Memoizer1<A,V> implements Computable<A, V> {

    @GuardedBy("this")
    private final Map<A,V> cache = new HashMap<>();
    private final Computable<A,V> c;

    Memoizer1(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
