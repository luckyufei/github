package com.facetime.util;

/**
 * 字符串处理工具类
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	/**
	 * 把字符串按填充符从左边补足指定的位数
	 *
	 * @param src
	 *            要填充的字符串
	 * @param ch
	 *            用来填充的字符
	 * @param byteLen
	 *            指定填充后的字符串字节数
	 */
	public static String appendLeft(String src, char ch, int byteLen) {
		StringBuffer sb = new StringBuffer();
		byteLen = byteLen - src.getBytes().length;
		for (int i = 0; i < byteLen; i++) {
			sb.append(ch);
		}
		sb.append(src);
		return sb.toString();
	}

	public static String appendRight(String src, char ch, int byteLen) {
		StringBuffer sb = new StringBuffer();
		sb.append(src);
		byteLen = byteLen - src.getBytes().length;
		for (int i = 0; i < byteLen; i++) {
			sb.append(ch);
		}
		return sb.toString();
	}

	/**
	 * Get binary length of string
	 *
	 * @param inputString
	 * @return
	 */
	public static int bin2int(String inputString) {
		int index;
		int tempLength;
		int returnLength = 0;
		byte[] tempArray;

		if (inputString == null) {
			return returnLength;
		}

		tempArray = inputString.getBytes();
		tempLength = tempArray.length;
		for (index = 0; index < tempLength; returnLength = returnLength * 2 + tempArray[index] - '0', index++) {
			;
		}

		return returnLength;
	}

	/**
	 * Conver byte to binary
	 *
	 * @param inputByte
	 * @return
	 */
	public static String byte2bin(byte inputByte) {
		char lbinDigit[] = { '0', '1' };
		char[] lArray = { lbinDigit[inputByte >> 7 & 0x01],

		lbinDigit[inputByte >> 6 & 0x01], lbinDigit[inputByte >> 5 & 0x01], lbinDigit[inputByte >> 4 & 0x01],
				lbinDigit[inputByte >> 3 & 0x01], lbinDigit[inputByte >> 2 & 0x01], lbinDigit[inputByte >> 1 & 0x01],
				lbinDigit[inputByte & 0x01] };

		return new String(lArray);
	}

	/**
	 * Conver bytes to hex string
	 *
	 * @param inputByte
	 * @return
	 */
	public static String byteToHex(byte inputByte) {

		char hexDigitArray[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		char[] tempArray = { hexDigitArray[inputByte >> 4 & 0x0f], hexDigitArray[inputByte & 0x0f] };

		return new String(tempArray);
	}

	/**
	 * Check if the string is null, if yes return ""
	 *
	 * @param rStr
	 * @return
	 */
	public static String checkNull(String rStr) {
		if (rStr == null) {
			return "";
		} else {
			return rStr.trim();
		}
	}

	/**
	 * Check if the string is null, if yes return ""
	 *
	 * @param rStr
	 * @return
	 */
	public static String checkNull(String rStr, String defaultValue) {
		if (rStr == null || rStr.equals("")) {
			return defaultValue;
		} else {
			return rStr.trim();
		}
	}

	/**
	 * Conver the string from one encoding to another
	 *
	 * @param inputString
	 * @param oldEncode
	 * @param newEncode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String converEncode(String inputString, String oldEncode, String newEncode)
			throws UnsupportedEncodingException {

		String returnString = "";

		if (inputString == null) {
			return returnString;
		}
		try {
			returnString = new String(inputString.getBytes(oldEncode), newEncode);
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
		return returnString;
	}

	/**
	 * 分解字符串，返回String[] 在 delim 参数中的字符是分隔标记的分隔符
	 */
	public final static String[] decomposerString(String str, String delim) {
		String sRst[] = null;
		int j = 0;

		StringTokenizer st = new StringTokenizer(str, delim);
		int i = st.countTokens();
		if (i == 0) {
			return null;
		}
		sRst = new String[i];
		while (st.hasMoreTokens()) {
			sRst[j++] = st.nextToken().trim();
		}
		return sRst;
	}

	/**
	 * 去掉字符串里面跟XML标签冲突的字符
	 *
	 * @param input
	 *            要处理的字符串
	 * @return 替换后的字符串
	 */
	public static String escapeXMLstr(String input) {
		if (input == null) {
			return null;
		}

		StringBuffer output = new StringBuffer();
		int len = input.length();
		for (int i = 0; i < len; i++) {
			char ch = input.charAt(i);
			if (ch == '&') {
				output.append("&amp;");
				continue;
			}
			if (ch == '<') {
				output.append("&lt;");
				continue;
			}
			if (ch == '>') {
				output.append("&gt;");
				continue;
			}
			if (ch == '\'') {
				output.append("&apos;");
				continue;
			}
			if (ch == '"') {
				output.append("&quot;");
			} else {
				output.append(ch);
			}
		}

		return output.toString();
	}

	/**
	 * 限制
	 *
	 * @param label
	 * @param maxLen
	 * @return
	 */
	public static String fitLabel(String label, int maxLen) {
		if (label.length() >= maxLen) {
			return label.substring(0, maxLen) + "..";
		}

		return label;
	}

	/**
	 * Replace the special char in html
	 *
	 * @param inputString
	 * @return
	 */
	public static String fixHTML(String inputString) {

		int index = 0;
		char tempChar;
		StringBuffer stringBuffer;

		if (inputString == null) {
			return "";
		}
		if (inputString.trim().equals("")) {
			return inputString;
		}

		stringBuffer = new StringBuffer(inputString);

		while (index < stringBuffer.length()) {
			if ((tempChar = stringBuffer.charAt(index)) == '"') {
				stringBuffer.replace(index, index + 1, "&#34;");
				index += 5;
				continue;
			}
			if (tempChar == '\'') { // "'"
				stringBuffer.replace(index, index + 1, "&#39;");
				index += 5;
				continue;
			}
			if (tempChar == '&') {
				try {
					if ((tempChar = stringBuffer.charAt(index + 1)) == '#') {
						index += 2;
						continue;
					}
				} catch (StringIndexOutOfBoundsException ex) {
					// needn't to do anything
				}

				stringBuffer.replace(index, index + 1, "&amp;");
				index += 5;
				continue;
			}
			if (tempChar == '<') {
				stringBuffer.replace(index, index + 1, "&lt;");
				index += 4;
				continue;
			}
			if (tempChar == '>') {
				stringBuffer.replace(index, index + 1, "&gt;");
				index += 4;
				continue;
			}
			index++;
		}
		return stringBuffer.toString();
	}

	/**
	 * Conver "'" to "''"
	 *
	 * @param inputString
	 * @return
	 */
	public static String fixSQL(String inputString) {

		int index = 0;
		StringBuffer stringBuffer;

		if (inputString == null) {
			return "";
		}
		if (inputString.trim().equals("")) {
			return inputString;
		}

		stringBuffer = new StringBuffer(inputString);

		while (index < stringBuffer.length()) {
			if (stringBuffer.charAt(index) == '\'') {
				stringBuffer.replace(index, index + 1, "''");
				index += 2;
				continue;
			}
			index++;
		}
		return stringBuffer.toString();
	}

	/**
	 * Replace some special character in the input string
	 *
	 * @param inputString
	 * @return
	 */
	public static String fixWML(String inputString) {
		int index = 0;
		char ch;

		if (inputString == null) {
			return "";
		}
		StringBuffer strbuff = new StringBuffer(inputString);

		while (index < strbuff.length()) {
			if ((ch = strbuff.charAt(index)) == '"') {
				strbuff.replace(index, index + 1, "&#34;");
				index += 5;
				continue;
			}

			if (ch == '\'') {
				strbuff.replace(index, index + 1, "&#39;");
				index += 5;
				continue;
			}

			if (ch == '$') {
				strbuff.replace(index, index + 1, "$$");
				index += 2;
				continue;
			}

			if (ch == '&') {
				strbuff.replace(index, index + 1, "&amp;");
				index += 5;
				continue;
			}

			if (ch == '<') {
				strbuff.replace(index, index + 1, "&lt;");
				index += 4;
				continue;
			}

			if (ch == '>') {
				strbuff.replace(index, index + 1, "&gt;");
				index += 4;
				continue;
			}

			index++;
		}

		return strbuff.toString();
	}

	public static final String formatDecimal(double decimal, String format) {
		java.text.DecimalFormat nf = new java.text.DecimalFormat(format);
		return nf.format(decimal);
	}

	/**
	 * @param strDecimal
	 * @param format
	 *            like ###.### or ###,###.## or $###,###.##
	 * @return
	 */
	public static final String formatDecimal(String strDecimal, String format) {
		java.text.DecimalFormat nf = new java.text.DecimalFormat(format);
		return nf.format(Double.parseDouble(strDecimal));
	}

	// 字符串以;号换行
	public final static String getNewLine(String string) {
		String br = ";<br>";
		StringBuffer sb = new StringBuffer();
		String[] str = StringUtils.split(string, ";");
		for (String element : str) {
			sb.append(element);
			sb.append(br);
		}
		return sb.toString();
	}

	/**
	 * 如果dest参数非空, 则返回dest, 否则返回defaultVal
	 * @param dest
	 * @param defaultVal
	 * @return
	 */
	public static final String getValidStr(String dest, String defaultVal) {
		if (isValid(dest)) {
			return dest.trim();
		}
		return defaultVal;
	}

	/**
	 * 如果dest参数非空, 则返回dest, 否则返回""
	 * @param dest
	 * @return
	 */
	public static final String getValiStr(String dest) {
		return getValidStr(dest, "");
	}

	/**
	 * Check if the string contains chinese
	 *
	 * @param inputString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean hasChinese(String inputString) throws UnsupportedEncodingException {
		byte[] tempArray;
		String tempCode = new String();
		boolean returnFlag = false;

		try {
			// lUniStr = new String(inputString.getBytes(), "big5");
			tempArray = inputString.getBytes("UnicodeBig");
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
		for (int lk = 0; lk < tempArray.length; lk++) {
			if (tempArray[lk] == 0) {
				lk++;
			} else {
				tempCode = byteToHex(tempArray[lk++]) + byteToHex(tempArray[lk]);

				if (!tempCode.equals("feff")) {
					returnFlag = true;
					break;
				}
			}
		}
		return returnFlag;
	}

	/**
	 * Check if the string contains space
	 *
	 * @param inputString
	 * @return
	 */
	public static boolean hasSpace(String inputString) {
		boolean returnFlag = true;

		if (inputString.indexOf(" ") < 0) {
			returnFlag = false;
		}

		return returnFlag;
	}

	/**
	 * Check if the charater is alpha
	 *
	 * @param inputChar
	 * @return
	 */
	public static boolean isAlpha(char inputChar) {
		if (inputChar >= 'a' && inputChar <= 'z') {
			return true;
		}
		if (inputChar >= 'A' && inputChar <= 'Z') {
			return true;
		}

		return false;
	}

	/**
	 * Check if the string consist of alpha
	 *
	 * @param inputString
	 * @return
	 */
	public static boolean isAlpha(String inputString) {
		int counter;

		for (counter = 0; counter < inputString.length(); counter++) {
			if (inputString.charAt(counter) >= 'a' && inputString.charAt(counter) <= 'z') {
				continue;
			}
			if (inputString.charAt(counter) >= 'A' && inputString.charAt(counter) <= 'Z') {
				continue;
			}
			return false;
		}
		return true;
	}

	public static final boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * @parm s 数字形式字符串
	 * @reutrn 如果该字符串表示正确数字 true
	 */
	public static final boolean isNumber(String s) {
		char[] ch = s.toCharArray();
		for (char element : ch) {
			if (element < '0' || element > '9') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if the input string is consist of numeric
	 *
	 * @param inputString
	 * @return
	 */
	public static boolean isNumeric(String inputString) {
		int counter;

		if (inputString == null || inputString.trim().length() == 0) {
			return false;
		}

		for (counter = 0; counter < inputString.length(); counter++) {
			if (!(inputString.charAt(counter) >= '0' && inputString.charAt(counter) <= '9')) {
				return false;
			}
		}
		return true;
	}

	public static final boolean isValid(String str) {
		return notEmpty(str);
	}

	public static final String likeOf(String src) {
		return isValid(src) ? "%" + src + "%" : src;
	}

	public static final boolean notEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * Pad string in lead
	 *
	 * @param padString
	 * @param minLength
	 * @param padChar
	 * @return
	 */
	public static String padLeading(String padString, int minLength, String padChar) {
		String tempPad = "";
		int lCnt = 0;

		String tempString = checkNull(padString);

		if (tempString.length() >= minLength) {
			return tempString;
		} else {
			for (lCnt = 1; lCnt <= minLength - tempString.length(); lCnt++) {
				tempPad = tempPad + padChar;
			}
		}
		return tempPad + tempString;
	}

	/**
	 * Pad string in tail
	 *
	 * @param padString
	 * @param minLength
	 * @param padChar
	 * @return
	 */
	public static String padTrailing(String padString, int minLength, String padChar) {
		String tempPad = "";
		int lCnt = 0;

		String tempString = checkNull(padString);

		if (tempString.length() >= minLength) {
			return tempString;
		} else {
			for (lCnt = 1; lCnt <= minLength - tempString.length(); lCnt++) {
				tempPad = padChar + tempPad;
			}
		}
		return tempString + tempPad;
	}

	public static String removeString(String tmp, String start, String end) {
		int startPosi = -1;
		int endPosi = -1;
		startPosi = tmp.indexOf(start);
		if (startPosi >= 0) {
			endPosi = tmp.indexOf(end, startPosi);
			if (endPosi >= startPosi) {
				tmp = tmp.substring(0, startPosi) + tmp.substring(endPosi + 2);
			}
		}
		return tmp;
	}

	/**
	 * 重复指定字符串
	 *
	 * @param s
	 *            需重复的字符串
	 * @param times
	 *            重复次数
	 * @return
	 */
	public static String repeat(String s, int times) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < times; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * 替换字符串中指定字符串,区分大小写
	 *
	 * @param line
	 *            原字符串
	 * @param oldString
	 *            待替换字符串
	 * @param newString
	 *            更新字符串
	 * @return
	 */
	public static final String replace(String line, String oldString, String newString) {
		if (line == null || oldString == null || newString == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 替换字符串中指定字符串,忽略大小写
	 *
	 * @param line
	 *            原字符串
	 * @param oldString
	 *            待替换字符串
	 * @param newString
	 *            更新字符串
	 * @return
	 */
	public static final String replaceIgnoreCase(String line, String oldString, String newString) {
		if (line == null || oldString == null || newString == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * Replace substring in the input string
	 *
	 * @param inputString
	 * @param beReplaced
	 * @param replaceTo
	 * @return
	 */
	public static String replaceString(String inputString, String beReplaced, String replaceTo) {
		int index = 0;
		String returnString = "";

		returnString = inputString;

		do {
			index = inputString.indexOf(beReplaced, index);

			if (index == -1) {
				break;
			}

			returnString = inputString.substring(0, index) + replaceTo
					+ inputString.substring(index + beReplaced.length());
			index += replaceTo.length();
			inputString = returnString;

		} while (true);

		return returnString.substring(0, returnString.length());
	}

	/**
	 * Split the string according to the delimetr to a vector
	 *
	 * @param inputString
	 * @param delimiter
	 * @return
	 */
	public static List<String> split(String inputString, String delimiter) {
		List<String> returnVector = new ArrayList<String>();
		int position = -1;

		position = inputString.indexOf(delimiter);
		while (position >= 0) {
			if (position > 0) {
				returnVector.add(inputString.substring(0, position));
			}

			inputString = inputString.substring(position + delimiter.length());
			position = inputString.indexOf(delimiter);
		}
		if (!inputString.equals("")) {
			returnVector.add(inputString);
		}
		return returnVector;
	}

	public static String[] splitToArray(String inputString, String delimiter) {
		return split(inputString, delimiter).toArray(new String[] {});
	}

	/**
	 * 打印出exception的出错StackTrace
	 *
	 * @param e
	 *            异常对象
	 * @return e.printStackTrace的字符串内容
	 */
	public static String stackToString(Exception e) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString();
		} catch (Exception e2) {
			return "(bad stack2string)".concat(e.getMessage());
		}
	}

	/**
	 * Cur string according to the length
	 *
	 * @param inputString
	 * @param length
	 * @return
	 */
	public static String subString(String inputString, int length) {

		String tempString = checkNull(inputString);

		if (tempString.length() >= length) {
			return tempString.substring(0, length);
		} else {
			return tempString;
		}
	}

	/**
	 * 将字符串按指定划分符分割成数组 如果不是以划分符结尾，加上划分符,结果数组长度=划分符数 如果要分析的字符串长度=0,返回长度为0的数组
	 *
	 * @param sData
	 *            要分隔的字符串
	 * @param symbol
	 *            分隔符
	 * @return
	 */
	public static final String[] toArray(String sData, String symbol) {
		if (sData == null || symbol == null) {
			return null;
		}
		if (sData.length() == 0) {
			return new String[] { "" };
		}

		int sbLen = symbol.length();

		// 如果不是以结束符结尾，加上结束符
		if (sData.length() < sbLen || !sData.substring(sData.length() - sbLen).equals(symbol)) {
			sData = sData + symbol;
		}

		String sRst[] = null;

		int j = 0;
		int i = sData.indexOf(symbol);
		while (i > -1) {
			j++;
			i += sbLen;
			i = sData.indexOf(symbol, i);
		}
		sRst = new String[j];
		j = 0;
		int k = 0;
		i = sData.indexOf(symbol);
		while (i > -1) {
			sRst[j++] = sData.substring(k, i);
			k = i + sbLen;
			i = sData.indexOf(symbol, k);
		}
		return sRst;
	}

	/**
	 * 转换字符串为整数，待转换字符串必须是数字内容，如"138","-9",而"abc123"为非法
	 *
	 * @param s
	 *            待转换字符串(参数取值在-2147483647到2147483647之间)
	 * @param def
	 *            当s非法时返回默认数字
	 * @return
	 */
	public static int toInt(String s, int def) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 转换字符串为长整型，待转换字符串必须是数字内容，如"138","-9",而"abc123"为非法
	 *
	 * @param s
	 *            待转换字符串
	 * @param def
	 *            当s非法时返回默认数字
	 * @return
	 */
	public static long toLong(String s, long def) {
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 去空格
	 *
	 * @param strObj
	 * @return
	 */
	public static final String trim(Object strObj) {
		if (strObj == null) {
			return "";
		}
		return strObj.toString().trim();
	}

	/**
	 * Trim the string if it 's not null
	 *
	 * @param rStr
	 * @return
	 */
	public static String trim(String rStr) {
		if (rStr == null) {
			return rStr;
		} else {
			return rStr.trim();
		}
	}

	/**
	 * 根据最大长度截取字符串
	 *
	 * @param src
	 *            目标字符串
	 * @param max
	 *            最大长度
	 * @return
	 */
	public static String truncate(String src, int maxLen) {
		if (src == null) {
			return "";
		}
		if (src.length() >= maxLen) {
			return src.substring(0, maxLen);
		}
		return src;
	}
}