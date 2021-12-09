package com.tms.advent.year2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tms.advent.util.ArrayUtil;
import com.tms.advent.util.CharGrid;
import com.tms.advent.util.Day;
import com.tms.advent.util.Direction;
import com.tms.advent.util.Point;
import com.tms.advent.util.XString;

public class Day20 extends Day<String> {
	private int tileSize = 10;
	List<Tile> tiles = new ArrayList<>();
	private static final Direction[] dirs = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
	// private static final Direction[] compDirs = {Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST};
	Map<Integer, Match[]> matchesByTile = new HashMap<>();
	Tile[][] image;
	int imageSize;
	
	protected void onRun() {
		List<List<String>> lists = ArrayUtil.splitList(input, "");
		for (List<String> tileList : lists)
			tiles.add(new Tile(tileList));
		imageSize = (int)Math.sqrt(tiles.size());
		image = new Tile[imageSize][imageSize];
		Collections.sort(tiles);
	}
	
	protected Object part1() {
		for (int i = 0; i < tiles.size(); i++) {
			Tile thisTile = tiles.get(i);
			for (int j = 0; j < tiles.size(); j++) {
				Tile otherTile = tiles.get(j);
				if (thisTile != otherTile) {
					Match m = thisTile.getMatch(otherTile);
					if (m != null) {
						Match[] matchDirs = matchesByTile.computeIfAbsent(thisTile.index, (key) -> new Match[4]);
						matchDirs[ArrayUtil.indexOf(dirs, m.dir1)] = m;
					}
				}
			}
		}
		long result = 1;
		for (Map.Entry<Integer, Match[]> entry : matchesByTile.entrySet())
			if (ArrayUtil.countNonNull(entry.getValue()) == 2) {
				result *= entry.getKey();
				Match[] matches = entry.getValue();
				if (matches[1] != null && matches[2] != null)
					image[0][0] = matches[1].tile1;
			}
		return result;
	}
	
	protected Object part2() {
		for (int y = 0; y < imageSize; y++) {
			for (int x = 0; x < imageSize - 1; x++) {
				Tile tile = image[x][y];
				if (tile != null) {
				Match eastMatch = matchesByTile.get(tile.index)[1];
				if (eastMatch != null && x < imageSize - 1)
					image[x + 1][y] = eastMatch.tile2;
				Match southMatch = matchesByTile.get(tile.index)[2];
				if (southMatch != null && y < imageSize - 1)
					image[x][y + 1] = southMatch.tile2;
				}
			}
		}
		for (int y = 0; y < imageSize; y++) {
			for (int x = 0; x < imageSize; x++)
				if (image[x][y] == null)
					System.out.print("null  -  ");
				else
					System.out.print(image[x][y].index + "  -  ");
			System.out.println();
		}
		System.out.println(image[0][0].index);
		return null;
	}
	
	private class Tile implements Comparable<Tile> {
		private int index;
		private CharGrid grid;

		public Tile(List<String> list) {
			index = Integer.parseInt(new XString(list.get(0)).textBetween(" ", ":"));
			list.remove(0);
			grid = new CharGrid(list);
		}
		
		private char[] getEdge(Direction dir) {
			if (dir == Direction.NORTH)
				return getEdge(0, 0, 1, 0);
			else if (dir == Direction.EAST)
				return getEdge(tileSize - 1, 0, 0, 1);
			else if (dir == Direction.SOUTH)
				return getEdge(0, tileSize - 1, 1, 0);
			else
				return getEdge(0, 0, 0, 1);
		}
		
		private char[] getEdge(int xStart, int yStart, int xInc, int yInc) {
			char[] result = new char[tileSize];
			for (int i = 0; i < tileSize; i++) {
				Point p = new Point(xStart + (i * xInc), yStart + (i * yInc));
				result[i] = grid.get(p);
			}
			return result;
		}
		
		private boolean edgesEqual(Direction thisDir, Direction otherDir, Tile other, boolean flipThis, boolean flipOther) {
			char[] edge = getEdge(thisDir);
			char[] otherEdge = other.getEdge(otherDir);
			for (int i = 0; i < tileSize; i++) {
				char c1 = flipThis ? edge[tileSize - i - 1] : edge[i];
				char c2 = flipOther ? otherEdge[tileSize - i - 1] : otherEdge[i];
				if (c1 != c2)
					return false;
			}
			return true;
		}
		
		public Match getMatch(Tile tile) {
			for (Direction thisDir : dirs)
				for (Direction otherDir : dirs) 
					for (int flip2 = 0; flip2 < 2; flip2++)
					if (edgesEqual(thisDir, otherDir, tile, false, flip2 == 0)) {
						Match result = new Match();
						result.tile1 = this;
						result.tile2 = tile;
						result.dir1 = thisDir;
						result.dir2 = otherDir;
						result.flip2 = flip2 == 0;
						return result;
					}
			return null;							
		}

		public int compareTo(Tile o) {
			if (index < o.index)
				return -1;
			else if (index > o.index)
				return 1;
			else
				return 0;
		}
	}
	
	private class Match {
		private Tile tile1, tile2;
		@SuppressWarnings("unused")
		private Direction dir1, dir2;
		@SuppressWarnings("unused")
		private boolean flip1, flip2;
		
		// private void orient() {
		// 	if (flip1)
		// 		tile1.grid.flip(dir1 == Direction.NORTH || dir1 == Direction.SOUTH);
		// 	if (flip2)
		// 		tile2.grid.flip(dir2 == Direction.NORTH || dir2 == Direction.SOUTH);
		// 	int dir1Index = ArrayUtil.indexOf(dirs, dir1);
		// 	int dir2Index = ArrayUtil.indexOf(dirs, dir2);
		// 	int targetDir2Index = ArrayUtil.indexOf(dirs, compDirs[dir1Index]);
		// 	while (dir2Index != targetDir2Index) {
		// 		tile2.grid.rotate();
		// 		dir2Index++;
		// 		if (dir2Index >= dirs.length)
		// 			dir2Index = 0;
		// 	}
		// }
	}
}