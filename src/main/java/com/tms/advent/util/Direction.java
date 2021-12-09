package com.tms.advent.util;

public enum Direction {
	NORTH(-1, 0), NORTHEAST(-1, 1), EAST(0, 1), SOUTHEAST(1, 1), SOUTH(1, 0), SOUTHWEST(1, -1), WEST(0, -1), NORTHWEST(-1, -1);
	
	public int xTranslate, yTranslate;
	
	private Direction(int rowTranslate, int colTranslate) {
		this.xTranslate = colTranslate;
		this.yTranslate = rowTranslate;
	}
}
