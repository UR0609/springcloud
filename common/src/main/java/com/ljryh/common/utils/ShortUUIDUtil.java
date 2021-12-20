package com.ljryh.common.utils;

import java.util.UUID;

public class ShortUUIDUtil {

    public static String[] chars = new String[]
            {
                    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
            };


    public static String getUuid() {
        StringBuffer stringBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    public static String getShortUuid() {

        StringBuffer stringBuffer = new StringBuffer();

        String uuid = UUID.randomUUID().toString().replace("-", "");

        for (int i = 0; i < 8; i++) {

            String str = uuid.substring(i * 4, i * 4 + 4);

            int strInteger = Integer.parseInt(str, 16);

            stringBuffer.append(chars[strInteger % 0x3E]);

        }


        return stringBuffer.toString();

    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        System.out.println(getUuid());
    }

}
