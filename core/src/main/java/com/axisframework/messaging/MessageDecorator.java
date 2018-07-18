package com.axisframework.messaging;

import java.io.Serializable;

/**
 * {@link Message} 的通用抽象类,用于代理一个已存在的消息。扩展这个装饰类来将消息赋予额外的特性。
 * @param <T>
 */
public abstract class MessageDecorator<T> implements Message<T>, Serializable {

    private static final long serialVersionUID = -4028503446178792697L;

    private final Message<T> delegate;

    protected MessageDecorator(Message<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getIdentifier() {
        return delegate.getIdentifier();
    }

    @Override
    public MetaData getMetaData() {
        return delegate.getMetaData();
    }

    @Override
    public T getPayload() {
        return delegate.getPayload();
    }

    @Override
    public Class<T> getPayloadType() {
        return delegate.getPayloadType();
    }

    protected Message<T> getDelegate() {
        return delegate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(describeType())
                .append("{");

        describeTo(sb);
        return sb.append("}")
                 .toString();
    }

    protected void describeTo(StringBuilder stringBuilder) {
        stringBuilder.append("payload={")
                     .append(getPayload())
                     .append('}')
                     .append(", metadata={")
                     .append(getMetaData())
                     .append('}')
                     .append(", messageIdentifier='")
                     .append(getIdentifier())
                     .append('\'');
    }

    protected String describeType() {
        return getClass().getSimpleName();
    }
}
