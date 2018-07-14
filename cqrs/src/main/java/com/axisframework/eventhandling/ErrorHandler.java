package com.axisframework.eventhandling;

public interface ErrorHandler {

    void handleError(ErrorContext errorContext) throws Exception;
}
