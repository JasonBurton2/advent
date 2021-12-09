package com.tms.advent.year2021;

import java.util.ArrayList;
import java.util.List;

import com.tms.advent.util.Day;
import com.tms.advent.util.XString;

public class Day4 extends Day<String> {
	int[] numbers;
	List<Board> boards;

	protected void initDay() {
		numbers = new XString(input.get(0)).splitInts(",");
		boards = buildBoards();
	}

    public Object part1() {
		for (int number : numbers)
			for (Board board : boards)
				if (board.callNumber(number))
					return board.sumOfAllUncalledNumbers() * number;
		throw new RuntimeException("No winning board");
    }
    
    public Object part2() {
		for (int number : numbers)
			for (int i = boards.size() - 1; i >= 0; i--) {
				Board board = boards.get(i);
				if (board.callNumber(number)) {
					boards.remove(i);
					if (boards.size() == 0)
						return board.sumOfAllUncalledNumbers() * number;
				}
			}
		throw new RuntimeException("No losing board");
    }

	private List<Board> buildBoards() { 
		List<Board> result = new ArrayList<>();
		for (int i = 2; i < input.size(); i += 6)
			result.add(new Board(i));
		return result;
	}

	private class Board {
		private int[][] values = new int[5][5];
		private boolean[][] called = new boolean[5][5];

		private Board(int startIndex) {
			for (int i = 0; i < 5; i++) {
				values[i] = new XString(input.get(startIndex + i).trim()).splitInts(" +");
				called[i] = new boolean[5];
			}
		}

		private boolean callNumber(int number) {
			for (int line = 0; line < 5; line++)
				for (int col = 0; col < 5; col++)
					if (values[line][col] == number) {
						called[line][col] = true;
						return checkBingo(line, col);
					}
			return false;
		}

		private boolean checkBingo(int line, int col) {
			boolean result = true;
			for (int index = 0; index < 5; index++)
				if (!called[line][index])
					result = false;
			if (!result)
				for (int index = 0; index < 5; index++)
					if (!called[index][col])
						return false;
			return true;
		}

		private int sumOfAllUncalledNumbers() {
			int result = 0;
			for (int line = 0; line < 5; line++)
				for (int col = 0; col < 5; col++)
					if (!called[line][col])
						result += values[line][col];
			return result;
		}
	}
}
