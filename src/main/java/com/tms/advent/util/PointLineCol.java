package com.tms.advent.util;

public class PointLineCol {
	public int line, col;
	public PointLineCol() {
	}
	
	public PointLineCol(int line, int col) {
		this.line = line;
		this.col = col;
	}

	public PointLineCol(PointLineCol src) {
		line = src.line;
		col = src.col;
	}

	public PointLineCol move(Direction dir, int distance) {
		return new PointLineCol(line + (dir.xTranslate * distance), col + (dir.yTranslate * distance));
	}
	
	public PointLineCol move(int heading, int amount) {
		PointLineCol point = new PointLineCol(this);
		heading = heading % 360;
		if (heading == 0)
			point.line -= amount;
		else if (heading == 45) {
			point.line -= amount;
			point.col += amount;
		}
		else if (heading == 90)
			point.col += amount;
		else if (heading == 135) {
			point.line += amount;
			point.col += amount;
		}
		else if (heading == 180)
			point.line += amount;
		else if (heading == 225) {
			point.line += amount;
			point.col -= amount;
		}
		else if (heading == 270)
			point.col -= amount;
		else if (heading == 315) {
			point.line -= amount;
			point.col -= amount;
		}
		return point;
	}
	
	@Override
	public String toString() {
		return "(" + line + ", " + col + ")";
	}

	public void moveBy(PointLineCol pt) {
		line += pt.line;
		col += pt.col;
	}
	
	public void set(int line, int col) {
		this.line = line;
		this.col = col;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PointLineCol))
			return false;
		return line == ((PointLineCol)obj).line && col == ((PointLineCol)obj).col;
	}
	
	@Override
	public int hashCode() {
        long bits = Double.doubleToLongBits(line);
        bits ^= Double.doubleToLongBits(col) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
	}
}
