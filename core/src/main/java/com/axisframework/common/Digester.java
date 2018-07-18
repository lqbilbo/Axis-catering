package com.axisframework.common;

import com.axisframework.common.exception.AxisException;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
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

    public static Digester newMD5Instance() {
        return newInstance("MD5");
    }

    public static String md5Hex(String input) {
        try {
            return newMD5Instance().update(input.getBytes("UTF-8")).digestHex();
        } catch (UnsupportedEncodingException e) {
            throw new AxisException("该系统不支持UTF-8编码", e);
        }
    }

    private Digester update(byte[] additionalData) {
        messageDigest.update(additionalData);
        return this;
    }

    private String digestHex() {
        return hex(messageDigest.digest());
    }

    private static String hex(byte[] hash) {
        return pad(new BigInteger(1, hash).toString(16));
    }

    private static String pad(String md5) {
        if (md5.length() == 32) {
            return md5;
        }
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < 32 - md5.length(); i++) {
            sb.append("0");
        }
        sb.append(md5);
        return sb.toString();
    }

}
