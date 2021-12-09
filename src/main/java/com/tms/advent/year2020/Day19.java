package com.tms.advent.year2020;

import java.util.List;

import com.tms.advent.util.ArrayUtil;
import com.tms.advent.util.Day;
import com.tms.advent.util.XString;

public class Day19 extends Day<String> {
	List<String> values;
	Rule[] rules;
	
	protected void onRun() {
		List<List<String>> lists = ArrayUtil.splitList(input, "");
		rules = new Rule[lists.get(0).size()];
		for (String line : lists.get(0)) {
			int index = Integer.parseInt(new XString(line).textBefore(":"));
			Rule rule = new Rule(new XString(line).textAfter(": "));
			rules[index] = rule;
		}
		values = lists.get(1);
	}
	
	protected Object part1() {
		return values.stream().filter(line -> rules[0].matches(line)).count();
	}
	
	protected Object part2() {
		rules[8] = new Rule("42 | 42 8");
		rules[11] = new Rule("42 31 | 42 11 31");
		return values.stream().filter(line -> rules[0].matches(line)).count();
	}
	
	private class Rule {
		// private List<Rule> ors = new ArrayList<>(), ands = new ArrayList<>();
		
		public Rule(String line) {
//			String[] orStrings = line.split("|");
//			for (String or : orStrings) {  
//				ors.add(new Rule(or));
//				String[] andStrings = or.split(" ");
//				for (String and : andStrings)
//					ands.add(new Rule(and));
//			}
		}
		
		public boolean matches(String line) {
			return true;
		}
	}
}