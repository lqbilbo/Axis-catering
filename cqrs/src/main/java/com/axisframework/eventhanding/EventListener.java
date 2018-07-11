package com.axisframework.eventhanding;

/**
 * 处理事件的类接口
 */
@FunctionalInterface
public interface EventListener {

    /**
     * 处理给定的事件。不推荐在事件处理过程中抛出异常
     * @param event 处理的事件
     * @throws Exception
     */
    void handle(EventMessage<?> event) throws Exception;

    default boolean canHandle(EventMessage<?> event) { return true; }

    /**
     * 执行由处理器分配的需要重置状态的活动
     */
    default void prepareReset() {
    }

    /**
     * 该处理器是否支持重置
     * @return 如果支持,返回 {@code true},否则返回 {@code false}
     */
    default boolean supportsReset() { return true; }
}
