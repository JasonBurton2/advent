package com.tms.advent.year2020;

import java.util.Collections;
import com.tms.advent.util.Day;
import com.tms.advent.util.ListType;

@ListType(Integer.class)
public class Day10 extends Day<Integer> {
	protected void makeInputList() {
		super.makeInputList();
		input.add(0);
		Collections.sort(input);
		input.add(input.get(input.size() - 1) + 3);
	}
	
	protected Object part1() {
		int[] diffs = new int[4];
		loop(1, (i) -> diffs[input.get(i) - input.get(i - 1)]++);
		return diffs[1] * diffs[3];
	}

	protected Object part2() {
		long[] pathsToHere = new long[input.size()];
		pathsToHere[0] = 1;
		for (int i = 0; i < input.size(); i++)
			for (int j = i + 1; j < input.size(); j++)
				if (input.get(j) - input.get(i) <= 3)
					pathsToHere[j] += pathsToHere[i];
		return pathsToHere[pathsToHere.length - 1];
	}
}