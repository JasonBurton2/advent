package com.tms.advent.year2020;

import java.util.List;
import java.util.ArrayList;

import com.tms.advent.util.Day;
import com.tms.advent.util.XString;

public class Day16 extends Day<String> {
	int[] yourTicket;
	List<Rule> rules = new ArrayList<>();
	List<int[]> tickets= new ArrayList<>(), validTickets = new ArrayList<>();

	protected void onRun() {
		int section = 0;
		for (String line : input) {
			if (line.length() == 0)
				section++;
			else {
				if (section == 0)
					rules.add(new Rule(line));
				else if (section == 1 && !line.startsWith("your "))
					yourTicket = new XString(line).splitInts(",");
				else if (section == 2 && !line.startsWith("nearby "))
					tickets.add(new XString(line).splitInts(","));
			}
		}
		validTickets.add(yourTicket);
	}
	
	protected Object part1() {
		int invalidSum = 0;
		for (int ticketIndex = 0; ticketIndex < tickets.size(); ticketIndex++) {
			int[] ticket = tickets.get(ticketIndex);
			boolean allFieldsValid = true;
			for (int i = 0; i < ticket.length; i++) {
				boolean valid = false;
				for (Rule rule : rules)
					if (rule.inRange(ticket[i]))
						valid = true;
				if (!valid) {
					invalidSum += ticket[i];
					allFieldsValid = false;
				}
			}
			if (allFieldsValid)
				validTickets.add(ticket);
		}
		return invalidSum;
	}
	
	protected Object part2() {
		int unresolvedFields;
		do {
			unresolvedFields = 0;
			for (int i = 0; i < yourTicket.length; i++) {
				List<Rule> possible = new ArrayList<>();
				for (Rule rule : rules) {
					if (rule.fieldIndex < 0) {
						boolean ruleIsValid = true;
						for (int[] ticket : validTickets)
							if (!rule.inRange(ticket[i]))
								ruleIsValid = false;
						if (ruleIsValid)
							possible.add(rule);
					}
				}
				if (possible.size() == 1)
					possible.get(0).fieldIndex = i;
				else if (possible.size() > 0)
					unresolvedFields++;
			}
		} while (unresolvedFields > 0);
		long result = 1;
		for (Rule rule : rules)
			if (rule.name.startsWith("departure"))
				result *= yourTicket[rule.fieldIndex];
		return result;
	}

	private static class Rule {
		private String name;
		private int[] range1, range2;
		private int fieldIndex = -1;
		
		public Rule(String line) {
			name = new XString(line).textBefore(":");
			line = new XString(line).textAfter(": ");
			range1 = parseRange(line.substring(0, line.indexOf(' ')));
			range2 = parseRange(line.substring(line.lastIndexOf(' ') + 1));
		}
		
		private boolean inRange(int value) {
			return (value >= range1[0] && value <= range1[1]) ||
				(value >= range2[0] && value <= range2[1]);
		}
		
		private int[] parseRange(String text) {
			int[] result = new int[2];
			result[0] = Integer.parseInt(new XString(text).textBefore("-"));
			result[1] = Integer.parseInt(new XString(text).textAfter("-"));
			return result;
		}
	}
}