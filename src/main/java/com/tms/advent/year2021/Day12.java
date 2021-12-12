package com.tms.advent.year2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tms.advent.util.Day;
import com.tms.advent.util.StringUtil;

public class Day12 extends Day<String> {
	Cave start, end;
	
	protected void initDay() {
		Map<String, Cave> caves = new HashMap<>();
		for (String line : input) {
			Cave cave1 = caves.computeIfAbsent(StringUtil.textBefore("-", line), (key) -> new Cave(key));
			caves.computeIfAbsent(StringUtil.textAfter("-", line), (key) -> new Cave(key)).connect(cave1);
		}
		start = caves.get("start");
		end = caves.get("end");
	}

    public Object part1() {
		return findRoutesToEnd(new ArrayList<>(), new Path(null, start), false);
    }

    public Object part2() {
		return findRoutesToEnd(new ArrayList<>(), new Path(null, start), true);		
    }

	private int findRoutesToEnd(List<Path> paths, Path path, boolean canVisitSmallTwice) {
		Cave current = path.last();
		if (current == end)
			paths.add(path);
		else 
			for (Cave other : current.connections)
				if ((other.big || !path.contains(other) || canVisitSmallTwice) && other != start)
					findRoutesToEnd(paths, new Path(path, other), (other.big || !path.contains(other)) && canVisitSmallTwice);
		return paths.size();
	}

	private class Cave {
		private List<Cave> connections = new ArrayList<>();
		private boolean big;

		private Cave(String id) {
			this.big = id.charAt(0) >= 'A' && id.charAt(0) <= 'Z';
		}

		private void connect(Cave other) {
			connections.add(other);
			other.connections.add(this);
		}
	}

	private class Path {
		private List<Cave> path = new ArrayList<>();

		public Path(Path initPath, Cave cave) {
			if (initPath != null)
				path.addAll(initPath.path);
			path.add(cave);
		}

		public Cave last() {
			return path.get(path.size() - 1);
		}

		public boolean contains(Cave other) {
			return path.contains(other);
		}
	}
}