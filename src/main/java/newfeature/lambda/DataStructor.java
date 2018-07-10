package newfeature.lambda;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataStructor {

    static Map<Integer, String> sampleMap = Maps.newHashMap();

    static AtomicInteger counter = new AtomicInteger(1);

    static List<Integer> sampleList = Lists.newArrayList();

    static List<String> sampleStrList = Lists.newArrayList("Hello","world","We","get","Ours");

    public static void inputElementsToCollection() {

        while (counter.get() < 10) {
            sampleMap.put(counter.get(), Integer.valueOf(counter.get()).toString());
            sampleList.add(counter.get());
            counter.incrementAndGet();
        }

    }

    public static Stream<Character> characterStream(String s) {
        List<Character> list = Lists.newArrayList();
        for (Character c : s.toCharArray()) list.add(c);
        return list.stream();
    }

    public static void main(String[] args) {

        inputElementsToCollection();
        sampleList.stream().filter(w->w>5).collect(Collectors.toList()).forEach(System.out::println);

        sampleStrList.stream().flatMap(s->characterStream(s)).limit(5).forEach(System.out::println);

    }
}
