package com.tms.advent.year2020;

import com.tms.advent.util.Day;
import com.tms.advent.util.XString;

public class Day15 extends Day<String> {
	protected Object part1() {
		return computeSpoken(2020);
	}
	
	protected Object part2() {
		return computeSpoken(30000000);
	}
	
	private long computeSpoken(int count) {
		int[] map = new int[count];
		for (int i = 0; i < map.length; i++)
			map[i] = -1;
		int[] values = new XString(input.get(0)).splitInts(",");
		for (int i = 0; i < values.length; i++)
			map[values[i]] = i + 1;
		int next = 0;
		for (int i = values.length + 1; i < count; i++) {
			int spoken = next;
			int prev = map[next];
			next = i - (prev == -1 ? i : prev);
			map[spoken] = i;
		}
		return next;
	}
}