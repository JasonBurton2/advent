package com.tms.advent.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class Day<T> {
	protected static String BASEDIR = System.getProperty("user.home") + "/git/advent";
	protected static String YEAR = "2021";
	private static final String MY_DOWNLOAD_COOKIE = "session=53616c7465645f5f415e78649018b843257a9e2c56b01502ef5a39dba39c6e1cb06a81c84c209773f97f852efba6a624";
	protected List<String> inputStrings;
	protected List<T> input;
	protected String inputFile;
	protected boolean debugEnabled = false;
	protected Class<?> inputClass = String.class;
	
	protected abstract Object part1();
	protected abstract Object part2();
	
	public Day() {
		boolean test = false;
		Annotation[] annotations = getClass().getAnnotations();
		for (Annotation a : annotations) {
			if (a.annotationType() == ListType.class)
				inputClass = ((ListType)a).value();
			else if (a.annotationType() == UseTestInput.class)
				test = true;
		}
		String justClass = getClass().getName();
		justClass = justClass.substring(justClass.lastIndexOf('.') + 1);
		File file;
		if (test)
			file = new File(BASEDIR + "/test-input/" + YEAR + "/" + justClass + ".txt");
		else
			file = new File(BASEDIR + "/input/" + YEAR + "/" + justClass + ".txt");
		if (!file.exists())
			download(justClass.substring(3), file);
		try {
			inputStrings = Files.readAllLines(file.toPath());
		}  catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	protected void makeInputList() {
		input = new ArrayList<>();
		for (int i = 0; i < inputStrings.size(); i++)
			input.add(createInputItem(i, inputStrings.get(i)));
	}
	
	@SuppressWarnings("unchecked") 
	protected T createInputItem(int index, String line) {
		try {
			Constructor<T> c = (Constructor<T>)inputClass.getDeclaredConstructor(String.class);
			return (T)c.newInstance(line);
		}
		catch (Exception ex) {
			try {
				return (T)inputClass.getDeclaredConstructor().newInstance();
			} catch (Exception ex2) {
				throw new RuntimeException(ex);
			}
		}
		
	}
	
	public long getMax(ValueListener<T> listener) {
		long max = 0;
		for (T item : input)
			max = Math.max(max, listener.getValue(item));
		return max;		
	}
	
	public long countInput(InputListener<T> listener) {
		long result = 0;
		for (T item : input)
			if (listener.include(item))
				result++;
		return result;
	}
	
	public void loop(int fromIndex, LoopListener listener) {
		for (int i = fromIndex; i < input.size(); i++)
			listener.evaluate(i);
	}
	
	protected void onRun() {
	}
	
	protected void initDay() {

	}
	
	protected void debug(String line, Object... args) {
		if (debugEnabled) {
			line = MessageFormat.format(line, args);
			System.out.println(line);
		}				
	}
	
	public void run() {
		makeInputList();
		onRun();
		long start = System.nanoTime();
		initDay();
		Object result = part1();
		double elapsed = (double)(System.nanoTime() - start) / 1000000;
		System.out.println("Part 1 finished in " + elapsed + "ms.  Result: " + result);
		
		start = System.nanoTime();
		initDay();
		result = part2();
		elapsed = (double)(System.nanoTime() - start) / 1000000;
		System.out.println("Part 2 finished in " + elapsed + "ms.  Result: " + result);
	}
	
	public void download(String day, File file) {
		try {
	        URL url = new URL("https://adventofcode.com/" + YEAR + "/day/" + day + "/input");
	        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        	con.addRequestProperty("Content-Type", "text/html");
            con.setRequestMethod("GET");
        	con.addRequestProperty("Cookie", MY_DOWNLOAD_COOKIE); 
            con.connect();
            if (con.getResponseCode() == 200) {
            	try (FileOutputStream output = new FileOutputStream(file)) {
	            	byte[] buffer = new byte[32768];
	            	int count = 0;
	            	while ((count = con.getInputStream().read(buffer, 0, 32768)) >= 0)
	            		output.write(buffer, 0, count);
            	}           	
            }
            else
            	System.out.println("Download returned response code " + con.getResponseCode());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public interface InputListener<T> {
		public boolean include(T item);
	}
	
	public interface ValueListener<T> {
		public long getValue(T item);
	}
	
	public interface LoopListener {
		public void evaluate(int index);
	}
}