package dev.tomdotbat.tasty_bytes.menu;

import org.json.JSONObject;
import org.json.JSONArray;

public abstract class Item { //The base of all menu items	
	public Item(String name, String[] searchTags, float price, float largePrice) { //Item constructor
		this.name = name;
		this.searchTags = searchTags;
		this.price = price;
		this.largePrice = largePrice;
	}
	
	public String getName() {return name;} //Useful getters
	public float getPrice(boolean isLarge) {return isLarge ? largePrice : price;}
	
	public boolean testSearchTerm(String searchTerm) { //Item searching				
		for (String searchTag : searchTags) {
			if (searchTag.equals(searchTerm)) return true;
			if (searchTag.contains(searchTerm)) return true;
		}
				
		return false;
	}
	
	public JSONObject serialize() { //Converts the item into a JSON object for easy storage
		JSONObject json = new JSONObject();
		
		json.put("name", name);
		
		JSONArray tags = new JSONArray(searchTags); //Convert the tags array into a JSON array
		json.put("searchTags", tags);
		
		json.put("price", price);
		json.put("largePrice", largePrice);
		
		return json;
	}
	
	protected static String[] deserializeTags(JSONArray tags) {
		String[] searchTags = new String[tags.length()];
		
		for (int i = 0; i < searchTags.length; i++) {
			searchTags[i] = tags.getString(i);
		}
		
		return searchTags;
	}
	
	protected String name; //The display name of the item
	protected String[] searchTags; //Other related words to this item to help find it
	protected float price; //The price of the item normally
	protected float largePrice; //The price of the large version of the item
}
