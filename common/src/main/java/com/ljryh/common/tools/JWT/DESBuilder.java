package com.ljryh.common.tools.JWT;

import sun.misc.BASE64Encoder;

import javax.crypto.KeyGenerator;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.SecureRandom;


public class DESBuilder {
    /**
     * 加密算法
     */
    public static final String KEY_ALGORTHM = "DES";

    private Key key;

    /**
     * 构造函数.
     *
     * @param str 传入的字符串，根据字符串随机生成Key
     */
    public DESBuilder(String str) {
        generatorRandomKey(str);
    }

    /**
     * @param strKey 通过strKey生成随机Key
     */
    public void generatorRandomKey(String strKey) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(KEY_ALGORTHM);
            generator.init(new SecureRandom(strKey.getBytes()));
            this.key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
    }

    /**
     * 通过strKey生成Key
     * @return Key对象
     */
    public Key getKey() {
        return key;
    }

    /**
     * 通过strKey生成Key
     * @return Key对象
     */
    public String getKeyToString() {
        return (new BASE64Encoder()).encodeBuffer(key.getEncoded());
    }

    /**
     * @param keyAddress
     * 秘钥的存储路径(建议秘钥使用“.bat”后缀）
     * @return 文件
     */
    public void getKeyToFile(String keyAddress) {
        FileOutputStream fileOutput = null;
        ObjectOutputStream objectOutput = null;
        try {
            fileOutput = new FileOutputStream(keyAddress);
            objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(this.key);
            System.out.println("success: " + keyAddress);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutput.close();
                objectOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
