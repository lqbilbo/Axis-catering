package com.axisframework.messaging;

/**
 * 配置触发回滚的开关
 *
 * @author luoqi
 * @since 1.0
 */
public interface RollbackConfiguration {

    /**
     * 是否触发回滚
     * @param throwable
     * @return
     */
    boolean rollbackOn(Throwable throwable);
}
