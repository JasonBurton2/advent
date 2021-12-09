package com.tms.advent.year2020;

import java.util.ArrayList;
import java.util.List;
import com.tms.advent.util.Day;
import com.tms.advent.util.ListType;
import com.tms.advent.util.XString;

@ListType(Day8.Instruction.class)
public class Day8 extends Day<Day8.Instruction> {
	private enum Op {
		ACC, JMP, NOP
	}
	
	protected Object part1() {
		Executor exec = new Executor();
		exec.execute(input);
		return exec.accumulator;
	}

	protected Object part2() {
		for (int swap = 0; swap < input.size(); swap++) {
			Instruction oldInstruction = input.get(swap);
			if (oldInstruction.op != Op.ACC) {
				Executor exec = new Executor();
				List<Instruction> list = new ArrayList<>();
				list.addAll(input);
				Instruction newInstruction = new Instruction();
				newInstruction.arg = oldInstruction.arg;
				if (oldInstruction.op == Op.NOP)
					newInstruction.op = Op.JMP;
				else
					newInstruction.op = Op.NOP;
				list.set(swap, newInstruction);
				boolean result = exec.execute(list);
				if (result)
					return exec.accumulator;
			}
		}
		return null;
	}
	
	public static class Instruction {
		private Op op;
		private int arg;
		
		private Instruction() {
		}
		
		public Instruction(String line) {
			String opString = new XString(line).textBefore(" ");
			arg = Integer.parseInt(new XString(line).textAfter(" "));
			op = Op.valueOf(opString.toUpperCase());
		}
	}
	
	private class Executor {
		private int accumulator = 0;
		
		private boolean execute(List<Instruction> instructions) {
			boolean[] executed = new boolean[instructions.size()]; 
			int index = 0;
			while (true) {
				if (index == instructions.size())
					return true;
				if (executed[index])
					return false;
				executed[index] = true;
				Instruction inst = instructions.get(index);
				if (inst.op == Op.JMP)
					index += inst.arg;
				else {
					if (inst.op == Op.ACC) 
						accumulator += inst.arg;
					index ++;
				}
			}
		}
	}
}