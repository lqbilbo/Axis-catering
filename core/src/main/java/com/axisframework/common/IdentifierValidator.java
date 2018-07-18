package com.axisframework.common;

import java.util.Map;
import java.util.WeakHashMap;

import static com.axisframework.common.util.ReflectionUtils.declaringClass;

/**
 * 对唯一性标识的验证器
 * whiteList: 维护一份通过验证的所有聚合类型的白名单
 *
 * @author luoqi
 * @since 1.0
 */
public final class IdentifierValidator {

    private static final IdentifierValidator INSTANCE = new IdentifierValidator();
    private static final Object NULL = new Object();


    private final Map<Class<?>, Object> whiteList = new WeakHashMap<>();

    private IdentifierValidator() {
    }

    public static IdentifierValidator getInstance() { return INSTANCE; }

    public boolean isValidIdentifier(Class<?> identifierType) {
        if (!whiteList.containsKey(identifierType)) {
            if (Object.class.equals(declaringClass(identifierType, "toString"))) {
                return false;
            }
            whiteList.put(identifierType, NULL);
        }
        return true;
    }
}
