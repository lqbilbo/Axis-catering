package com.axisframework.messaging;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Interceptor允许message可以在他们被发送之前被拦截或修改。
 * interceptor可以实现在任何工作单元(unit work)创建之前修改或拒绝message
 *
 * @param <T> interceptor可以处理的message类型
 * @author luoqi
 * @since 1.0
 */
public interface MessageDispatchInterceptor<T extends Message<?>> {

    /**
     * 每当message准备发送的时候调用。
     * @param message
     * @return 准备发送的message
     */
    default T handle(T message) { return handle(Collections.singletonList(message)).apply(0, message); }

    /**
     * 将给定的message列表设置interceptor。
     * 该方法可以获得一个改动过的message列表
     * @param messages
     * @return 根据message在列表中的位置进行改动后的function
     */
    BiFunction<Integer, T, T> handle(List<? extends T> messages);
}
