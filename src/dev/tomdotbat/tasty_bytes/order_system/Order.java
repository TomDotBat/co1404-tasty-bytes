package dev.tomdotbat.tasty_bytes.order_system;

import java.util.ArrayList;

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
		
		for (int i = 1; i < listLength; i++) formattedString += (i == listLength - 2 ? " and " : ", ") + mealList.get(i).getFormattedMeal();
		
		return formattedString;
	}
	
	public ArrayList<Meal> getMealList() {return mealList;}

	private ArrayList<Meal> mealList = new ArrayList<Meal>();
}
