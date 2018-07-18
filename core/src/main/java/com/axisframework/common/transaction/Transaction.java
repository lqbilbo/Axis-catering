package com.axisframework.common.transaction;

public interface Transaction {

    void commit();

    void rollback();
}
