package dev.tomdotbat.tasty_bytes.menu;

public class Drink extends Item {
	public Drink(String displayName, String searchTerm, String[] tags, float value, float largeValue) { //Drink constructor
		name = displayName;
		searchableName = searchTerm;
		searchTags = tags;
		price = value;
		largePrice = largeValue;
	}
}
