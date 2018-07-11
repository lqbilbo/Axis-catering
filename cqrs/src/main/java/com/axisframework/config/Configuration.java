package com.axisframework.config;

import com.axisframework.eventhanding.EventBus;

import java.util.function.Supplier;

/**
 * 全局配置
 * <p/>
 * 注意:在使用该配置提供的组件之前，确保已调用start方法{@link #start()}进行启动
 */
public interface Configuration {

    default EventBus eventBus() { return getComponent(EventBus.class); }

    default <T> T getComponent(Class<T> componentType) {
        return getComponent(componentType, () -> null);
    }

    <T> T getComponent(Class<T> componentType, Supplier<T> defaultImpl);

    void start();
}
