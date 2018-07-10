package com.threequick.common;

import com.threequick.common.exception.AxisException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 计算哈希值的实用类,基于 {@link java.security.MessageDigest}
 *
 * @author luoqi
 * @since 1.0
 */
public final class Digester {

    private final MessageDigest messageDigest;

    private Digester(MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

    public static Digester newInstance(String algorithm) {
        try {
            return new Digester(MessageDigest.getInstance(algorithm));
        } catch (NoSuchAlgorithmException e) {
            throw new AxisException("该系统不支持此哈希算法", e);
        }
    }

}
