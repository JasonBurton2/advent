package com.tms.advent.year2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tms.advent.util.Day;
import com.tms.advent.util.StringUtil;

public class Day8 extends Day<String> {
	private List<Entry> entries = new ArrayList<>();

	@Override
	protected void onRun() {
		for (String line : input)
			entries.add(new Entry(line));
	}
	
	public Object part1() {
		int uniqueDigitCount = 0;
		for (Entry entry : entries)
			for (String s : entry.outputValues)
				if (s.length() != 5 && s.length() != 6)
					uniqueDigitCount++;
		return uniqueDigitCount;
    }
    
    public Object part2() {
		int result = 0;
		for (Entry entry : entries) {
			String numStr = "";
			for (String digitStr : entry.outputValues)
				numStr += entry.translation.get(digitStr);
			result += Integer.parseInt(numStr);
		}

		return result;
    }

	private static class Entry {
		private String[] inputValues, outputValues;
		@SuppressWarnings("unchecked")
		private Set<String>[] digitPossibilities = new Set[10];
		Map<String, Integer> translation = new HashMap<>();

		public Entry(String line) {
			inputValues = sortValues(StringUtil.textBefore("|", line));
			outputValues = sortValues(StringUtil.textAfter("|", line));
			for (int i = 0; i < digitPossibilities.length; i++)
				digitPossibilities[i] = new HashSet<>();
			for (String value : inputValues) {
				int len = value.length();
				if (len == 2)
					digitPossibilities[1].add(value);
				else if (len == 3)
					digitPossibilities[7].add(value);
				else if (len == 4)
					digitPossibilities[4].add(value);
				else if (len == 5) {
					digitPossibilities[2].add(value);
					digitPossibilities[3].add(value);
					digitPossibilities[5].add(value);
				}
				else if (len == 6) {
					digitPossibilities[0].add(value);
					digitPossibilities[6].add(value);
					digitPossibilities[9].add(value);
				}
				else if (len == 7)
					digitPossibilities[8].add(value);
			}
			String oneValue = digitPossibilities[1].iterator().next();
			String oneFourDiff = findMissing(oneValue, digitPossibilities[4].iterator().next());
			digitPossibilities[0].removeIf(poss -> containsAll(poss, oneFourDiff)); // zero is the 6-segment value without the middle bar that four has but 
			digitPossibilities[6].removeIf(poss -> containsAll(poss, oneValue) || digitPossibilities[0].contains(poss)); // six is the 6-segment value that doesn't have the segments from 1
			digitPossibilities[9].removeIf(poss -> digitPossibilities[0].contains(poss) || digitPossibilities[6].contains(poss)); // nine is the remaing 6-segement
			digitPossibilities[3].removeIf(poss -> !containsAll(poss, oneValue)); // three is the 5-segment value that has both from 1
			digitPossibilities[2].removeIf(poss -> digitPossibilities[3].contains(poss) || findMissing(poss, digitPossibilities[9].iterator().next()).length() != 2); // two is the 5-segment that is missing 2 segments from 9
			digitPossibilities[5].removeIf(poss -> digitPossibilities[3].contains(poss) || digitPossibilities[2].contains(poss)); // five is the remaing 5-segement
			for (int i = 0; i < digitPossibilities.length; i++) {
				if (digitPossibilities[i].size() != 1)
					throw new RuntimeException();
				translation.put(digitPossibilities[i].iterator().next(), i);
			}
		}

		private String findMissing(String from, String in) {
			String result = "";
			for (int i = 0; i < in.length(); i++)
				if (!from.contains(in.substring(i, i + 1)))
					result += in.charAt(i);
			return result;
		}

		private boolean containsAll(String searchIn, String searchFor) {
			return findMissing(searchIn, searchFor).length() == 0;
		}

		private String[] sortValues(String values) {
			String[] split = values.trim().split(" ");
			for (int i = 0; i < split.length; i++) {
				char[] chars = split[i].toCharArray();
				Arrays.sort(chars);
				split[i] = new String(chars);
			}
			return split;
		}
	}
}