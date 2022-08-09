package com.ljryh.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumericUtils {
	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
			'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'W', 'X', 'Y', 'Z' };

	/**
	 * 将十进制的数字转换为指定进制的字符串。
	 * 
	 * @param i 十进制的数字。
	 * @param system 指定的进制，常见的2/8/16。
	 * @return 转换后的字符串。
	 */
	public static String numericToString(int i, int system) {
		long num = 0;
		if (i < 0) {
			num = ((long) 2 * 0x7fffffff) + i + 2;
		} else {
			num = i;
		}
		char[] buf = new char[32];
		int charPos = 32;
		while ((num / system) > 0) {
			buf[--charPos] = digits[(int) (num % system)];
			num /= system;
		}
		buf[--charPos] = digits[(int) (num % system)];
		return new String(buf, charPos, (32 - charPos));
	}

	/**
	 * 将其它进制的数字（字符串形式）转换为十进制的数字。
	 * 
	 * @param s 其它进制的数字（字符串形式）
	 * @param system 指定的进制，常见的2/8/16。
	 * @return 转换后的数字。
	 */
	public int stringToNumeric(String s, int system) {
		char[] buf = new char[s.length()];
		s.getChars(0, s.length(), buf, 0);
		long num = 0;
		for (int i = 0; i < buf.length; i++) {
			for (int j = 0; j < digits.length; j++) {
				if (digits[j] == buf[i]) {
					num += j * Math.pow(system, buf.length - i - 1);
					break;
				}
			}
		}
		return (int) num;
	}
	/**
	 * 字符长度不够自动补0
	 * @param str
	 * @param length
	 * @return
	 */
	public static String formatStr(String str, int length) {
		if (str == null) {
			return null;
		}
		int strLen = str.length();
		if (strLen == length) {
			return str;
		} else if (strLen < length) {
			int temp = length - strLen;
			String tem = "";
			for (int i = 0; i < temp; i++) {
				tem = "0" + tem;
			}
			return tem + str;
		} else {
			return str.substring(0, length);
		}
	}

	/**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
           Pattern pattern = Pattern.compile("[0-9]*");
           Matcher isNum = pattern.matcher(str);
           if( !isNum.matches() ){
               return false;
           }
           return true;
    }
 
    
	public static void main(String[] args) {
		NumericUtils de = new NumericUtils();
		System.out.println(de.stringToNumeric("0A", 32));
		System.out.println(de.stringToNumeric("00A", 32));
		System.out.println(de.stringToNumeric("0000A", 32));
		System.out.println(de.stringToNumeric("00000A", 32));
		//取到主键ID 直接转成32进制 ，长度不够前方补0
	//	System.out.println(de.numericToString(10, 32));
	}
}
