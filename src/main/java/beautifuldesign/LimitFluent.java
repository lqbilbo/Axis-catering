package beautifuldesign;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class LimitFluent {

    public static void main(String[] args) throws Exception {

        /***
         * 限制某个接口的时间窗请求数

        LoadingCache<Long, AtomicLong> counter = (LoadingCache<Long, AtomicLong>)
                CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(new CacheLoader<Long, AtomicLong>() {
                    @Override
                    public AtomicLong load(Long aLong) throws Exception {
                        return new AtomicLong(0);
                    }
                });
        long limit = 1000;
        while(true) {
            long currentSeconds = System.currentTimeMillis() / 1000;
            if (counter.get(currentSeconds).incrementAndGet() > limit) {
                System.out.println("限流了：" + currentSeconds);
                continue;
            }
        }*/

        RateLimiter limiter = RateLimiter.create(5);
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());

    }

    public Integer call(Callable<Integer> callable) throws Exception {
        return 1;
    }
}
