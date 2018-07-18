package com.axisframework.common.util;

/**
 * 反射实用类
 *
 * @author luoqi
 * @since 1.0
 */
public class ReflectionUtils {

    public static Class<?> declaringClass(Class<?> instanceClass, String methodName, Class<?>... parameterTypes) {
        try {
            return instanceClass.getMethod(methodName, parameterTypes).getDeclaringClass();
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private ReflectionUtils() {
    }
}
