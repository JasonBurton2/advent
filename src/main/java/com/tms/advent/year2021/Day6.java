package com.tms.advent.year2021;

import com.tms.advent.util.ArrayUtil;
import com.tms.advent.util.Day;
import com.tms.advent.util.XString;

public class Day6 extends Day<String> {
	private long[] fishByDay;

	@Override
	protected void initDay() {
		fishByDay = new long[9];
		int[] timers = new XString(input.get(0)).splitInts(",");
		for (int timer : timers)
			fishByDay[timer]++;
	}
	
	public Object part1() {
		return simulateDays(80);
    }
    
    public Object part2() {
		return simulateDays(256);
    }

	private long simulateDays(int count) {
		for (int i = 0; i < count; i++) {
			long readyToSpawn = fishByDay[0];
			for (int day = 1; day < fishByDay.length; day++)
				fishByDay[day - 1] = fishByDay[day];
			fishByDay[6] += readyToSpawn;
			fishByDay[8] = readyToSpawn;
		}
		return ArrayUtil.sumArray(fishByDay);
	}
}