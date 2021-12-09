package com.tms.advent.year2021;

import java.util.function.IntFunction;

import com.tms.advent.util.Day;
import com.tms.advent.util.XString;

public class Day7 extends Day<String> {
	int[] positions;

	@Override
	protected void initDay() {
		positions = new XString(input.get(0)).splitInts(",");
	}
	
	public Object part1() {
		return getMinFuel(distance -> distance);
    }
    
    public Object part2() {
		return getMinFuel(distance -> distance * (distance + 1) / 2); // triangle number - like factoral, but with addition.  Learn something new every day.
    }

	private int getMinFuel(IntFunction<Integer> costCalcFunction) {
		int lastFuel = Integer.MAX_VALUE;
		for (int destination = 0; destination < Integer.MAX_VALUE; destination++) {
			int thisFuel = 0;
			for (int crabPos : positions)
				thisFuel += costCalcFunction.apply(Math.abs(crabPos - destination));
			if (thisFuel > lastFuel)
				break;
			lastFuel = thisFuel;
		}
		return lastFuel;
	}
}