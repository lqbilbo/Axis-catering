package com.axisframework.eventhanding;

import com.axisframework.messaging.Message;

import java.time.Instant;
import java.util.Map;

/**
 * 代表一个包装事件的message,通过payload进行标识。事件是应用中的两个组件交互时发生的事件。
 * 它包含在事件中的组件相关的数据信息
 * @param <T> 包含在本条message中的payload类型
 * @author luoqi
 *
 * @since 1.0
 */
public interface EventMessage<T> extends Message<T> {

    @Override
    String getIdentifier();

    @Override
    Message<T> withMetaData(Map<String, ?> metaData);

    @Override
    Message<T> andMetaData(Map<String, ?> metaData);

    /**
     * 返回事件的时间戳。时间戳被设置成日期并表明事件发布的时间
     */
    Instant getTimestamp();
}
