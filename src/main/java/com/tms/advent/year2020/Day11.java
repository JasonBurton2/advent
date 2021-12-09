package com.tms.advent.year2020;

import com.tms.advent.util.CharGrid;
import com.tms.advent.util.Day;
import com.tms.advent.util.Direction;
import com.tms.advent.util.Point;

public class Day11 extends Day<String> {
	protected Object part1() {
		return everybodySettleDown(4, 1);
	}
	
	protected Object part2() {
		return everybodySettleDown(5, Integer.MAX_VALUE);
	}
	
	private int everybodySettleDown(int ickyTolerance, int stayAwayFromMeDistance) {
		CharGrid seats = new CharGrid(input);
		while (seats.getModCount() > 0)
			seats = everybodyFidget(seats, ickyTolerance, stayAwayFromMeDistance);
		return seats.count('#');
	}
	
	private CharGrid everybodyFidget(CharGrid seats, int ickyTolerance, int stayAwayFromMeDistance) {
		CharGrid result = new CharGrid(seats);
		seats.visit((point, val) -> {
			int ickyFactor = getIckyFactor(seats, point, stayAwayFromMeDistance);
			if (val == 'L' && ickyFactor == 0)
				result.set(point, '#');
			else if (val == '#' && ickyFactor >= ickyTolerance)
				result.set(point, 'L');
		});
		return result;
	}

	private int getIckyFactor(CharGrid seats, Point point, int stayAwayFromMeRadius) {
		int count = 0;
		for (Direction dir : Direction.values()) {
			for (int radius = 1; radius <= stayAwayFromMeRadius; radius++) {
				Point checkPoint = point.move(dir, radius);
				if (!seats.inBounds(checkPoint))
					break;
				if (seats.get(checkPoint) == '#')
					count++;
				if (seats.get(checkPoint) != '.')
					break;
			}
		}
		return count;
	}	
}