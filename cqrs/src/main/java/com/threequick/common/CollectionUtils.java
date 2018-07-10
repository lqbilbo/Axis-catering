package com.threequick.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

public class CollectionUtils {

    private CollectionUtils() {
    }

    /**
     * 按照给定的集合创建器获取两个集合的交集
     * @param collection1
     * @param collection2
     * @param collectionBuilder
     * @param <T>
     * @param <C>
     * @return
     */
    public static <T, C extends Collection<T>> C intersect(Collection<? extends T> collection1, Collection<? extends T> collection2, Supplier<C> collectionBuilder) {
        C result = collectionBuilder.get();
        HashSet<T> items = new HashSet<>(collection2);
        for (T next : collection1) {
            if (!items.add(next)) {
                result.add(next);
            }
        }
        return result;
    }
}
