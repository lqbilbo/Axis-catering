package com.axisframework.messaging;

public enum RollbackConfigurationType implements RollbackConfiguration {

    NEVER {
        @Override
        public boolean rollbackOn(Throwable throwable) {
            return false;
        }
    },

    ANY_THROWABLE {
        @Override
        public boolean rollbackOn(Throwable throwable) {
            return true;
        }
    },

    UNCHECKED_EXCEPTIONS {
        @Override
        public boolean rollbackOn(Throwable throwable) {
            return !(throwable instanceof Exception) || throwable instanceof RuntimeException;
        }
    },

    RUNTIME_EXCEPTIONS {
        @Override
        public boolean rollbackOn(Throwable throwable) {
            return throwable instanceof RuntimeException;
        }
    }
}
