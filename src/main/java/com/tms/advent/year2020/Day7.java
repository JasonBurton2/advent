package com.tms.advent.year2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tms.advent.util.Day;
import com.tms.advent.util.ListType;

@ListType(Day7.Bag.class)
public class Day7 extends Day<Day7.Bag> {
	private Map<String, Bag> bags = new HashMap<>();
	
	protected void onRun() {
		for (Bag bag : input)
			bags.put(bag.color, bag);
	}
	protected Object part1() {
		Map<String, List<String>> allowedContainers = new HashMap<>();
		for (Bag bag : bags.values()) {
			for (Content c : bag.contents) {
				List<String> list = allowedContainers.computeIfAbsent(c.color, (key) -> new ArrayList<>());
				list.add(bag.color);
			}
		}
		Set<String> result = new HashSet<String>();
		addAllowedContainers("shiny gold", allowedContainers, result);
		return result.size();
	}

	protected Object part2() {
		return countRecursive("shiny gold") - 1;
	}
	
	private int countRecursive(String color) {
		Bag bag = bags.get(color);
		int result = 1;
		for (Content c : bag.contents)
			result += c.count * countRecursive(c.color);
		return result;
	}
	
	private Set<String> addAllowedContainers(String color, Map<String, List<String>> allowedContainers, Set<String> result) {
		List<String> allowedList = allowedContainers.get(color);
		if (allowedList != null) {
			result.addAll(allowedList);
			for (String innerColor : allowedList)
				addAllowedContainers(innerColor, allowedContainers, result);
		}
		return result;
	}
	
	public static class Bag {
		private String color;
		private List<Content> contents = new ArrayList<>();
		
		public Bag(String line) {
			line = line.replace(" bags", "").replace(" bag", "").replace(".", "");
			int containPos = line.indexOf(" contain");
			color = line.substring(0, containPos);
			line = line.substring(containPos + 9);
			if (!line.equals("no other")) {
				String[] contents = line.split(", ");
				for (String s : contents) {
					int spacePos = s.indexOf(" ");
					Content c = new Content();
					c.count = Integer.parseInt(s.substring(0, spacePos));
					c.color = s.substring(spacePos + 1);
					this.contents.add(c);
				}
			}
		}
	}
	
	private static class Content {
		private int count;
		private String color;
		public String toString() {
			return count + " " + color;
		}
	}
}