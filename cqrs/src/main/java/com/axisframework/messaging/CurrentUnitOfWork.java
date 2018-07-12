package com.axisframework.messaging;

import java.util.Deque;
import java.util.function.Consumer;

/**
 * 进入当前工作单元(UnitOfWork)的入口(entry point)。组件管理的事务边界可以注册或清空UnitOfWork实例。
 *
 * @author luoqi
 * @since 1.0
 */
public abstract class CurrentUnitOfWork {

    private static final ThreadLocal<Deque<UnitOfWork<?>>> CURRENT = new ThreadLocal<>();

    public static boolean isStarted() {
        return CURRENT.get() != null && !CURRENT.get().isEmpty();
    }

    /**
     * 如果UnitOfWork处于激活的状态,则调用给定的 {@code consumer}
     * @param consumer
     * @return
     */
    public static boolean ifStarted(Consumer<UnitOfWork<?>> consumer) {
        if (isStarted()) {
            consumer.accept(get());
            return true;
        }
        return false;
    }

    public static UnitOfWork<?> get() {
        if (isEmpty()) {
            throw new IllegalStateException("该线程无正在运行的工作单元.");
        }
        return CURRENT.get().peek();
    }

    private static boolean isEmpty() {
        Deque<UnitOfWork<?>> unitsOfWork = CURRENT.get();
        return unitsOfWork == null || unitsOfWork.isEmpty();
    }
}
