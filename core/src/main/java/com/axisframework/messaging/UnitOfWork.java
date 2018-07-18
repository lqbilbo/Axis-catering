package com.axisframework.messaging;

import com.axisframework.common.transaction.Transaction;
import com.axisframework.common.transaction.TransactionManager;
import com.axisframework.eventhandling.EventMessage;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 监控消息处理流程的接口类
 */
public interface UnitOfWork<T extends EventMessage<?>> {

    void start();

    void commit();

    default void rollback() {
        rollback(null);
    }

    void rollback(Throwable cause);

    default boolean isActive() {
        return phase().isStarted();
    }

    Phase phase();

    void onPrepareCommit(Consumer<UnitOfWork<T>> handler);

    void onCommit(Consumer<UnitOfWork<T>> handler);

    void afterCommit(Consumer<UnitOfWork<T>> handler);

    void onRollback(Consumer<UnitOfWork<T>> handler);

    void onCleanup(Consumer<UnitOfWork<T>> handler);

    Optional<UnitOfWork<?>> parent();

    default boolean isRoot() {
        return !parent().isPresent();
    }

    @SuppressWarnings("unchecked")
    default UnitOfWork<?> root() {
        return parent().map(UnitOfWork::root).orElse((UnitOfWork) this);
    }

    T getMessage();

    UnitOfWork<T> transformMessage(Function<T, ? extends Message<?>> transformOperator);

    MetaData getCorrelationData();

    Map<String, Object> resources();

    @SuppressWarnings("unchecked")
    default <R> R getResource(String name) {
        return (R) resources().get(name);
    }

    @SuppressWarnings("unchecked")
    default <R> R getOrComputeResource(String key, Function<? super String, R> mappingFunction) {
        return (R) resources().computeIfAbsent(key, mappingFunction);
    }

    @SuppressWarnings("unchecked")
    default <R> R getOrDefaultResource(String key, R defaultValue) {
        return (R) resources().getOrDefault(key, defaultValue);
    }

    default void attachTransaction(TransactionManager transactionManager) {
        try {
            Transaction transaction = transactionManager.startTransaction();
            onCommit(u -> transaction.commit());
            onRollback(u -> transaction.rollback());
        } catch (Throwable t) {
            rollback(t);
            throw t;
        }
    }

    default void execute(Runnable task) {
        execute(task, RollbackConfigurationType.ANY_THROWABLE);
    }

    default void execute(Runnable task, RollbackConfiguration rollbackConfiguration) {
        try {
            executeWithResult(() -> {
                task.run();
                return null;
            }, rollbackConfiguration);
        } catch (Exception e) {
            throw (RuntimeException) e;
        }
    }

    <R> R executeWithResult(Callable<R> task, RollbackConfiguration rollbackConfiguration) throws Exception;

    enum Phase {

        NOT_STARTED(false, false),

        STARTED(true, false),

        PREPARE_COMMIT(true, false),

        COMMIT(true, true),

        ROLLBACK(true, true),

        AFTER_COMMIT(true, true),

        CLEANUP(false, true),

        CLOSED(false, true);

        private final boolean started;
        private final boolean reverseCallbackOrder;

        Phase(boolean started, boolean reverseCallbackOrder) {
            this.started = started;
            this.reverseCallbackOrder = reverseCallbackOrder;
        }

        public boolean isStarted() {
            return started;
        }

        public boolean isReverseCallbackOrder() {
            return reverseCallbackOrder;
        }

        public boolean isBefore(Phase phase) {
            return ordinal() < phase.ordinal();
        }

        public boolean isAfter(Phase phase) {
            return ordinal() > phase.ordinal();
        }
    }

}
