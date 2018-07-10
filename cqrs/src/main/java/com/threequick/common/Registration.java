package com.threequick.common;

/**
 * 提供了用于取消注册的途径
 */
@FunctionalInterface
public interface Registration extends AutoCloseable {

    /**
     * 通过调用{@link #cancel()}实现处理器的失效
     * <p/>
     * {@inheritDoc}
     */
    @Override
    default void close() { cancel(); }

    /**
     * @return 如果该处理器被成功地失效掉，返回{@code true},
     * 如果该处理器目前没有注册，返回{@code false}
     */
    boolean cancel();

}
