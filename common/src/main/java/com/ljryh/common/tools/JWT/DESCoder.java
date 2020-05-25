package com.ljryh.common.tools.JWT;

import sun.misc.BASE64Decoder;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

import static com.ljryh.common.tools.JWT.DESBuilder.KEY_ALGORTHM;


public class DESCoder {

    private Key toKey(byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORTHM);
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    /**
     * @param key key(Base64编码）
     * @return
     * @throws Exception
     */
    public Key toKey(String key) throws Exception {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        Key keyObj = toKey(keyBytes);
        return keyObj;
    }
}
