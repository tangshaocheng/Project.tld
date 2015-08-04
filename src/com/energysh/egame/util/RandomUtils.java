package com.energysh.egame.util;

import java.util.Random;

public class RandomUtils {

	private static Random randGen = new Random();
	private static char[] numbersAndLetters = ("0123456789abcdefghijkmnpqrstuvwxyz").toCharArray();;

	private static char[] numbers = ("0123456789").toCharArray();;

	public static synchronized final String randomString(int length) {

		if (length < 1) {
			return null;
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(numbersAndLetters.length)];
		}
		return new String(randBuffer);
	}

	public static synchronized final String randomStringUseNumber(int length) {

		if (length < 1) {
			return null;
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbers[randGen.nextInt(numbers.length)];
		}
		return new String(randBuffer);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			System.out.println(randomString(32));
		}
	}
}
