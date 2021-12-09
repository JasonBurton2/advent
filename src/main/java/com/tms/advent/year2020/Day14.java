package com.tms.advent.year2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tms.advent.util.Day;
import com.tms.advent.util.XString;

public class Day14 extends Day<String> {
	protected Object part1() {
		HashMap<Long, Long> addressSpace = new HashMap<>();
		String mask = null;
		for (String line : input) {
			if (line.startsWith("mask = "))
				mask = line.substring(7);
			else {
				XString xLine = new XString(line); 
				long address = Long.parseLong(xLine.textBetween("[", "]"));
				long value = Long.parseLong(xLine.textAfter(" = "));
				addressSpace.put(address, applyMask(value, mask));
			}
		}
		return sumSpace(addressSpace);
	}
	
	protected Object part2() {
		HashMap<Long, Long> addressSpace = new HashMap<>();
		List<String> masks = new ArrayList<>();		
		for (String line : input) {
			if (line.startsWith("mask = ")) {
				String mask = line.substring(7).replace('0', '!');
				masks.clear();
				fillPossibleMasks(masks, mask, 0);
			} else {
				XString xLine = new XString(line); 
				long address = Long.parseLong(xLine.textBetween("[", "]"));
				long value = Long.parseLong(xLine.textAfter(" = "));
				for (String mask : masks)
					addressSpace.put(applyMask(address, mask), value);
			}
		}
		return sumSpace(addressSpace);
	}
	
	private long applyMask(long value, String mask) {
		long result = value;
	    for (int i = 0; i < mask.length(); i++) {
	    	int bit = 63 - (i + 28);
	    	char c = mask.charAt(i); 
	    	if (c == '0')
	    		result &= ~(1l << bit);
	    	else if (c == '1')
	    		result |= (1l << bit);	    	
	    }
	    return result;
	}
	
	private long sumSpace(HashMap<Long, Long> addressSpace) {
		long result = 0;
		for (Long value : addressSpace.values())
			result += value;
		return result;		
	}		
	
	private void fillPossibleMasks(List<String> masks, String mask, int startIndex) {
		int xPos = mask.indexOf('X');
		if (xPos < 0)
			masks.add(mask);
		else {
			XString xStr = new XString(mask);
    		fillPossibleMasks(masks, xStr.replaceRange(xPos, xPos, "0"), xPos + 1);
    		fillPossibleMasks(masks, xStr.replaceRange(xPos, xPos, "1"), xPos + 1);
    		return;
		}
	}
}