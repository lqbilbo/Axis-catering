package com.axisframework.common;

import java.util.UUID;

public class DefaultIdentifierFactory extends IdentifierFactory {

    /**
     * {@inheritDoc}
     * <p/>
     * 创建基于pseudo-random UUID的标识
     * @return
     */
    @Override
    protected String generateIdentifier() {
        return UUID.randomUUID().toString();
    }
}
