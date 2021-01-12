package dev.tomdotbat.tasty_bytes.order_system;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Order {
	public void addMeal(Meal meal) { //Adds a meal to the order
		mealList.add(meal);
	}
	
	public float calculateTotalCost() { //Calculates the cost of everything in the order
		float totalCost = 0f;
		
		for (int i = 0; i < mealList.size(); i++) {
			totalCost += mealList.get(i).getPrice();
		}
		
		return totalCost;
	}
	
	public String getFormattedItemList() { //Gets a formatted list of everything in the order
		int listLength = mealList.size();
		
		if (listLength < 1) return "";
		else if (listLength == 1) return mealList.get(0).getFormattedMeal();
		
		String formattedString = mealList.get(0).getFormattedMeal();
		for (int i = 1; i < listLength; i++) formattedString += ", " + mealList.get(i).getFormattedMeal();
		
		return formattedString;
	}
	
	public static String getFormattedItemList(JSONArray array) { //Gets a formatted list of everything in a previously saved order
		int listLength = array.length();
		
		if (array.length() < 1) return "";
		
		JSONObject meal = array.getJSONObject(0);
		
		if (listLength < 1) return "";
		else if (listLength == 1) {
			return Meal.getFormattedMeal(
					meal.getBoolean("isSupersized"),
					meal.getString("main"),
					meal.has("side") ? meal.getString("side") : null,
					meal.has("side") ? meal.getString("drink") : null
			);
		}
		
		String formattedString = Meal.getFormattedMeal(
				meal.getBoolean("isSupersized"),
				meal.getString("main"),
				meal.has("side") ? meal.getString("side") : null,
				meal.has("side") ? meal.getString("drink") : null
		);
		
		for (int i = 1; i < listLength; i++) {
			meal = array.getJSONObject(i);
			formattedString += ", " + Meal.getFormattedMeal(
					meal.getBoolean("isSupersized"),
					meal.getString("main"),
					meal.has("side") ? meal.getString("side") : null,
					meal.has("side") ? meal.getString("drink") : null
			);
		}
		
		return formattedString;
	}
	
	public ArrayList<Meal> getMealList() {return mealList;} //Meal list getter
	
	public JSONArray serialize() { //Converts the item into a JSON array for easy storage
		JSONArray array = new JSONArray();
		
		for (int i = 0; i < mealList.size(); i++) array.put(mealList.get(i).serialize()); //Serialise each into into a JSON object and add them to the array
		
		return array;
	}
	
	private ArrayList<Meal> mealList = new ArrayList<Meal>();
}
