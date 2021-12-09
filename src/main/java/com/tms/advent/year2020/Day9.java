package com.tms.advent.year2020;

import java.util.List;
import com.tms.advent.util.Day;
import com.tms.advent.util.ListType;

@ListType(Long.class)
public class Day9 extends Day<Long> {
	protected Object part1() {
		return getInvalidNumber();
	}
	
	protected Object part2() {
		long invalid = getInvalidNumber();
		for (int i = 0; i < input.size(); i++) {
			int endIndex = getContiguousWithSum(input, i, invalid);
			if (endIndex >= 0)
				return sumMinMax(input, i, endIndex);
		}
		return null;
	}

	private long getInvalidNumber() {
		int size = 25;
		long[] array = new long[size];
		for (int i = 0; i < size; i++)
			array[i] = input.get(i);
		
		int tail = 0;
		for (int i = size; i < input.size(); i++) {
			long current = input.get(i);
			if (!isSum(current, array))
				return current;
			array[tail++] = current;
			if (tail >= array.length)
				tail = 0;
		}
		throw new RuntimeException("Invalid number not found.");
	}
	
	private boolean isSum(long value, long[] list) {
		int size = list.length; 
		for (int i = 0; i < size; i++)
			for (int j = i + 1; j < size; j++)
				if (list[i] + list[j] == value)
					return true;
		return false;
	}
	
	private int getContiguousWithSum(List<Long> list, int startIndex, long mustSumTo) {
		long sum = list.get(startIndex);
		for (int i = startIndex + 1; i < list.size(); i++) {
			sum += list.get(i);
			if (sum == mustSumTo)
				return i;
			else if (sum > mustSumTo)
				return -1;
		}
		return -1;
	}
	
	private long sumMinMax(List<Long> list, int start, int end) {
		long min = Long.MAX_VALUE, max = Long.MIN_VALUE;
		for (int i = start; i <= end; i++) {
			long curr = list.get(i);
			min = Math.min(min, curr);
			max = Math.max(max, curr);
		}
		return min + max;
	}
}