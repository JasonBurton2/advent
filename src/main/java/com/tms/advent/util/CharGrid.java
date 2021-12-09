package com.tms.advent.util;

import java.util.List;

public class CharGrid {
	public int yMax;
	public int xMax;
	private int modCount;
	private char[][] data;

	public CharGrid(CharGrid src) {
		this(src, false);
	}

	public CharGrid(CharGrid src, boolean empty) {
		xMax = src.xMax;
		yMax = src.yMax;
		data = new char[xMax][yMax];
		if (!empty) {
			for (int x = 0; x < src.xMax; x++)
				for (int y = 0; y < src.data[x].length; y++)
					data[x][y] = src.data[x][y];
		}
	}
	
	public CharGrid(List<String> input) {
		xMax = input.size();
		yMax = input.get(0).length();
		data = new char[xMax][yMax];
		for (int x = 0; x < xMax; x++)
			for (int y = 0; y < yMax; y++)
				data[x][y] = input.get(x).charAt(y);
		modCount = xMax * yMax;
	}
	
	public void visit(Looper looper) {
		for (int x = 0; x < xMax; x++)
			for (int y = 0; y < yMax; y++)
				looper.process(new Point(x, y), data[x][y]);		
	}
	
	public int count(char c) {
		int result = 0;
		for (int x = 0; x < xMax; x++)
			for (int y = 0; y < yMax; y++) {
				char val = data[x][y];
				if (val == c)
					result++;
			}
		return result;
	}

	public void print(String title) {
		System.out.println("***** " + title + " *****");
		for (int y = 0; y < yMax; y++) {
			for (int x = 0; x < xMax; x++)
				System.out.print(data[x][y] + " ");
			System.out.println();
		}
	}
	
	public char get(Point p) {
		return data[p.x][p.y];
	}

	public void set(Point p, char c) {
		data[p.x][p.y] = c;
		modCount++;
	}
	
	public int getModCount() {
		return modCount;
	}

	public boolean inBounds(Point p) {
		return p.y >= 0 && p.x >= 0 && p.y < yMax && p.x < xMax;
	}

	public interface Looper {
		public void process(Point point, char value);
	}
	
	public void flip(boolean aboutVerticalAxis) {
		if (aboutVerticalAxis) {
		    for(int x = 0; x < (data.length / 2); x++) {
		        char[] temp = data[x];
		        data[x] = data[data.length - x - 1];
		        data[data.length - x - 1] = temp;
		    }			
		} else {
			for (int x = 0; x < xMax; x++) {
				for(int y = 0; y < (data[x].length / 2); y++) {
					char temp = data[x][y];
					data[x][y] = data[x][data[x].length - y - 1];
					data[x][data[x].length - y - 1] = temp;
				}
			}
		}
	}

	public void rotate() {
		char[][] data = this.data;
	    for (int x = 0; x < xMax / 2; x++) {
	        for (int y = x; y < xMax - x - 1; y++) {
	            char temp = data[x][y];
	            data[x][y] = data[xMax - 1 - y][x];
	            data[xMax - 1 - y][x] = data[xMax - 1 - x][xMax - 1 - y];
	            data[xMax - 1 - x][xMax - 1 - y] = data[y][xMax - 1 - x];
	            data[y][xMax - 1 - x] = temp;
	        }
	    }	}
}
