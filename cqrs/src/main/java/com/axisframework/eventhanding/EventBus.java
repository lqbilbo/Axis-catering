package com.axisframework.eventhanding;

import com.axisframework.common.Registration;
import com.axisframework.messaging.MessageDispatchInterceptor;

import java.util.Arrays;
import java.util.List;

public interface EventBus {

    /**
     * 批量发布事件消息到event bus上。这些事件会被通知到所有订阅了的listeners
     * @param events
     */
    default void publish(EventMessage<?>... events) { publish(Arrays.asList(events)); }

    void publish(List<? extends EventMessage<?>> events);

    /**
     * 在bus上注册拦截器interceptor,用于将message批量地拦截住
     * @param dispatchInterceptor
     * @return 取消注册的处理器
     */
    Registration registerDispatchInterceptor(MessageDispatchInterceptor<? super EventMessage<?>> dispatchInterceptor);
}
