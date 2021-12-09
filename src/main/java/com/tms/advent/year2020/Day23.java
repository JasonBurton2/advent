package com.tms.advent.year2020;

import com.tms.advent.util.Day;

public class Day23 extends Day<String> {
	protected Object part1() {
		int[] cups = initCups(9, getInputDigit(0));
		moveItCrabby(100, cups, getInputDigit(0));
		StringBuilder result = new StringBuilder();
		int i = 1;
		do {
			result.append(cups[i]);
			i = cups[i];
		} while (i != 1);
		return result.substring(0, result.length() - 1); 
	}

	protected Object part2() {
		int[] cups = initCups(1000000, 10);
		cups[1000000] = getInputDigit(0);
		moveItCrabby(10000000, cups, getInputDigit(0));
		return (long)cups[1] * (long)cups[cups[1]];
	}
	
	private void moveItCrabby(int iterations, int[] cups, int current) {
		for (int i = 1; i <= iterations; i++) {
			int pickup1 = cups[current];
			int pickup2 = cups[pickup1];
			int pickup3 = cups[pickup2];
			int dest = current - 1;
			if (dest <= 0)
				dest = cups.length - 1;
			while (dest == pickup1 || dest == pickup2 || dest == pickup3) {
				dest--;
				if (dest <= 0)
					dest = cups.length - 1;
			}
			cups[current] = cups[pickup3];
			int holder = cups[dest];
			cups[dest] = pickup1;
			cups[pickup3] = holder;
			current = cups[current];
		}
	}
	
	private int getInputDigit(int index) {
		return Integer.parseInt(Character.toString(input.get(0).charAt(index)));
	}
	
	private int[] initCups(int numCups, int lastDigit) {
		int[] result = new int[numCups + 1];
		for (int i = 0; i <= input.get(0).length() - 2; i++)
			result[getInputDigit(i)] = getInputDigit(i + 1);
		result[getInputDigit(input.get(0).length() - 1)] = lastDigit;
		for (int i = 10; i <= numCups; i++)
			result[i] = i + 1;
		return result;
	}
}