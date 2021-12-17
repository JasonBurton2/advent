package com.tms.advent.year2021;

import com.tms.advent.util.Day;
import com.tms.advent.util.StringUtil;

public class Day17 extends Day<String> {
	int highestShot, hits;
	int minX, maxX, minY, maxY;

	protected void onRun() {
		String after = StringUtil.textAfter("=", input.get(0));
		String[] xRange = StringUtil.textBefore(",", after).split("\\.\\.");
		String[] yRange = StringUtil.textAfter("=", after).split("\\.\\.");
		minX = Math.min(Integer.parseInt(xRange[0]), Integer.parseInt(xRange[1]));
		maxX = Math.max(Integer.parseInt(xRange[0]), Integer.parseInt(xRange[1]));
		minY = Math.min(Integer.parseInt(yRange[0]), Integer.parseInt(yRange[1]));
		maxY = Math.max(Integer.parseInt(yRange[0]), Integer.parseInt(yRange[1]));
		for (int xVelocity = 1; xVelocity < 500; xVelocity++)
			for (int yVelocity = -500; yVelocity < 500; yVelocity++)
				if (landsInTarget(xVelocity, yVelocity))
					hits++;
	}

	private boolean landsInTarget(int xVelocity, int yVelocity) {
        int x = 0, y = 0, higher = 0;
        while (true){
            x += xVelocity;
            y += yVelocity;
            higher = Math.max(higher, y);
            if (xVelocity > 0) 
				xVelocity--;
			else if (xVelocity < 0)
				xVelocity++;
            yVelocity -= 1;
            if (x >= minX && x <= maxX && y >= minY && y <= maxY){
				highestShot = Math.max(higher, highestShot);
                return true;
            } else if(x > maxX || y < minY)
                return false;
        }	
	}

    @Override
    public Object part1() {
		return highestShot;
	}

	public Object part2() {
		return hits;
	}
}
