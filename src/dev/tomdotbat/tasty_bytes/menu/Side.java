package dev.tomdotbat.tasty_bytes.menu;

public class Side extends Item {
	public Side(String displayName, String searchTerm, String[] tags, float value, float largeValue) { //Side constructor
		name = displayName;
		searchableName = searchTerm;
		searchTags = tags;
		price = value;
		largePrice = largeValue;
	}
}
