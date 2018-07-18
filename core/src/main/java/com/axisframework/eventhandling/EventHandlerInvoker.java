package com.axisframework.eventhandling;

/**
 * 事件处理器的延迟调用
 *
 * @author luoqi
 * @since 1.0
 */
public interface EventHandlerInvoker {

    boolean canHandle(EventMessage<?> eventMessage);

    void handle(EventMessage<?> message) throws Exception;

    default boolean supportsReset() {
        return true;
    }

    default void performReset() {
    }
}
