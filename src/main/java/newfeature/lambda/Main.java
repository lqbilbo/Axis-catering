package newfeature.lambda;

import com.google.common.collect.Lists;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<String> labels = Lists.newArrayList("h1");
        Stream<Button> stream = labels.stream().map(Button::new);
        Button[] buttons = stream.toArray(Button[]::new);

        repeatMessage("Hello", 1000);

    }

    public static synchronized int modifyCount(int count) {
        return count++;
    }

    public static void repeatMessage(String text, int count) {
        Runnable r = () -> {
            int count1 = modifyCount(count);
            for (int i = 0; i < count1; i++) {
                //count++;  因为线程安全问题，count不能被更改
                System.out.println(text);
                Thread.yield();
            }
        };
        new Thread(r).start();
    }




}
