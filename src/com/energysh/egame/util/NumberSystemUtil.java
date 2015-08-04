package com.energysh.egame.util;

import java.math.BigInteger;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 数进制转换
 * 
 * @author rocker
 * 
 */
public class NumberSystemUtil {

	/**
	 * 任意进制转换为十进制
	 * 
	 * @param input
	 *            ：数据源
	 * @param base
	 *            ：数据源进制
	 * @return
	 */
	public static String any2Ten(String input, int base) {
		if (MyUtil.getInstance().isBlank(input))
			return null;
		BigInteger result = BigInteger.ZERO, temp = BigInteger.ONE;
		int len = input.length();
		for (int i = len - 1; i >= 0; i--) {
			if (i != len - 1)
				temp = temp.multiply(BigInteger.valueOf(base));
			int num = changeDec(input.charAt(i));
			result = result.add(temp.multiply(BigInteger.valueOf(num)));
		}
		return result.toString();
	}

	/**
	 * 十进制转换为任意进制
	 * 
	 * @param input
	 *            ：数据源
	 * @param base
	 *            ：数据源进制
	 * @return
	 */
	public static String ten2Any(String input, int base) {
		MyUtil mu = MyUtil.getInstance();
		if (mu.isBlank(input))
			return null;
		BigInteger bigInput = BigInteger.valueOf(mu.toLong(input));
		BigInteger bigBase = BigInteger.valueOf(base);
		String result = "";
		while (bigInput.compareTo(BigInteger.ZERO) != 0) {
			BigInteger temp = bigInput.mod(bigBase);
			bigInput = bigInput.divide(bigBase);
			char ch = changToNum(temp);
			result = ch + result;
		}
		return result;
	}

	/**
	 * 任意转换为任意
	 * 
	 * @param input
	 *            ：数据源
	 * @param scouceBase
	 *            ：数据源进制
	 * @param targetBase
	 *            ：目标数进制
	 */
	public static String any2Any(String input, int scouceBase, int targetBase) {
		if (MyUtil.getInstance().isBlank(input))
			return null;
		String bigtemp = any2Ten(input, scouceBase);
		String result = ten2Any(bigtemp, targetBase);
		return result;
	}

	/**
	 * 数字转换为字符
	 * 
	 * @param bigInt
	 * @return
	 */
	private static char changToNum(BigInteger bigInt) {
		int n = bigInt.intValue();
		if (n >= 10 && n <= 35)
			return (char) (n - 10 + 'A');
		else if (n >= 36 && n <= 61)
			return (char) (n - 36 + 'a');
		else
			return (char) (n + '0');
	}

	/**
	 * 十进制转换中把字符转换为数
	 * 
	 * @param ch
	 * @return
	 */
	private static int changeDec(char ch) {
		int num = 0;
		if (ch >= 'A' && ch <= 'Z')
			num = ch - 'A' + 10;
		else if (ch >= 'a' && ch <= 'z')
			num = ch - 'a' + 36;
		else
			num = ch - '0';
		return num;
	}

	public static void main(String[] args) throws ParseException, SocketException {
		long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-01-18 20:00:01").getTime();
		// String x = time + "13625467894";
		System.out.println(ten2Any(time + "", 36));
		System.out.println(ten2Any("13625467894", 36));
		System.out.println(ten2Any(time + "", 36) + ten2Any("13625467894", 36));
		// System.out.println(ten2Any(x, 36));
		// System.out.println(MD5Encoder.encode(x.getBytes()));
	}
}
