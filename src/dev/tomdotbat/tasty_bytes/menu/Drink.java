package dev.tomdotbat.tasty_bytes.menu;

import org.json.JSONObject;

public class Drink extends Item {
	public Drink(String name, String[] searchTags, float price, float largePrice) { //Drink constructor
		super(name, searchTags, price, largePrice);
	}

	public static Drink deserialize(JSONObject obj) { //Converts a JSON object back into a drink object
		return new Drink(
				obj.getString("name"),
				deserializeTags(obj.getJSONArray("searchTags")),
				obj.getFloat("price"),
				obj.getFloat("largePrice")
		);
	}
}
