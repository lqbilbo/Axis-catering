package com.axisframework.eventhanding;

import java.util.List;

public class ErrorContext {

    private final String eventProcessor;
    private final Exception error;
    private final List<? extends EventMessage<?>> failedEvents;

    public ErrorContext(String eventProcessor, Exception error, List<? extends EventMessage<?>> failedEvents) {
        this.eventProcessor = eventProcessor;
        this.error = error;
        this.failedEvents = failedEvents;
    }

    public String eventProcessor() {
        return eventProcessor;
    }

    public Exception error() {
        return error;
    }

    public List<? extends EventMessage<?>> failedEvents() {
        return failedEvents;
    }
}
