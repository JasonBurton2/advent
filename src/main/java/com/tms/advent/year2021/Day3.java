package com.tms.advent.year2021;

import java.util.ArrayList;
import java.util.List;

import com.tms.advent.util.Day;

public class Day3 extends Day<String> {
    public Object part1() {
		int[] zeroCounts = getZeroCounts(input);
		String gamma = "", epsilon = "";
		for (int count : zeroCounts) {
			gamma += count > input.size() / 2 ? "0" : "1";
			epsilon += count > input.size() / 2 ? "1" : "0";
		}
		return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2);
    }
    
    public Object part2() {
		String[] values = {"", ""};
		for (int oxygenRate = 0; oxygenRate < 2; oxygenRate++) {
			List<String> lines = new ArrayList<>(input);
			for (int bitIndex = 0; bitIndex < input.get(0).length(); bitIndex++) {
				filter(lines, bitIndex, oxygenRate == 0);
				if (lines.size() == 1)
					break;
			}
			values[oxygenRate] = lines.get(0);
		}
		return Integer.parseInt(values[0], 2) * Integer.parseInt(values[1], 2);
    }

	private void filter(List<String> lines, int bitIndex, boolean mostCommon) {
		int zeroCount = getZeroCounts(lines)[bitIndex];
		char keep;
		if (zeroCount > lines.size() / 2)
			keep = mostCommon ? '0' : '1';
		else 
			keep = mostCommon ? '1' : '0';
		for (int i = lines.size() - 1; i >= 0 && lines.size() > 1; i--)
			if (lines.get(i).charAt(bitIndex) != keep)
				lines.remove(i);
	}

	private int[] getZeroCounts(List<String> lines) {
		int[] zeroCounts = new int[lines.get(0).length()];
		for (String line: lines)
			for (int i = 0; i < line.length(); i++)
				if (line.charAt(i) == '0')
					zeroCounts[i]++;
		return zeroCounts;
	}
}