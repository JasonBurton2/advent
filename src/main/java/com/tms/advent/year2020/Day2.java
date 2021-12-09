package com.tms.advent.year2020;

import com.tms.advent.util.Day;
import com.tms.advent.util.ListType;
import com.tms.advent.util.XString;

@ListType(Day2.Password.class)
public class Day2 extends Day<Day2.Password> {
	protected Object part1() {
		return countInput((password) -> password.isValidPart1());
	}

	protected Object part2()	{
		return countInput((password) -> password.isValidPart2());
	}

	public static class Password {
		private int min, max;
		private char match;
		private String password;
		
		public Password(String line) {
			String minMax = new XString(line).textBefore(" ");
			line = new XString(line).textAfter(" ");
			min = Integer.parseInt(new XString(minMax).textBefore("-"));
			max = Integer.parseInt(new XString(minMax).textAfter("-"));
			match = line.charAt(0);
			password = new XString(line).textAfter(" ");
		}
		
		public boolean isValidPart1() {
			int count = 0;
			for (int i = 0; i < password.length(); i++)
				if (password.charAt(i) == match)
					count++;
			return count >= min && count <= max;
		}

		public boolean isValidPart2() {
			boolean atMin = password.charAt(min - 1) == match;
			boolean atMax = password.charAt(max - 1) == match;
			return (atMin || atMax) && (!atMin || !atMax);  
		}
	}
}
