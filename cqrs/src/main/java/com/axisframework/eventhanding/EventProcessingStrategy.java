package com.axisframework.eventhanding;

import java.util.List;
import java.util.function.Consumer;

/**
 * 该接口描述了批量事件的处理策略
 */
public interface EventProcessingStrategy {

    void handle(List<? extends EventMessage<?>> events, Consumer<List<? extends EventMessage<?>>> processor);
}
