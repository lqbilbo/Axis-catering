package com.threequick.common.transaction;

public interface Transaction {

    void commit();

    void rollback();
}
