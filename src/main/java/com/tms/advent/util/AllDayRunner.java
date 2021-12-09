package com.tms.advent.util;

public class AllDayRunner {
	public static final String PKG = "com.tms.advent.year2021";
	public static void main(String[] args) {
		long start = System.nanoTime();
		int lastDay = 0;
		try {
			for (int i = 1; i <= 25; i++) {
				try {
					runDay(i);
					lastDay = i;
				} catch (ClassNotFoundException ex) {
					break;
				}
			}
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		long elapsed = (System.nanoTime() - start) / 1000000;
		System.out.println(lastDay + " days run in " + elapsed + "ms");
	}
	
	static void runDay(int dayNum) throws Exception {
		Day<?> day = (Day<?>)Class.forName(PKG + ".Day" + dayNum).getDeclaredConstructor().newInstance();
		System.out.println("**** Day " + dayNum + " ****");
		day.run();
		System.out.println("**********\r\n");
	}
}