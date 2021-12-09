package com.tms.advent.year2020;

import com.tms.advent.util.Day;
import com.tms.advent.util.ListType;

@ListType(Integer.class)
public class Day25 extends Day<Integer> {
	protected Object part1() {
		int doorPublicKey = input.get(0);
		int cardPublicKey = input.get(1);
		int doorLoopSize = getLoopSize(doorPublicKey);
		// int cardLoopSize = getLoopSize(cardPublicKey);
		long result = 1;
		for (int i = 0; i < doorLoopSize; i++)
			result = (result * cardPublicKey) % 20201227;
		return result;
	}
	
	private int getLoopSize(int key) {
		long value = 1;
		for (int i = 0; i < 100000000; i++) {
			value = (value * 7) % 20201227;
			if (value == key)
				return i + 1;
		}
		throw new RuntimeException("Could not determine loop size for " + key);
	}

	protected Object part2() {
		return null;
	}
}