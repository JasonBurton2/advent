package com.tms.advent.util;

public class Point {
	public int x, y;
	public Point() {
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(String xCommaY) {
		int commaPos = xCommaY.indexOf(",");
		this.x = Integer.parseInt(xCommaY.substring(0, commaPos).trim());
		this.y = Integer.parseInt(xCommaY.substring(commaPos + 1).trim());
	}
	
	public Point(Point src) {
		x = src.x;
		y = src.y;
	}

	public Point move(Direction dir, int distance) {
		return new Point(x + (dir.xTranslate * distance), y + (dir.yTranslate * distance));
	}
	
	public Point move(int heading, int amount) {
		Point point = new Point(this);
		if (heading == 0)
			point.y -= amount;
		else if (heading == 90)
			point.x += amount;
		else if (heading == 180)
			point.y += amount;
		else if (heading == 270)
			point.x -= amount;
		return point;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public void moveBy(Point pt) {
		x += pt.x;
		y += pt.y;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void rotate(boolean clockwise, int times) {
		for (int i = 0; i < times; i++)
			if (clockwise)
				set(y * -1, x);
			else
				set(y, x * -1);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point))
			return false;
		return x == ((Point)obj).x && y == ((Point)obj).y;
	}
	
	@Override
	public int hashCode() {
        long bits = Double.doubleToLongBits(x);
        bits ^= Double.doubleToLongBits(y) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
	}
}
