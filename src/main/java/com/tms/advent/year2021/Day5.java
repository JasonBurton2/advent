package com.tms.advent.year2021;

import java.util.ArrayList;
import java.util.List;

import com.tms.advent.util.Day;
import com.tms.advent.util.Point;
import com.tms.advent.util.XString;

public class Day5 extends Day<String> {
	int size = 1000;
	List<Line> lines;
	int[][] map;

	protected void initDay() {
		lines = new ArrayList<>();
		map = new int[size][];
		for (int i = 0; i < map.length; i++)
			map[i] = new int[size];
		for (String line : input) 
			lines.add(new Line(new XString(line)));
	}

    public Object part1() {
		for (Line line : lines)
			if (line.isStraight())
				line.applyToMap(map);
		return countDangerous();
    }
    
    public Object part2() {
		for (Line line : lines)
			line.applyToMap(map);
		return countDangerous();
    }

	private int countDangerous() {
		int result = 0;
		for (int y = 0; y < map[0].length; y++)
			for (int x = 0; x < map.length; x++)
				if (map[x][y] > 1)
					result++;
		return result;
	}

	private static class Line {
		Point start, end;

		private Line(XString def) {
			start = new Point(def.textBefore(" -> "));
			end = new Point(def.textAfter(" -> "));
		}

		private boolean isStraight() {
			return start.x == end.x || start.y == end.y;
		}

		private int getIncrement(int start, int end) {
			if (start < end)
				return 1;
			else if (start > end)
				return -1;
			return 0;			
		}

		private void applyToMap(int[][] map) {
			int xInc = getIncrement(start.x, end.x);
			int yInc = getIncrement(start.y, end.y);

			Point curr = start;
			do {
				map[curr.x][curr.y]++;
				if (curr.equals(end))
					break;
				curr.x += xInc;
				curr.y += yInc;
			} while (true);
		}
	}
}