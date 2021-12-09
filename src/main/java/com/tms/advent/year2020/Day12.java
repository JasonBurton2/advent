package com.tms.advent.year2020;

import com.tms.advent.util.Day;
import com.tms.advent.util.ListType;
import com.tms.advent.util.Point;

@ListType(Day12.Instruction.class)
public class Day12 extends Day<Day12.Instruction> {
	protected Object part1() {
		int facing = 90;
		Point loc = new Point();
		for (Instruction inst : input) {
			if (inst.direction == 'L')
				facing = fixHeading(facing - inst.amount);
			else if (inst.direction == 'R')
				facing = fixHeading(facing + inst.amount);
			else
				loc = loc.move(getHeading(inst.direction, facing), inst.amount);
		}
		return Math.abs(loc.y) + Math.abs(loc.x);
	}
	
	protected Object part2() {
		Point waypoint = new Point(10, -1);
		Point loc = new Point();
		for (Instruction inst : input) {
			if (inst.direction == 'L' || inst.direction == 'R')
				waypoint.rotate(inst.direction == 'R', inst.amount / 90);
			else if (inst.direction == 'F') {
				for (int i = 0; i < inst.amount; i++)
					loc.moveBy(waypoint);
			}
			else
				waypoint = waypoint.move(getHeading(inst.direction, -1), inst.amount);
		}
		return Math.abs(loc.y) + Math.abs(loc.x);
	}
	
	private int getHeading(char dir, int facing) {
		if (dir == 'N')
			return 0;
		else if (dir == 'E')
			return 90;
		else if (dir == 'S')
			return 180;
		else if (dir == 'W')
			return 270;
		else
			return facing;
	}
	
	private int fixHeading(int heading) {
		if (heading < 0)
			heading = 360 - (heading * -1);
		else if (heading >= 360)
			heading = heading - 360;
		return heading;
	}
	
	public static class Instruction {
		private char direction;
		private int amount;
		
		public Instruction(String line) {
			direction = line.charAt(0);
			amount = Integer.parseInt(line.substring(1));
		}
	}
}