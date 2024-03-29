package com.ljryh.client.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/4/10 14:16
 */
public class MD5EncryptedNumbersUtils {

    /**
     * 16-》10
     * @param str
     * @return
     */
    private static int tohx16(String str) {
        switch (str) {
            case "0":
                return 0;
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "a":
            case "A":
                return 10;
            case "b":
            case "B":
                return 11;
            case "c":
            case "C":
                return 12;
            case "d":
            case "D":
                return 13;
            case "e":
            case "E":
                return 14;
            case "f":
            case "F":
                return 15;

        }
        return 0;
    }

    /**
     * 转换10进制
     * @param i
     * @return
     */
    private static int to10(int i) {
        String s = i + "";
        int sum = 0;
        for (int j = 0; j < s.length(); j++) {
            char ch = s.charAt(j);
            sum += Integer.parseInt(ch + "");
        }
        return sum >= 10 ? to10(sum) : sum;
    }

    /**
     * 加密数
     * @param dateNow
     * @return
     */
    public static String encryptedNumbers(String dateNow) {
        String pwd = DigestUtils.md5Hex(dateNow);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            String s = pwd.substring(i * 5, (i + 1) * 5);
            int sum = 0;
            for (int j = 0; j < 5; j++) {
                char ch = s.charAt(j);
                sum += tohx16(ch + "");
            }
            sb.append(to10(sum));
        }

        return sb.toString();
    }
    public static void main(String[] args) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            System.out.println(new String(decoder.decode("PHA+5aSn6JCo6L6+5pKS5aSn6JCo6L6+6Zi/6JCo5b636Zi/6JCo5b636Zi/6JCo5b636Zi/6JCo5b63PGltZyBjbGFzcz0id3NjbnBoIiBzcmM9Imh0dHBzOi8vd2VjaGF0LnNhbWljLmNvbS5jbi96dXVsLXBsYXRmb3JtL2ZpbGUvZG93bmxvYWQvMmRlNjgwZWM0M2E2ZjQ4Y2M2ODc2MmE5MGM1YmY2NzIiIC8+6K+36Zeu6K+36Zeu576k57+BPC9wPg=="), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String str1 = new String(Base64.getDecoder().decode("PHA+5aSn6JCo6L6+5pKS5aSn6JCo6L6+6Zi/6JCo5b636Zi/6JCo5b636Zi/6JCo5b636Zi/6JCo5b63PGltZyBjbGFzcz0id3NjbnBoIiBzcmM9Imh0dHBzOi8vd2VjaGF0LnNhbWljLmNvbS5jbi96dXVsLXBsYXRmb3JtL2ZpbGUvZG93bmxvYWQvMmRlNjgwZWM0M2E2ZjQ4Y2M2ODc2MmE5MGM1YmY2NzIiIC8+6K+36Zeu6K+36Zeu576k57+BPC9wPg=="));
        System.out.println(str1);
//        for (int i = 0; i < 20; i++) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            Date date = new Date();
//            Calendar c = Calendar.getInstance();
//            c.setTime(date);
//            c.add(Calendar.YEAR, 1);
//            c.add(Calendar.DAY_OF_MONTH, -i);
//            date = c.getTime();
//            String dateNow = sdf.format(date);
//            System.out.println("日期："+dateNow+"，六位验车码："+encryptedNumbers(dateNow));
//        }
    }

}
