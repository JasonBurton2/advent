package com.tms.advent.year2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tms.advent.util.Day;
import com.tms.advent.util.ListType;
import com.tms.advent.util.XString;

@ListType(Day4.Passport.class)
public class Day4 extends Day<Day4.Passport> {
	protected Object part1() {
		return countInput((passport) -> passport.hasRequiredFields());
	}

	protected Object part2() {
		return countInput((passport) -> passport.hasRequiredFields() && passport.hasValidFieldValues());
	}
	
	@Override
	protected void makeInputList() {
		input = new ArrayList<>();
		input.add(new Passport());
		for (String line : inputStrings) {
			if (line.trim().length() == 0)
				input.add(new Passport());
			else
				input.get(input.size() - 1).addFields(line);
		}
	}
	
	public static class Passport {
		private static final Field[] fieldList = {
			new Field("byr", true, (value) -> value.matches("^(19[2-9][0-9]|200[0-2])$")),				
			new Field("iyr", true, (value) -> value.matches("^(201[0-9]|2020)$")), 
			new Field("eyr", true, (value) -> value.matches("^(202[0-9]|2030)$")), 
			new Field("hgt", true, (value) -> value.matches("(^(1[5-8][0-9]|19[0-3])cm$)|(^(59|6[0-9]|7[0-6])in$)")),
			new Field("hcl", true, (value) -> value.matches("^#(\\d|[a-f])+$")),
			new Field("ecl", true, (value) -> value.matches("amb|blu|brn|gry|grn|hzl|oth")),
			new Field("pid", true, (value) -> value.matches("^[0-9]{9}$")),
			new Field("cid", false, null) 
		};
		private Map<String, String> fields = new HashMap<>();
		
		public void addFields(String line) {
			String[] fields = line.split(" ");
			for (String field : fields) {
				String name = new XString(field).textBefore(":");
				String value = new XString(field).textAfter(":");
				this.fields.put(name.trim(), value.trim());
			}
		}
		
		public boolean hasRequiredFields() {
			for (Field field : fieldList)
				if (field.required && !fields.containsKey(field.name))
					return false;
			return true;			
		}
		
		public boolean hasValidFieldValues() { 
			for (Field field : fieldList)
				try { 
					if (field.validator != null && !field.validator.isValid(fields.get(field.name)))
						return false;
				}
				catch (Exception ex) {
					return false;
				}
			return true;			
		}
	}
	
	private static class Field { 
		private String name;
		private boolean required;
		private Validator validator;
		
		public Field(String name, boolean required, Validator validator) {
			this.name = name;
			this.required = required;
			this.validator = validator;
		}
	}
	
	public interface Validator {
		public boolean isValid(String value);
	}
}