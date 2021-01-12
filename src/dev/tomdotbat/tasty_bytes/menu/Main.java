package dev.tomdotbat.tasty_bytes.menu;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main extends Item {
	public Main(String name, String[] searchTags, float price, float largePrice, Side[] availableSides) { //Main constructor
		super(name, searchTags, price, largePrice);
		this.availableSides = availableSides;
	}
	
	public String getFormattedAvailableSides() { //Creates a formatted list of available sides
		String formattedString = availableSides[0].getName();
		
		for (int i = 1; i < availableSides.length; i++) {
			formattedString += ", " + availableSides[i].getName();
		}
		
		return formattedString;
	}
	
	public Side checkForValidSide(String input) { //Finds a side from the message the user gave
		String[] splitInput = input.split(" ");
		for (int i = 0; i < splitInput.length; i++) {
			if (splitInput[i].length() < 3) continue; //Skips over words like "a"
			for (int j = 0; j < availableSides.length; j++) if (availableSides[j].testSearchTerm(splitInput[i])) return availableSides[j];
		}
		
		return null;
	}
	
	public JSONObject serialize() { //Converts the item into a JSON object for easy storage
		JSONObject json = super.serialize(); //Run the inherited serialisation function

		JSONArray sides = new JSONArray();
		for (int i = 0; i < availableSides.length; i++) sides.put(availableSides[i].serialize()); //Serialise each into into a JSON object and add them to the array
		
		json.put("availableSides", sides);
		
		return json;
	}
	
	public static Main deserialize(JSONObject obj) { //Converts a JSON object back into a main object
		Side[] sides = new Side[obj.getJSONArray("availableSides").length()];
		
		for (int i = 0; i < sides.length; i++) {
			sides[i] = Side.deserialize(obj.getJSONArray("availableSides").getJSONObject(i));
		}
		
		return new Main(
				obj.getString("name"),
				deserializeTags(obj.getJSONArray("searchTags")),
				obj.getFloat("price"),
				obj.getFloat("largePrice"),
				sides
		);
	}
	
	private Side[] availableSides;
}
