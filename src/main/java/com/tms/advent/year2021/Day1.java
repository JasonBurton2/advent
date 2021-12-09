package com.tms.advent.year2021;

import com.tms.advent.util.Day;
import com.tms.advent.util.ListType;

@ListType(Integer.class)
public class Day1 extends Day<Integer> {
    public Object part1() {
		int increaseCount = 0, lastValue = Integer.MAX_VALUE;
		for (int i : input) {
			if (i > lastValue)
				increaseCount++;
			lastValue = i;
		}
		return increaseCount;
    }
    
    public Object part2() {
		int increaseCount = 0, lastValue = Integer.MAX_VALUE;
		for (int i = 2; i < input.size(); i++) {
			int value = input.get(i) + input.get(i - 1) + input.get(i - 2);
			if (value > lastValue)
				increaseCount++;
			lastValue = value;
		}
    	return increaseCount;
    }
}