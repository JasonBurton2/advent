package com.tms.advent.year2020;

import com.tms.advent.util.Day;
import com.tms.advent.util.ArrayUtil;

public class Day17 extends Day<String> {
	protected Object part1() {
		return doCycles(6, true);
	}
	
	protected Object part2() {
		return doCycles(6, false);
	}
	
	private long doCycles(int cycles, boolean singleW) {
		Object[][][][] layers = ArrayUtil.initialize(input.get(0).length() + cycles * 2, input.size() + cycles * 2, cycles * 2 + 1, cycles * 2 + 1, false);
		for (int y = 0; y < input.size(); y++)
			for (int x = 0; x < input.get(y).length(); x++)
				layers[x + cycles][y + cycles][cycles][cycles] = input.get(y).charAt(x) == '#';
		for (int i = 0; i < cycles; i++) {
			Object[][][][] newLayers = ArrayUtil.copy(layers);
			ArrayUtil.visit(layers, (x, y, z, w) -> {
				if (w == cycles || !singleW) { 
					long count = ArrayUtil.sumAdjacent(layers, x, y, z, w, (inX, inY, inZ, inW) -> (boolean)layers[inX][inY][inZ][inW] ? 1: 0);
					if ((boolean)layers[x][y][z][w] && count < 2 || count > 3)
						newLayers[x][y][z][w] = false; 
					else if (!(boolean)layers[x][y][z][w] && count == 3)
						newLayers[x][y][z][w] = true;
				}
			});
			ArrayUtil.copyTo(newLayers, layers);
		}
		return ArrayUtil.sum(layers, (x, y, z, w) -> (boolean)layers[x][y][z][w] ? 1 : 0);
	}
}