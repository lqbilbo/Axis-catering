package com.axisframework.eventhandling;

public interface EventProcessor {

    String getName();

    void start();

    void shutDown();

}
