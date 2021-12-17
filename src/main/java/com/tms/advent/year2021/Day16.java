package com.tms.advent.year2021;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.tms.advent.util.Day;

public class Day16 extends Day<String> {
	private Packet outer;
	private String[] binStrings = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", 
		"1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111" };

	@Override
	protected void onRun() {
		String line = input.get(0);
		String binary = "";
		for (int i = 0; i < line.length(); i++)
			binary += binStrings[Integer.parseInt(line.substring(i, i + 1), 16)];
		outer = new Packet(binary, 0);
	}

	@Override
	public Object part1() {
		return outer.versionSum();
	}

	@Override
	public Object part2() {
		return outer.value();
	}

	private List<Packet> readPackets(String str) {
		List<Packet> result = new ArrayList<>();
		int index = 0;
		while (index < str.length()) {
			Packet p = new Packet(str, index);
			index = p.endIndex;
			result.add(p);
		}
		return result;
	}

	private class Packet {
		private int version, typeId, lengthTypeId, endIndex;
		private long literalValue;
		private List<Packet> subs = new ArrayList<>();

		private Packet(String str, int index) {
			version = Integer.parseInt(str.substring(index, index += 3), 2);
			typeId = Integer.parseInt(str.substring(index, index += 3), 2);
			if (typeId == 4) {
				endIndex = index;
				String literalString = "";
				boolean lastSegment = false;
				while (!lastSegment)
				do { 
					lastSegment = str.charAt(endIndex) == '0';
					literalString += str.substring(endIndex + 1, endIndex += 5);
				} while (!lastSegment);
				literalValue = new BigInteger(literalString, 2).longValue();
			} else {
				lengthTypeId = Integer.parseInt(str.substring(index, index += 1));
				if (lengthTypeId == 0) {
					int subPacketLength = new BigInteger(str.substring(index, index += 15), 2).intValue();
					endIndex = index + subPacketLength;
					subs = readPackets(str.substring(index, endIndex));
				}
				else {
					int subPacketLength = new BigInteger(str.substring(index, index += 11), 2).intValue();
					for (int i = 0; i < subPacketLength; i++) {
						Packet p = new Packet(str, index);
						index = p.endIndex;
						subs.add(p);
					}
					endIndex = index;
				}
			}
		}

		private long versionSum() {
			long result = version;
			for (Packet p : subs)
				result += p.versionSum();
			return result;
		}

		private long value() {
			switch (typeId) {
				case 0:	return subs.stream().mapToLong(Packet::value).sum();
				case 1: return subs.stream().mapToLong(Packet::value).reduce(1, (a, b) -> a * b);
				case 2: return subs.stream().mapToLong(Packet::value).min().getAsLong();
				case 3: return subs.stream().mapToLong(Packet::value).max().getAsLong();
				case 4: return literalValue;
				case 5: return subs.get(0).value() > subs.get(1).value() ? 1 : 0;
				case 6: return subs.get(0).value() < subs.get(1).value() ? 1 : 0;
				case 7: return subs.get(0).value() == subs.get(1).value() ? 1 : 0;
				default: throw new RuntimeException("Unrecognized type " + typeId);
			}
		}
	}
}