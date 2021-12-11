package com.tms.advent.year2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tms.advent.util.Day;
import com.tms.advent.util.Grid;
import com.tms.advent.util.PointLineCol;

public class Day9 extends Day<String> {
	Grid<Integer> grid;
	int riskLevel;
	List<Integer> basins = new ArrayList<>();

	public void onRun() {
		grid = Grid.readIntGrid(input);
		grid.visit(point -> {		
			if (grid.visitAdjacentWithBoolean(point, true, (adj, value) -> value = value && grid.get(point) < grid.get(adj))) {
				riskLevel += grid.get(point) + 1;
				basins.add(getBasinSize(point, new HashSet<>()));
			}
		});
	}

    public Object part1() {
		return riskLevel;
    }
    
    public Object part2() {
		Collections.sort(basins, Collections.reverseOrder());
		return basins.get(0) * basins.get(1) * basins.get(2);
    }

	private int getBasinSize(PointLineCol point, Set<PointLineCol> basin) {
		basin.add(point);
		return grid.visitAdjacentWithInt(point, 1, (adj, value) -> value + (basin.contains(adj) || grid.get(adj) == 9 ? 0 : getBasinSize(adj, basin)));
	}
}