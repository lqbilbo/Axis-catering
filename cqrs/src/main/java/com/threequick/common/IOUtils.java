package com.threequick.common;

import java.nio.charset.Charset;

/**
 * IO操作的实用方法
 *
 * @author luoqi
 * @since 1.0
 */
public final class IOUtils {

    public static final Charset UTF8 = Charset.forName("UTF-8");

    private IOUtils() {
    }

    public static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {

            }
        }
    }

    public static void closeQuietlyIfCloseable(Object closeable) {
        if (closeable instanceof AutoCloseable) {
            closeQuietly((AutoCloseable) closeable);
        }
    }
}
