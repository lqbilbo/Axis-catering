package com.axisframework.common.util;

import java.util.function.Supplier;

public class ObjectUtils {

    private ObjectUtils() {
    }

    public static <T> T getOrDefault(T instance, Supplier<T> defaultProvider) {
        if (instance == null) {
            return defaultProvider.get();
        }
        return instance;
    }

    public static <T> T getOrDefault(T instance, T defaultValue) {
        if (instance == null) {
            return defaultValue;
        }
        return instance;
    }

    public static <T extends CharSequence> T getNotEmptyOrDefault(T instance, T defaultValue) {
        if (instance == null || instance.length() == 0) {
            return defaultValue;
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> nullSafeTypeOf(T instance) {
        if (instance == null) {
            return (Class<T>) Void.class;
        }
        return (Class<T>) instance.getClass();
    }

    /**
     * deadline(最终期限)计时器
     * @param deadline
     * @return
     */
    public static long getRemainingOfDeadline(long deadline) {
        long leftTimeout = deadline - System.currentTimeMillis();
        leftTimeout = leftTimeout < 0 ? 0 : leftTimeout;
        return leftTimeout;
    }

}
