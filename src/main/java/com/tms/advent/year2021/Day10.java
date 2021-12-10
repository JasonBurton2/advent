package com.tms.advent.year2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.tms.advent.util.Day;

public class Day10 extends Day<String> {
	private String opens = "([{<";
	private String closes = ")]}>";

    public Object part1() {
		int result = 0;
		for (String line : input)
			result += new ParsedLine(line).invalidSyntaxScore;
		return result;
    }

    public Object part2() {
		List<Long> scores = new ArrayList<>();
		for (String line : input) {
			ParsedLine parsed = new ParsedLine(line);
			if (parsed.completionScore > 0)
				scores.add(parsed.completionScore);			
		}
		Collections.sort(scores);
		return scores.get(scores.size() / 2);
    }

	private class ParsedLine {
	 	private long invalidSyntaxScore, completionScore;
		private Stack<Integer> stack = new Stack<>();

		private ParsedLine(String line) {
			int[] syntaxScores = new int[] {3, 57, 1197, 25137};
			for (int i = 0; i < line.length(); i++) {
				String c = line.substring(i, i+ 1);
				int openIndex = opens.indexOf(c);
				if (openIndex >= 0)
					stack.push(openIndex);
				else {
					int closeIndex = closes.indexOf(c);
					if (closeIndex == stack.peek())
						stack.pop();
					else {
						invalidSyntaxScore = syntaxScores[closeIndex];
						return;
					}
				}
			}
			while (stack.size() > 0)
				completionScore = completionScore * 5 + (stack.pop() + 1);
		}
	}
}