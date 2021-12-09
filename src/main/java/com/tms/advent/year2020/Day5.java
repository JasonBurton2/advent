package com.tms.advent.year2020;

import com.tms.advent.util.Day;
import com.tms.advent.util.ListType;

@ListType(Day5.Seat.class)
public class Day5 extends Day<Day5.Seat> {
	protected Object part1() {
		return getMax((seat) -> seat.getId());
	}

	protected Object part2() {
		Seat[] seatArray = new Seat[128 * 8];
		for (Seat seat : input)
			seatArray[seat.getId()] = seat;
		for (int i = 1; i < seatArray.length - 1; i++)
			if (seatArray[i] == null && seatArray[i - 1] != null && seatArray[i + 1] != null)
				return i;
		return null;
	}
	
	public static class Seat {
		int row, column;

		public Seat(String line) {
			row = findBinary(line.substring(0, 7), 128, 'F');
			column = findBinary(line.substring(7), 8, 'L');
		}
		
		private int findBinary(String line, int count, char lower) {
			int floor = 0, ceil = count;
			for (int i = 0; i < line.length(); i++) {
				int half = floor + ((ceil - floor) / 2);
				if (line.charAt(i) == lower)
					ceil = half;
				else
					floor = half;
			}
			return floor;
		}
		
		private int getId() {
			return row * 8 + column;
		}
	}
}