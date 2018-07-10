package concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionTests {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Collections.synchronizedCollection(list);
    }
}
