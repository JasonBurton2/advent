package com.tms.advent.year2020;

import com.tms.advent.util.Day;
import com.tms.advent.util.ListType;

@ListType(Integer.class)
public class Day1 extends Day<Integer> {
    public Object part1() {
    	int targetValue = 2020;
    	boolean[] contains = new boolean[targetValue];
    	for (int value : input)
    		if (contains[targetValue - value])
    			return value * (targetValue - value);
    		else if (value < targetValue)
    			contains[value] = true;
    	return null;
    }
    
    public Object part2() {
    	int targetValue = 2020;
    	boolean[] contains = new boolean[targetValue];
    	for (int i : input)
    		contains[i] = true;
    	for (int i = 0; i < input.size(); i++)
    		for (int j = i + 1; j < input.size(); j++) {
    			int complement = targetValue - (input.get(i) + input.get(j));
    			if (complement >= 0 && complement < targetValue && contains[complement])
					return input.get(i) * input.get(j) * complement;
    		}
    	return null;
    }
}