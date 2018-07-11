package com.axisframework.messaging;

import java.util.Map;

/**
 * 一个Message包含Payload和MetaData。典型的案例是Commands和Events
 * 不要直接实现Message，而是去实现CommandMessage或者EventMessage
 *
 * @param <T> 包含在该Message中的payload类型
 * @author luoqi
 * @see com.axisframework.eventhanding.EventMessage
 * @since 1.0
 */
public interface Message<T> {

    String getIdentifier();

    /**
     * 返回事件中的meta data。meta data是k-v对的集合
     * @return meta data
     */
    MetaData getMetaData();

    /**
     * 返回事件中payload。payload是应用特定信息
     * @return payload
     */
    T getPayload();

    Class<T> getPayloadType();

    /**
     * 创建一个给定{@code metaData}的message。
     * @param metaData
     * @return
     */
    Message<T> withMetaData(Map<String, ?> metaData);

    /**
     * 进行meta data的合并操作。
     * @param metaData
     * @return
     */
    Message<T> andMetaData(Map<String, ?> metaData);
}
