package spi;

import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

public class TestStore {

    @Test
    public void test() {

        ServiceLoader<Store> stores = ServiceLoader.load(Store.class);
        Iterator<Store> it = stores.iterator();
        Store s ;
        String data = "datas";
        while (it.hasNext()) {
            s = it.next();
            s.store(data);
        }
    }
}
