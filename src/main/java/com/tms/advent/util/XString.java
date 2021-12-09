package com.tms.advent.util;

public class XString {
	private String wrap;
	
	public XString(String wrap) {
		this.wrap = wrap;
	}
	
	public String textBefore(String searchFor) {
		int pos = wrap.indexOf(searchFor);
		if (pos < 0)
			throw new RuntimeException("[" + searchFor + "] was not found in ]" + wrap + "]");
		return wrap.substring(0, pos);
	}

	public String textAfter(String searchFor) {
		int pos = wrap.indexOf(searchFor);
		if (pos < 0)
			throw new RuntimeException("[" + searchFor + "] was not found in ]" + wrap + "]");
		return wrap.substring(pos + searchFor.length());
	}

	public String textBetween(String start, String end) {
		int startPos = wrap.indexOf(start);
		if (startPos < 0)
			throw new RuntimeException("Could not find start " + start + " in " + wrap);
		int endPos = wrap.indexOf(end, startPos);
		if (endPos < 0)
			throw new RuntimeException("Could not find end " + end + " in " + wrap);
		return wrap.substring(startPos + start.length(), endPos);
	}

	public String replaceRange(int fromIndex, int toIndex, String replaceWith) {
		return wrap.substring(0, fromIndex) + replaceWith + wrap.substring(fromIndex + 1); 
	}

	public int[] splitInts(String splitRegEx) {
			String[] values = wrap.split(splitRegEx);
			int[] result = new int[values.length];
			for (int i = 0; i < values.length; i++) 
				try {
					result[i] = Integer.parseInt(values[i]);
				} catch (NumberFormatException ex) {
					throw new NumberFormatException("Couldn't split line [" + values[i] + "]");
				}
		
			return result;
	}

	public long[] splitLongs(String splitRegEx) {
		String[] values = wrap.split(splitRegEx);
		long[] result = new long[values.length];
		for (int i = 0; i < values.length; i++)
			result[i] = Long.parseLong(values[i]);
		return result;
	}
}
