package scratch;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Set<Day> weekend = EnumSet.noneOf(Day.class);
        weekend.add(Day.SATURDAY);
        weekend.add(Day.SUNDAY);
        System.out.println(weekend);

        Worker[] workers = new Worker[] {
                new Worker("张三", EnumSet.of(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.FRIDAY)),
                new Worker("李四", EnumSet.of(Day.TUESDAY, Day.THUSDAY, Day.SATURDAY)),
                new Worker("王五", EnumSet.of(Day.TUESDAY, Day.THUSDAY)),
        };

        Arrays.binarySearch(args, args[0]);
        /*Set<scratch.Day> days = EnumSet.allOf(scratch.Day.class);
        Set<scratch.Worker> workerSet = new HashSet<>();
        for (scratch.Worker worker : workers) {
            if (worker.getAvailableDays().containsAll(
                    EnumSet.of(scratch.Day.MONDAY, scratch.Day.TUESDAY)
            )) {
                workerSet.add(worker);
            }
        }*/

        Map<Day, Integer> countMap = new EnumMap<>(Day.class);
        for (Worker worker : workers) {
            for (Day d : worker.getAvailableDays()) {
                Integer count = countMap.get(d);
                countMap.put(d, count==null?1:count+1);
            }
        }

        Set<Day> days = EnumSet.noneOf(Day.class);
        for (Map.Entry<Day, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() >= 2) {
                days.add(entry.getKey());
            }
        }


        System.out.println(days);
    }
}
