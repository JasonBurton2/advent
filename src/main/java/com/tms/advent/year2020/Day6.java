package com.tms.advent.year2020;

import java.util.ArrayList;

import com.tms.advent.util.Day;
import com.tms.advent.util.ListType;

@ListType(Day6.Group.class)
public class Day6 extends Day<Day6.Group> {
	@Override
	protected void makeInputList() {
		input = new ArrayList<>();
		Group group = new Group();
		for (String line : inputStrings) {
			if (line.trim().length() == 0) {
				input.add(group);
				group = new Group();
			}
			else
				group.add(line);	
		}
		input.add(group);
	}
	
	protected Object part1() {
		return countInput((group) -> group.isAnyYes());
	}

	protected Object part2() {
		int result = 0;
		for (Group group : input) {
			int groupCount = 0;
			for (int i = 0; i < group.allYes.length; i++)
				if (group.allYes[i])
					groupCount++;
			result += groupCount;
		}
		return result;
	}
	
	public static class Group {
		private boolean[] anyYes = new boolean[26],
			allYes = new boolean[26];
		
		private Group() {
			for (int i = 0; i < allYes.length; i++)
				allYes[i] = true;
		}

		private boolean isAnyYes() {
			for (int i = 0; i < anyYes.length; i++)
				if (anyYes[i])
					return true;
			return false;
		}		
		
		private void add(String line) {
			for (int i = 0; i < line.length(); i++)
				anyYes[line.charAt(i) - 'a'] = true;
			for (int i = 0; i < anyYes.length; i++)
				if (!line.contains("" + (char)('a' + i)))
					allYes[i] = false;
		}
	}
}