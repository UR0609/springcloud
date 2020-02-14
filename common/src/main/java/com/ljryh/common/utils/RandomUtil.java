package com.ljryh.common.utils;

import java.util.Random;

public class RandomUtil {
	private static Random strGen = new Random();;
	private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
			.toCharArray();

	/** * 产生随机字符串 * */
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[strGen.nextInt(61)];
		}
		return new String(randBuffer).toLowerCase();
	}
}