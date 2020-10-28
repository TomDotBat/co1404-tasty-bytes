package dev.tomdotbat.tasty_bytes.menu;

import java.util.ArrayList;

public class Builder {
	public static void buildMenu() { //Create all of our mains, sides and drinks
		Side[] burgerSides = { //Define all the sides available for burgers
			new Side("Chips", "chips", new String[] {"fries"}, .8f, 1.2f), //DisplayName, SearchableName, SearchTags, NormalPrice, LargePrice
			new Side("Wedges", "wedges", new String[] {}, .8f, 1.2f),
			new Side("Salad", "salad", new String[] {"greens"}, .8f, 1.2f)
		};

		mainsList.add(new Main("Vegetable Burger", "vegetable burger", new String[] {"vegetable", "veggie", "veg"}, 3f, 3.4f, burgerSides)); //Add all of our mains
		mainsList.add(new Main("Chicken Burger", "chicken burger", new String[] {"chicken", "chick"}, 2.6f, 3f, burgerSides)); //DisplayName, SearchableName, SearchTags, NormalPrice, LargePrice, AvailableSides
		mainsList.add(new Main("Cheese Burger", "cheese burger", new String[] {"cheese", "cheddar", "chese"}, 3.2f, 3.6f, burgerSides));

		drinksList.add(new Drink("Coke", "coke", new String[] {"cola", "pepsi"}, .75f, .9f)); //Add all of our mains
		drinksList.add(new Drink("Water", "water", new String[] {}, .0f, .0f)); //DisplayName, SearchableName, SearchTags, NormalPrice, LargePrice
		drinksList.add(new Drink("Coffee", "coffee", new String[] {"latte", "cappuccino", "americano"}, 1.2f, 1.6f));
	}

	public static String getFormattedMainsList() { //Creates a formatted list of mains
		String formattedString = mainsList.get(0).getName();
		
		for (int i = 1; i < mainsList.size(); i++) {
			formattedString += ", " + mainsList.get(i).getName();
		}
		
		return formattedString;
	}

	public static String getFormattedDrinksList() { //Creates a formatted list of drinks
		String formattedString = drinksList.get(0).getName();
		
		for (int i = 1; i < drinksList.size(); i++) {
			formattedString += ", " + drinksList.get(i).getName();
		}
		
		return formattedString;
	}
	
	public static ArrayList<Main> getMainsList() {return mainsList;} //Useful getters
	public static ArrayList<Drink> getDrinksList() {return drinksList;}
	
	private static ArrayList<Main> mainsList = new ArrayList<Main>();
	private static ArrayList<Drink> drinksList = new ArrayList<Drink>();
}
