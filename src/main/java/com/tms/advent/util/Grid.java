package com.tms.advent.util;

import java.lang.reflect.Array;
import java.util.List;

public class Grid<T> {
	public T[][] grid;

	public static Grid<Integer> readIntGrid(List<String> input) {
		int lines = input.size();
		int cols = input.get(0).length();
		Grid<Integer> grid = new Grid<>(Integer.class, lines, cols);
		for (int line = 0; line < lines; line++)
			for (int col = 0; col < cols; col++)
				grid.set(new PointLineCol(line, col), input.get(line).charAt(col) - 48);		
		return grid;
	} 

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

	@SuppressWarnings("unchecked")		
	public Grid(Class<?> cls, int lines, int cols, T initValue) {
		grid = (T[][])Array.newInstance(cls, lines, cols);		
		visit(point -> set(point, initValue));
	}

	public String toString() {
		return toString((point, value) -> value);
	}

	public String toString(ObjectGridVisitor visitor) {
		StringBuilder result = new StringBuilder();
		int cols = numCols();
		visit(point -> {
			result.append(visitor.visit(point, get(point)));
			if (point.col == cols - 1)
				result.append("\n");
		});
		return result.toString();
	}


	public int numLines() {
		return grid.length;
	}

	public int numCols() {
		return grid[0].length;
	}

	public T get(PointLineCol point) {
		return grid[point.line][point.col];
	}

	public Grid<T> set(PointLineCol point, T value) {
		grid[point.line][point.col] = value;
		return this;
	}

	public Grid<T> visit(GridVisitor visitor) {
		for (int line = 0; line < grid.length; line++)
			for (int col = 0; col < grid[line].length; col++) 
				visitor.visit(new PointLineCol(line, col));
		return this;
	}

	public Object visitWithObject(Object initValue, ObjectGridVisitor visitor) {
		Object value = initValue;
		for (int line = 0; line < grid.length; line++)
			for (int col = 0; col < grid[line].length; col++) 
				value = visitor.visit(new PointLineCol(line, col), value);
		return value;
	}

	public int visitWithInt(int initValue, IntGridVisitor visitor) {
		return (int)visitWithObject(initValue, (adj, value) -> visitor.visit(adj, (int)value));
	}

	public Grid<T> visitAdjacent(PointLineCol point, GridVisitor visitor) {
		visitAdjacentWithObject(point, null, (adj, value) -> { visitor.visit(adj); return null; });		
		return this;
	}

	public boolean visitAdjacentWithBoolean(PointLineCol point, boolean initValue, BooleanGridVisitor visitor) {
		return (boolean)visitAdjacentWithObject(point, initValue, (adj, value) -> visitor.visit(adj, (boolean)value));
	}

	public int visitAdjacentWithInt(PointLineCol point, int initValue, IntGridVisitor visitor) {
		return (int)visitAdjacentWithObject(point, initValue, (adj, value) -> visitor.visit(adj, (int)value));
	}

	public Object visitAdjacentWithObject(PointLineCol point, Object initValue, ObjectGridVisitor visitor) {
		return visitAdjacentWithObject(point, false, initValue, visitor);
	}

	public Object visitAdjacentWithObject(PointLineCol point, boolean visitDiagonal, Object initValue, ObjectGridVisitor visitor) {
		Object value = initValue;
		int step = visitDiagonal ? 45 : 90;
		for (int heading = 0; heading < 360; heading += step) {
			PointLineCol adj = point.move(heading, 1);
			if (adj.line >= 0 && adj.line < grid.length && adj.col >= 0 && adj.col < grid[adj.line].length)
				value = visitor.visit(adj, value);
		}
		return value;
	}
}
