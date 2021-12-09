package com.tms.advent.year2020;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import com.tms.advent.util.Day;

public class Day18 extends Day<String> {
	protected Object part1() {
		return input.stream().mapToLong(line -> evaluateExpression(line, true)).sum();
	}
	
	protected Object part2() {
		return input.stream().mapToLong(line -> evaluateExpression(line, false)).sum();
	}
	
	private long evaluateExpression(String exp, boolean leftToRight) {
		int parenIndex = 0;
		while ((parenIndex = exp.indexOf('(')) >= 0) {
			 int depth = 1;
			 for (int i = parenIndex + 1; i < exp.length(); i++)
				 if (exp.charAt(i) == '(')
					 depth++;
				 else if (exp.charAt(i) == ')') {
					 depth--;
					 if (depth == 0) {
						 long inner = evaluateExpression(exp.substring(parenIndex + 1, i), leftToRight);
						 exp = exp.substring(0, parenIndex) + inner + exp.substring(i + 1);
						 break;
					 }
				 }
		}
		List<String> tokens = new ArrayList<>(Arrays.asList(exp.split(" ")));
		if (!leftToRight)
			for (int i = 0; i < tokens.size() - 1; i++)
				if (tokens.get(i + 1).equals("+")) {
					tokens.set(i, Long.toString(Long.parseLong(tokens.get(i)) + Long.parseLong(tokens.get(i + 2))));
					tokens.remove(i + 1);
					tokens.remove(i + 1);
					i--;
				}
	
		long result = Long.parseLong(tokens.get(0));
		for (int i = 1; i < tokens.size(); i += 2) {
			if (tokens.get(i).equals("*"))
				result *= Long.parseLong(tokens.get(i + 1));
			else
				result += Long.parseLong(tokens.get(i + 1));
		}
		return result;
	}
}