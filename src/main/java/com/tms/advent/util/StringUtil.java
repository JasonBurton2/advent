package com.tms.advent.util;

public class StringUtil {
	public static String textBefore(String searchFor, String searchIn) {
		int pos = searchIn.indexOf(searchFor);
		if (pos < 0)
			throw new RuntimeException("[" + searchFor + "] was not found in ]" + searchIn + "]");
		return searchIn.substring(0, pos);
	}

	public static String textAfter(String searchFor, String searchIn) {
		int pos = searchIn.indexOf(searchFor);
		if (pos < 0)
			throw new RuntimeException("[" + searchFor + "] was not found in ]" + searchIn + "]");
		return searchIn.substring(pos + searchFor.length());
	}

	public static String textBetween(String input, String start, String end) {
		int startPos = input.indexOf("[");
		if (startPos < 0)
			throw new RuntimeException("Could not find start " + start + " in " + input);
		int endPos = input.indexOf("]", startPos);
		if (endPos < 0)
			throw new RuntimeException("Could not find end " + end + " in " + input);
		return input.substring(startPos + 1, endPos);
	}

	public static String replaceRange(String mask, String replaceWith, int fromIndex, int toIndex) {
		return mask.substring(0, fromIndex) + replaceWith + mask.substring(fromIndex + 1); 
	}

	public static int[] splitToInts(String line, String splitRegEx) {
		String[] values = line.split(splitRegEx);
		int[] result = new int[values.length];
		for (int i = 0; i < values.length; i++)
			result[i] = Integer.parseInt(values[i]);
		return result;
	}
}
