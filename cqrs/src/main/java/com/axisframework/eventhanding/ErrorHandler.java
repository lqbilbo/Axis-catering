package com.axisframework.eventhanding;

public interface ErrorHandler {

    void handleError(ErrorContext errorContext) throws Exception;
}
