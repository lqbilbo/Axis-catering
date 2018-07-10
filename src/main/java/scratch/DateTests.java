package scratch;/*import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.main.concurrent.ThreadLocalRandom;
import java.util.main.concurrent.atomic.AtomicLong;
import java.util.main.concurrent.atomic.LongAdder;*/

import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class DateTests {
    /*public static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };*/

    /*private scratch.DateTests() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        System.out.println(random.nextInt());
    }*/

//    public static scratch.DateTests newInstance() {
//        return new scratch.DateTests();
//    }

    public static void main(String[] args) {
//        scratch.DateTests dateTests = scratch.DateTests.newInstance();

        /*AtomicLong count = new AtomicLong();
        count.addAndGet(1);

        LongAdder longAdder = new LongAdder();
        longAdder.add(1);*/

        MethodType mtToString = MethodType.methodType(String.class);
        MethodType mtSetter = MethodType.methodType(void.class, Object.class);
        MethodType mtStringComparator = MethodType.methodType(int.class, String.class, String.class);
        System.out.println(mtToString.toMethodDescriptorString());



        /*HashMap<Integer, String> hashMap = new HashMap<>(2);
        for (int i=0;i<17;i++) {
            hashMap.put(i, i+"");
        }*/

    }

}
