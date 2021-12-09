package com.tms.advent.year2021;

import com.tms.advent.util.Day;
import com.tms.advent.util.StringUtil;

public class Day2 extends Day<String> {
    public Object part1() {
		int hPos = 0, depth = 0;
		for (String line : input) {
			int value = Integer.parseInt(StringUtil.textAfter(" ", line));
			if (line.startsWith("forward"))
				hPos += value;
			else if (line.startsWith("down"))
				depth += value;
			else
				depth -= value;
		}
		return hPos * depth;
    }
    
    public Object part2() {
		int hPos = 0, depth = 0, aim = 0;
		for (String line : input) {
			int value = Integer.parseInt(StringUtil.textAfter(" ", line));
			if (line.startsWith("forward")) {
				hPos += value;
				depth += aim * value;
			}
			else if (line.startsWith("down"))
				aim += value;
			else
				aim -= value;
		}
		return hPos * depth;
    }
}