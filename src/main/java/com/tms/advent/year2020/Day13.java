package com.tms.advent.year2020;

import java.util.ArrayList;
import java.util.List;

import com.tms.advent.util.Day;

public class Day13 extends Day<String> {
	long earliest;
	List<String> buses;
	@Override
	protected void makeInputList() {
		super.makeInputList();
		buses = new ArrayList<>();
		earliest = Long.parseLong(input.get(0));
		String[] busStrings = input.get(1).split(",");
		for (String s : busStrings)
			buses.add(s);
	}
	protected Object part1() {
		long bestDepart = Integer.MAX_VALUE;
		long busId = 0;
		for (String s : buses) {
			if (s.equals("x"))
				continue;
			int i = Integer.parseInt(s);
			long thisDepart = ((earliest / i) * i) + i;
			if (thisDepart < bestDepart) {
				busId = i;
				bestDepart = thisDepart;
			}
		}
		return busId * (bestDepart - earliest);
	}
	
	protected Object part2() {
		long[] numbers = new long[buses.size()], remainder = new long[buses.size()];
		int index = 0;
		for (int i = 0; i < numbers.length; i++)
			if (!buses.get(i).equals("x")) {
				numbers[index] = Long.parseLong(buses.get(i));
				remainder[index++] = i;
			}
		return findMinX(numbers, remainder, index);		
	}
	
// I ripped this code off from https://www.geeksforgeeks.org/chinese-remainder-theorem-set-2-implementation/		
	// A Java program to demonstrate working of Chinise remainder Theorem 
		// Returns modulo inverse of a with respect to m using extended 
		// Euclid Algorithm. Refer below post for details: 
		// https://www.geeksforgeeks.org/multiplicative-inverse-under-modulo-m/ 
	static long inv(long a, long m) 
	{ 
		long m0 = m, t, q; 
		long x0 = 0, x1 = 1; 
	
		if (m == 1) 
		return 0; 
	
		// Apply extended Euclid Algorithm 
		while (a > 1) 
		{ 
			// q is quotient 
			q = a / m; 
	
			t = m; 
	
			// m is remainder now, process 
			// same as euclid's algo 
			m = a % m;a = t; 
	
			t = x0; 
	
			x0 = x1 - q * x0; 
	
			x1 = t; 
		} 
	
		// Make x1 positive 
		if (x1 < 0) 
			x1 += m0; 
	
		return x1; 
	} 
	
	// k is size of num[] and rem[]. 
	// Returns the smallest number 
	// x such that: 
	// x % num[0] = rem[0], 
	// x % num[1] = rem[1], 
	// .................. 
	// x % num[k-2] = rem[k-1] 
	// Assumption: Numbers in num[] are pairwise 
	// coprime (gcd for every pair is 1) 
	static long findMinX(long num[], long rem[], int k) 
	{ 
		// Compute product of all numbers 
		long prod = 1; 
		for (int i = 0; i < k; i++) 
			prod *= num[i]; 
	
		// Initialize result 
		int result = 0; 
	
		// Apply above formula 
		for (int i = 0; i < k; i++) 
		{ 
			long pp = prod / num[i]; 
			result += rem[i] * inv(pp, num[i]) * pp; 
		} 
	
		return result % prod; 
	} 
}