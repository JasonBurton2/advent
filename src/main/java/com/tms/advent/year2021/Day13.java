 package com.tms.advent.year2021;

import com.tms.advent.util.Day;
import com.tms.advent.util.Grid;
import com.tms.advent.util.PointLineCol;
import com.tms.advent.util.StringUtil;
import com.tms.advent.util.UseTestInput;

// @UseTestInput
public class Day13 extends Day<String> {
	Grid<Boolean> grid;

	protected void initDay() {
		int lines = 0, cols = 0;
		for (String line : input)
			if (line.contains(",")) {
				PointLineCol point = new PointLineCol(line);
				cols = Math.max(cols, point.col);
				lines = Math.max(lines, point.line);
			}
		grid = new Grid<>(Boolean.class, lines + 1, cols + 1, false);
		for (String line : input)
			if (line.contains(",")) {
				PointLineCol point = new PointLineCol(line);
				grid.set(point, true);
			}
	}

    public Object part1() {
		for (String line : input)
			if (line.startsWith("fold")) {
				grid = fold(line.charAt(11) == 'x', Integer.parseInt(StringUtil.textAfter("=", line)));
				break;
			}
		return grid.visitWithInt(0, (point, value) -> value + (grid.get(point) ? 1 : 0));
    }

    public Object part2() {
		for (String line : input)
			if (line.startsWith("fold"))
				grid = fold(line.charAt(11) == 'x', Integer.parseInt(StringUtil.textAfter("=", line)));
		return "\n" + grid.toString((point, value) -> value == Boolean.TRUE ? "#" : ".");
    }

	private Grid<Boolean> fold(boolean isColFold, int foldAt) {
		Grid<Boolean> target = new Grid<>(Boolean.class, isColFold ? grid.numLines() : foldAt, isColFold ? foldAt: grid.numCols(), false);
		target.visit(point -> {
			PointLineCol foldPoint = new PointLineCol(isColFold ? point.line: grid.numLines() - point.line - 1, isColFold ? grid.numCols() - point.col - 1 : point.col);
			target.set(point, grid.get(point) || grid.get(foldPoint));
		});
		return target;
	}
}