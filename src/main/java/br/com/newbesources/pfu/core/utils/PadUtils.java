package br.com.newbesources.pfu.core.utils;

/**
 * @author gabriel
 * 
 * Oct 11, 2013
 */
public class PadUtils {
	public static String removeLeftPad(String str, String pad) {
		while (str.startsWith(pad)) {
			str = str.replaceFirst(pad, "");
		}
		return str;
	}

	public static String removeRightPad(String str, String pad) {
		while (str.endsWith(pad)) {
			str = str.substring(0, str.lastIndexOf(pad));
		}
		return str;
	}

	public static String leftPad(String str, int size, String pad) {
		StringBuilder builder = new StringBuilder();
		addPad(str, size, pad, builder);
		builder.append(str);
		return builder.substring(0, size);
	}

	public static String rightPad(String str, int size, String pad) {
		StringBuilder builder = new StringBuilder();
		builder.append(str);
		addPad(str, size, pad, builder);
		return builder.substring(0, size);
	}

	public static void addPad(String str, int size, String pad,
			StringBuilder builder) {
		for (int i = 0; i < size - str.length(); i++) {
			builder.append(pad);
		}
	}
}
