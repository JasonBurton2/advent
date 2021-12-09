package com.tms.advent.year2020;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

import com.tms.advent.util.Day;

public class Day22 extends Day<String> {
	protected Object part1() {
		List<Integer>[] decks = initDecks();
		return getScore(decks[getWinner(decks, false)]);
	}
	
	protected Object part2() {
		List<Integer>[] decks = initDecks();
		return getScore(decks[getWinner(decks, true)]);
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	private int getWinner(List<Integer>[] decks, boolean recurse) {
		HashSet<List<List<Integer>>> previousStates = new HashSet<>();
		while (!decks[0].isEmpty() && !decks[1].isEmpty()) {
			if (recurse) {
				List<List<Integer>> newState = List.of(decks[0], decks[1]);
				if (previousStates.contains(newState))
					return 0;
				previousStates.add(newState);
			}
			int[] cards = new int[] {decks[0].remove(0), decks[1].remove(0)};
			int winner;
			if (recurse && decks[0].size() >= cards[0] && decks[1].size() >= cards[1])
				winner = getWinner(new List[] {new ArrayList(decks[0].subList(0, cards[0])), new ArrayList(decks[1].subList(0, cards[1]))}, true);
			else
				winner = cards[0] > cards[1] ? 0 : 1;
			decks[winner].add(cards[winner]);
			decks[winner].add(cards[winner == 0 ? 1 : 0]);
		}
		return decks[0].size() > 0 ? 0 : 1;
	}
	
	private int getScore(List<Integer> deck) {
		int score = 0;
		int index = 0;
		for (Integer card : deck)
			score += (deck.size() - index++) * card;
		return score;
	}

	@SuppressWarnings("unchecked")
	private List<Integer>[] initDecks() {
		List<Integer>[] result = new List[] {new ArrayList<Integer>(), new ArrayList<Integer>()};
		int index = -1;
		for (String line : input) {
			if (line.startsWith("Player"))
				index++;
			else if (line.length() > 0)
				result[index].add(Integer.parseInt(line));
		}
		return result;
	}
}