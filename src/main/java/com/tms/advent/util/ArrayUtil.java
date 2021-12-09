package com.tms.advent.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {
	public static void shiftArray(Object array, int fromIndex, int toIndex, int shiftBy) {
		Object[] arr = (Object[])array;
		if (shiftBy > 0)
			for (int index = toIndex; index >= fromIndex; index--)
				arr[index + 1] = arr[index];
		else
			for (int index = fromIndex; index < toIndex; index++)
				arr[index - 1] = arr[index];
	}

	public static long sumArray(long[] array) {
		long result = 0;
		for (int i = 0; i < array.length; i++)
			result += array[i];
		return result;
	}

	@SuppressWarnings("unchecked") 
	static <T> T[][] clone(T[][] source) {
	    Class<? extends T[][]> type = (Class<? extends T[][]>) source.getClass();
	    T[][] copy = (T[][]) Array.newInstance(type.getComponentType(), source.length);

	    for (int i = 0; i < source.length; i++)
	        copy[i] = source[i].clone();
	    return copy;
	}
	
	public static Object[][][] copy(Object[][][] src) {
		Object[][][] target = new Object[src.length][src[0].length][src[0][0].length];
		copyTo(src, target);
		return target;
	}
	
	public static Object[][][] copyTo(Object[][][] src, Object[][][] target) {
		visit(src, (x, y, z) -> target[x][y][z] = src[x][y][z]);
		return target;
	}

	public static Object[][][][] copy(Object[][][][] src) {
		Object[][][][] target = new Object[src.length][src[0].length][src[0][0].length][src[0][0][0].length];
		copyTo(src, target);
		return target;
	}
	
	public static Object[][][][] copyTo(Object[][][][] src, Object[][][][] target) {
		visit(src, (x, y, z, w) -> target[x][y][z][w] = src[x][y][z][w]);
		return target;
	}	
	
	public static long sum(Object[][][] src, ThreeDValuer counter) {
		return sum(src, 0, src.length, 0, src[0].length, 0, src[0][0].length, counter);
	}
	
	public static long sum(Object[][][] src, int xStart, int xEnd, int yStart, int yEnd, int zStart, int zEnd, ThreeDValuer counter) {
		long result = 0;
		for (int x = Math.max(xStart, 0); x < Math.min(xEnd + 1, src.length); x++) 
			for (int y = Math.max(yStart, 0); y < Math.min(yEnd + 1, src[0].length); y++) 
				for (int z = Math.max(zStart, 0); z < Math.min(zEnd + 1, src[0][0].length); z++) 
					result += counter.getValue(x, y, z);
		return result;
	}

	public static long sum(Object[][][][] src, int xStart, int xEnd, int yStart, int yEnd, int zStart, int zEnd, int wStart, int wEnd, FourDValuer counter) {
		long result = 0;
		for (int x = Math.max(xStart, 0); x < Math.min(xEnd + 1, src.length); x++) 
			for (int y = Math.max(yStart, 0); y < Math.min(yEnd + 1, src[0].length); y++) 
				for (int z = Math.max(zStart, 0); z < Math.min(zEnd + 1, src[0][0].length); z++) 
					for (int w = Math.max(wStart, 0); w < Math.min(wEnd + 1, src[0][0][0].length); w++) 
						result += counter.getValue(x, y, z, w);
		return result;
	}
	
	public static long sum(Object[][][][] src, FourDValuer counter) {
		long result = 0;
		for (int x = 0; x < src.length; x++) 
			for (int y = 0; y < src[0].length; y++)
				for (int z = 0; z < src[0][0].length; z++)
					for (int w = 0; w < src[0][0][0].length; w++)
						result += counter.getValue(x, y, z, w);
		return result;
	}
	
	public static void visit(Object[][][] src, ThreeDVisitor visitor) {
		for (int x = 0; x < src.length; x++) 
			for (int y = 0; y < src[0].length; y++)
				for (int z = 0; z < src[0][0].length; z++)
					visitor.doIt(x, y, z);
	}
	
	
	public static void visit(Object[][][][] src, FourDVisitor visitor) {
		for (int x = 0; x < src.length; x++) 
			for (int y = 0; y < src[0].length; y++)
				for (int z = 0; z < src[0][0].length; z++)
					for (int w = 0; w < src[0][0][0].length; w++)
						visitor.doIt(x, y, z, w);
	}
	
	public static long sumAdjacent(Object[][][][] src, int toX, int toY, int toZ, int toW, FourDValuer visitor) {
		return sum(src, toX - 1, toX + 1, toY - 1, toY + 1, toZ - 1, toZ + 1, toW - 1, toW + 1, (x, y, z, w) -> {
			if (!(toX == x && toY == y && toZ == z && toW == w))
				return visitor.getValue(x, y, z, w);
			else 
				return 0; 
		});
	}
	
	public static Object[][][][] initialize(int xLength, int yLength, int zLength, int wLength, Object initValue) {
		Object[][][][] result = new Object[xLength][yLength][zLength][wLength];
		if(initValue != null)
			visit(result, (x, y, z, w) -> result[x][y][z][w] = initValue);
		return result; 
	}
	
	public static interface ThreeDVisitor {
		public void doIt(int x, int y, int z);
	}
	
	public static interface ThreeDValuer {
		public int getValue(int x, int y, int z);
	}
	
	public static interface FourDVisitor {
		public void doIt(int x, int y, int z, int w);
	}
	
	public static interface FourDValuer {
		public int getValue(int x, int y, int z, int w);
	}

	public static List<List<String>> splitList(List<String> list, String splitOn) {
		List<List<String>> result = new ArrayList<List<String>>();
		result.add(new ArrayList<String>());
		for (String line : list)
			if (line.equals(splitOn))
				result.add(new ArrayList<String>());
			else
				result.get(result.size() - 1).add(line);
		return result;
	}

	public static int indexOf(Object[] array, Object find) {
		for (int i = 0; i < array.length; i++)
			if (array[i].equals(find))
				return i;
		return -1;
	}

	public static int countNonNull(Object[] value) {
		int result = 0;
		for (Object o : value)
			if (o != null)
				result++;
		return result;
	}

	public static String toString(Object[] array) {
		StringBuilder result = new StringBuilder("[");
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null)
				result.append("null");
			else
				result.append(array[i].toString());
			if (i < array.length - 1)
				result.append(",");
		}
		result.append("]");
		return result.toString();		
	}
}
