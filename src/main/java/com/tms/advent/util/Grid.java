package com.tms.advent.util;

import java.lang.reflect.Array;

public class Grid<T> {
	public T[][] grid;

	public static interface GridVisitor {
		public void visit(PointLineCol point);
	}

	public static interface ObjectGridVisitor {
		public Object visit(PointLineCol point, Object value);
	}

	public static interface BooleanGridVisitor {
		public boolean visit(PointLineCol point, boolean value);
	}

	public static interface IntGridVisitor {
		public int visit(PointLineCol point, int value);
	}

	@SuppressWarnings("unchecked")		
	public Grid(Class<?> cls, int lines, int cols) {
		grid = (T[][])Array.newInstance(cls, lines, cols);		
	}

	public T get(PointLineCol point) {
		return grid[point.line][point.col];
	}

	public void set(PointLineCol point, T value) {
		grid[point.line][point.col] = value;
	}

	public void visit(GridVisitor visitor) {
		for (int line = 0; line < grid.length; line++)
			for (int col = 0; col < grid[line].length; col++) 
				visitor.visit(new PointLineCol(line, col));
	}

	public void visitAdjacent(PointLineCol point, GridVisitor visitor) {
		visitAdjacentWithObject(point, null, (adj, value) -> { visitor.visit(adj); return null; });		
	}

	public boolean visitAdjacentWithBoolean(PointLineCol point, boolean initValue, BooleanGridVisitor visitor) {
		return (boolean)visitAdjacentWithObject(point, initValue, (adj, value) -> visitor.visit(adj, (boolean)value));
	}

	public int visitAdjacentWithInt(PointLineCol point, int initValue, IntGridVisitor visitor) {
		return (int)visitAdjacentWithObject(point, initValue, (adj, value) -> visitor.visit(adj, (int)value));
	}

	public Object visitAdjacentWithObject(PointLineCol point, Object initValue, ObjectGridVisitor visitor) {
		Object value = initValue;
		for (int heading = 0; heading < 360; heading += 90) {
			PointLineCol adj = point.move(heading, 1);
			if (adj.line >= 0 && adj.line < grid.length && adj.col >= 0 && adj.col < grid[adj.line].length)
				value = visitor.visit(adj, value);
		}
		return value;
	}
}
