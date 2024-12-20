package com.ljryh.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author ljryh
 * @version 1.0
 * @date 2024/10/16 14:33
 */
public class RsaUtils {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    //已拿到的公钥串
    private static String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCd2+fl3zPgHiZJMj5ypuLz16ZZ59soacHMq5XEsEJkmgq41MdrayH/5hG65UBgYw43vzTxr91cVyVZMm9coNXsd3KRi5UFrpN1a4jjzUNICbLqike1zULak07V+Q2RewVD9rNNv+j3Wt+TBtzJEk2tvBmVET8pNgWxsDBVV6a8GQIDAQAB";

    /**
     * 根据公钥加密
     *
     * @param data
     * @return
     * @throws Exception
     * @exception/throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String encrypt(String data) throws Exception {
        byte[] decode = Base64.decodeBase64(PUBLICKEY);

        PublicKey pubKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decode));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] dataByte = data.getBytes("UTF-8");
        int inputLen = dataByte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(dataByte, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataByte, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();

        String outStr = Base64.encodeBase64String(encryptedData);
        return outStr;
    }

    public static void main(String[] args) throws Exception {
        String encrypt = encrypt("{\n" +
                "  \"username\": \"CVuKyD0178\",\n" +
                "  \"password\": \"QweAsd123!\"\n" +
                "}");
        System.out.println(encrypt);
    }

}
