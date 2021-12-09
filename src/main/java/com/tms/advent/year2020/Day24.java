package com.tms.advent.year2020;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tms.advent.util.Day;
import com.tms.advent.util.Point;

public class Day24 extends Day<String> {
	Map<Point, Boolean> tiles = new HashMap<>();

	protected Object part1() {
		for (String line : input) {
			int pos = 0;
			Point curr = new Point(0, 0);
			int lineLen = line.length();
			while (pos < lineLen) {
				for (HexDir dir : HexDir.values()) {
					if (line.length() >= + pos + dir.len && line.substring(pos, pos + dir.len).equals(dir.str)) {
						curr.moveBy(dir.move);
						pos += dir.len;
					}
				}
			}
			boolean flipped = tiles.computeIfAbsent(curr, (key) -> false);
			tiles.put(curr, !flipped);
		}
		return tiles.values().stream().filter((value) -> value).count();
	}

	protected Object part2() {
		for (int i = 0; i < 100; i++) {
			Set<Point> checkedWhites = new HashSet<>();
			Map<Point, Boolean> newTiles = new HashMap<>(tiles);
			tiles.entrySet().stream().filter((entry) -> entry.getValue()).forEach((entry) -> {
				int blackNeighbors = countBlackNeighbors(entry.getKey(), newTiles, checkedWhites);
				if (blackNeighbors == 0 || blackNeighbors > 2)
					newTiles.put(entry.getKey(), false);
			});
			tiles = newTiles;
		}
		return tiles.values().stream().filter((value) -> value).count();
	}
	
	private int countBlackNeighbors(Point point, Map<Point, Boolean> newTiles, Set<Point> checkedWhites) {
		int result = 0;
		for (HexDir dir : HexDir.values()) {
			Point neighborPoint = new Point(point.x + dir.move.x, point.y + dir.move.y);
			Boolean neighborBlack = tiles.get(neighborPoint);
			if (neighborBlack != null && neighborBlack)
				result++;
			else if (checkedWhites != null && !checkedWhites.contains(neighborPoint)) {
				int blackNeighbors = countBlackNeighbors(neighborPoint, newTiles, null);
				if (blackNeighbors == 2)
					newTiles.put(neighborPoint, true);
			}
		}
		return result;
	}
	
	private enum HexDir {
		E(1, 0), W(-1, 0), SE(0, 1), SW(-1, 1), NE(1, -1), NW(0, -1);
		
		private Point move;
		private String str;
		private int len;
		private HexDir(int moveX, int moveY) {
			move = new Point(moveX, moveY);
			str = toString().toLowerCase();
			len = str.length(); 
		}
	}

}