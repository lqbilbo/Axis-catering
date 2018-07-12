package com.axisframework.messaging;


import com.axisframework.common.Registration;

import java.util.List;
import java.util.function.Consumer;

public interface SubscribableMessageSource<M extends Message<?>> {

    /**
     * 将指定的 {@code messageProcessor} 订阅到该消息源中。
     * 进行订阅之后,它将会收听到发布到这个源上的消息。
     * @param messageProcessor
     * @return 取消 {@code messageProcessor} 的订阅,之后将不再接收到此类消息
     */
    Registration subscribe(Consumer<List<? extends M>> messageProcessor);
}
