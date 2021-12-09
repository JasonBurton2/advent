package com.tms.advent.year2020;

import com.tms.advent.util.Day;

public class Day3 extends Day<String> {
	protected Object part1() {
		return getTreeCount(3, 1);
	}

	protected Object part2() {
		return getTreeCount(1, 1) * getTreeCount(3, 1) *	getTreeCount(5, 1) * getTreeCount(7, 1) * getTreeCount(1, 2);
	}
	
	private int getTreeCount(int slopeX, int slopeY) {
		int x = 0, treeCount = 0;
		for (int y = 0; y < input.size(); y += slopeY) {
			String line = inputStrings.get(y);
			if (line.charAt(x) == '#')
				treeCount++;
			x += slopeX;
			if (x >= line.length())
				x = x - line.length();
		}
		return treeCount;
	}
}