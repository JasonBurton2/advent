package com.tms.advent.year2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tms.advent.util.Day;
import com.tms.advent.util.ListType;
import com.tms.advent.util.XString;

@ListType(Day21.Food.class)
public class Day21 extends Day<Day21.Food> {
	private Set<String> allIngredients = new HashSet<>();
	Map<String, Set<String>> possibleCauses = new HashMap<>();
	
	protected void onRun() {
		for (Food food : input) {
			allIngredients.addAll(food.ingredients);
			for (String allergen : food.allergens) {
				Set<String> causesForThis = possibleCauses.computeIfAbsent(allergen, (key) -> new HashSet<>(food.ingredients));
				for (Food otherFood : input)
					if (otherFood != food && otherFood.allergens.contains(allergen))
						for (String thisIngredient : food.ingredients)
							if (!otherFood.ingredients.contains(thisIngredient))
								causesForThis.remove(thisIngredient);
			}
		}
	}
	
	protected Object part1() {
		long result = 0;
		for (String ingredient : allIngredients) {
			boolean foundInCauses = false;
			for (Set<String> poss : possibleCauses.values()) 
				foundInCauses = foundInCauses || poss.contains(ingredient);
			if (!foundInCauses)
				for (Food foods : input)
					if (foods.ingredients.contains(ingredient))
						result++;;
		}
		return result;
	}
	
	protected Object part2() {
		boolean foundUnknown = true;
		while (foundUnknown) {
			foundUnknown = false;
			for (Map.Entry<String, Set<String>> entry : possibleCauses.entrySet())
				if (entry.getValue().size() == 1) {
					String unique = entry.getValue().iterator().next();
					for (Map.Entry<String, Set<String>> otherEntry : possibleCauses.entrySet())
						if (!entry.getKey().equals(otherEntry.getKey()))
							otherEntry.getValue().remove(unique);
				}
				else
					foundUnknown = true;
		}
		StringBuilder result = new StringBuilder();
		List<String> sortedAllergens = new ArrayList<>(possibleCauses.keySet());
		Collections.sort(sortedAllergens);
		for (String allergen : sortedAllergens)
			result.append(possibleCauses.get(allergen).iterator().next() + ",");
		return result.substring(0, result.length() - 1);
	}
	
	public static class Food {
		Set<String> ingredients, allergens;
		public Food(String line) {
			XString x = new XString(line);
			ingredients = new HashSet<>(Arrays.asList(x.textBefore(" (c").split(" ")));
			allergens = new HashSet<>(Arrays.asList(x.textBetween("(contains ", ")").split(", ")));
		}
	}
}