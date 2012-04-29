package com.facetime.util;

import java.math.BigDecimal;

/**
 * 处理数字加减的类
 *
 * @author Frank
 *
 */
public class NumberUtils {

	private static final int DEF_DIV_SCALE = 2;

	/**
	 *
	 * 提供精确的加法运算。
	 *
	 * @param v1
	 *            被加数
	 *
	 * @param v2
	 *            加数
	 *
	 * @return 两个参数的和
	 *
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 *
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，最多精确到
	 *
	 * 小数点以后10位，默认为小数后２位，以后的数字四舍五入。
	 *
	 * @param v1
	 *            被除数
	 *
	 * @param v2
	 *            除数
	 *
	 * @return 两个参数的商
	 *
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 *
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 *
	 * 定精度，以后的数字四舍五入。
	 *
	 * @param v1
	 *            被除数
	 *
	 * @param v2
	 *            除数
	 *
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 *
	 * @return 两个参数的商
	 *
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 将已知的字符串转换为指定长度的字符串,右补空格
	 *
	 * @param single
	 * @param length
	 * @return
	 */
	public static String getStringToLength(String single, int length) {
		int j = single.getBytes().length;
		for (int i = 0; i < length - j; i++) {
			single = single + " ";
		}
		return single;
	}

	/**
	 *
	 * 提供精确的乘法运算。
	 *
	 * @param v1
	 *            被乘数
	 *
	 * @param v2
	 *            乘数
	 *
	 * @return 两个参数的积
	 *
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 *
	 * 提供精确的小数位四舍五入处理。
	 *
	 * @param v
	 *            需要四舍五入的数字
	 *
	 * @param scale
	 *            小数点后保留几位
	 *
	 * @return 四舍五入后的结果
	 *
	 */

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 将单位数的数字转换为双位数（在前面加零）
	 *
	 * @param single
	 *            单位数
	 * @return 双位数， 如果参数不是单位数返回为null
	 */
	public static String single2Double(int single) {
		if (single > 9) {
			return new Integer(single).toString();
		}
		String monthStr = "";
		if (single < 10) {
			monthStr += "0";
		}
		monthStr += new Integer(single).toString();
		return monthStr;
	}

	/**
	 * 将单位数的数字转换为指定长度的字符(前补零)
	 *
	 * @param single
	 * @param length
	 */
	public static String single2Double(int single, int length) {
		String strSingle = Integer.toString(single);
		int singleLen = strSingle.length();
		for (int i = 0; i < length - singleLen; i++) {
			strSingle = "0" + strSingle;
		}
		return strSingle;
	}

	public static String single2Double(String single, int length) {
		int j = single.length();
		for (int i = 0; i < length - j; i++) {
			single = "0" + single;
		}
		return single;
	}

	/**
	 *
	 * 提供精确的减法运算。
	 *
	 * @param v1
	 *            被减数
	 *
	 * @param v2
	 *            减数
	 *
	 * @return 两个参数的差
	 *
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
}
