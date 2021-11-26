package com.ljryh.svm.utils;

/**
 * @author ljryh
 * @date 2021/11/23 16:53
 */
public class UnicodeUtils {
    public static String wordChangeUnicode(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (19968 <= c && c < 40869) {
                sb.append("\\u" + Integer.toHexString(c));
            } else {
                sb.append(c);
            }

        }
        return sb.toString();
    }
}
