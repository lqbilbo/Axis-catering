package com.threequick.common.transaction;

public enum NoTransactionManager implements TransactionManager {

    /**
     * 事务管理器的单例
     */
    INSTANCE;

    public static TransactionManager instance() { return INSTANCE; }

    private static final Transaction TRANSACTION = new Transaction() {
        @Override
        public void commit() {

        }

        @Override
        public void rollback() {

        }
    };

    @Override
    public Transaction startTransaction() {
        return TRANSACTION;
    }
}
