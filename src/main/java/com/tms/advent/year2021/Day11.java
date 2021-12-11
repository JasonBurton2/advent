package com.tms.advent.year2021;

import java.util.HashSet;
import java.util.Set;

import com.tms.advent.util.Day;
import com.tms.advent.util.Grid;
import com.tms.advent.util.PointLineCol;

public class Day11 extends Day<String> {
	private Set<PointLineCol> stepFlashes = new HashSet<>();
	
    public Object part1() {
		Grid<Integer> grid = Grid.readIntGrid(input);
		int result = 0;
		for (int i = 0; i < 100; i++)
			result += step(grid);
		return result;
    }

    public Object part2() {
		Grid<Integer> grid = Grid.readIntGrid(input);
		int stepIndex = 0;
		do {
			stepIndex++;
		} while (step(grid) != 100);
		return stepIndex;
    }

	private int step(Grid<Integer> grid) {
		grid.visit(point -> grid.set(point, grid.get(point) + 1));
		stepFlashes.clear();
		int startingSize;
		do {
			startingSize = stepFlashes.size();
			grid.visit(point -> {
				if (grid.get(point) > 9) {
					stepFlashes.add(point);
					grid.set(point, 0);
					grid.visitAdjacentWithObject(point, true, null, (adj, obj) -> {
						if (!stepFlashes.contains(adj))
							grid.set(adj, grid.get(adj) + 1);
						return obj;
					});
				}
			});
		} while (stepFlashes.size() - startingSize != 0);
		return stepFlashes.size();
	}
}