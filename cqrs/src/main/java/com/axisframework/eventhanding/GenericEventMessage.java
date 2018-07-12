package com.axisframework.eventhanding;

import com.axisframework.messaging.Message;
import com.axisframework.messaging.MessageDecorator;

import java.time.Clock;
import java.time.Instant;
import java.util.Map;
import java.util.function.Supplier;

/**
 * EventMessage接口的通用实现类
 */
public class GenericEventMessage<T> extends MessageDecorator<T> implements EventMessage<T> {
    private static final long serialVersionUID = 4863463881901179803L;
    private final Supplier<Instant> timestampSupplier;

    public static Clock clock = Clock.systemUTC();

    protected GenericEventMessage(Message<T> delegate, Supplier<Instant> timestampSupplier) {
        super(delegate);
        this.timestampSupplier = timestampSupplier;
    }

    @Override
    public Instant getTimestamp() {
        return timestampSupplier.get();
    }

    @Override
    public Message<T> withMetaData(Map<String, ?> metaData) {
        if (getMetaData().equals(metaData)) {
            return this;
        }
        return new GenericEventMessage<>(getDelegate().withMetaData(metaData), timestampSupplier);
    }

    @Override
    public Message<T> andMetaData(Map<String, ?> metaData) {
        if (metaData == null || metaData.isEmpty() || getMetaData().equals(metaData)) {
            return this;
        }
        return new GenericEventMessage<>(getDelegate().andMetaData(metaData), timestampSupplier);
    }

    @Override
    protected void describeTo(StringBuilder stringBuilder) {
        super.describeTo(stringBuilder);
        stringBuilder.append(", timestamp='")
                .append(getTimestamp().toString());
    }

    @Override
    protected String describeType() {
        return "GenericEventMessage";
    }
}
