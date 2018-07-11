package com.axisframework.common.transaction;

import java.util.function.Supplier;

/**
 * 管理事务的接口
 *
 * @author luoqi
 * @since 1.0
 */
@FunctionalInterface
public interface TransactionManager {

    Transaction startTransaction();

    /**
     * 在新的事务 {@link Transaction} 中执行给定的 {@code task}。
     * 当task正常完成的时候事务提交,抛出异常时进行回滚
     * @param task
     */
    default void executeInTransaction(Runnable task) {
        Transaction transaction = startTransaction();
        try {
            task.run();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    /**
     * {@link #executeInTransaction(Runnable)} 需要返回结果的版本。
     * @param supplier
     * @param <T>
     * @return
     */
    default <T> T fetchInTransaction(Supplier<T> supplier) {
        Transaction transaction = startTransaction();
        try {
            T result = supplier.get();
            transaction.commit();
            return result;
        } catch (Throwable e) {
            transaction.rollback();
            throw e;
        }
    }
}
