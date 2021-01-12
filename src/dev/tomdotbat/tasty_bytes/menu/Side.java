package dev.tomdotbat.tasty_bytes.menu;

import org.json.JSONObject;

public class Side extends Item {
	public Side(String name, String[] searchTags, float price, float largePrice) { //Side constructor
		super(name, searchTags, price, largePrice);
	}

	public static Side deserialize(JSONObject obj) { //Converts a JSON object back into a side object
		return new Side(
				obj.getString("name"),
				deserializeTags(obj.getJSONArray("searchTags")),
				obj.getFloat("price"),
				obj.getFloat("largePrice")
		);
	}
}
