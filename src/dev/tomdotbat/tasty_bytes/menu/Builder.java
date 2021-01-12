package dev.tomdotbat.tasty_bytes.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.JSONArray;

public class Builder {
	private static final String menuFileName = "menu.json";
	
	public static void buildMenu() { //Create all of our mains, sides and drinks
		File file = new File(menuFileName);
		if (!file.exists() || !file.canRead() || file.isDirectory()) { 
			return; //If the file doesn't exist, can't be read, or is somehow a directory, return
		}

		Scanner fileReader;
		JSONObject menuData;
		try {
			fileReader = new Scanner(file);
			
			String jsonData = "";
			while (fileReader.hasNextLine()) {
				jsonData += fileReader.nextLine();
			}
			
			fileReader.close();
			menuData = new JSONObject(jsonData);
		}
		catch (Exception ex) {return;}
		
		JSONArray mains = menuData.getJSONArray("mains"); //Deserialise all the mains (+sides) and add them to the menu
		for (int i = 0; i < mains.length(); i++) mainsList.add(Main.deserialize(mains.getJSONObject(i)));

		JSONArray drinks = menuData.getJSONArray("drinks"); //Deserialise all the drinks and add them to the menu
		for (int i = 0; i < mains.length(); i++) drinksList.add(Drink.deserialize(drinks.getJSONObject(i)));
		
		/* Menu items can be built into the program directly by doing the following:
		
		Side[] burgerSides = { //Define all the sides available for burgers
			new Side("Chips", new String[] {"chips", "fries"}, .8f, 1.2f), //DisplayName, SearchTags, NormalPrice, LargePrice
			new Side("Wedges", new String[] {"wedges"}, .8f, 1.2f),
			new Side("Salad", new String[] {"salad", "greens"}, .8f, 1.2f)
		};

		mainsList.add(new Main("Vegetable Burger", new String[] {"vegetable", "veggie", "veg"}, 3f, 3.4f, burgerSides)); //Add all of our mains
		mainsList.add(new Main("Chicken Burger", new String[] {"chicken", "chick"}, 2.6f, 3f, burgerSides)); //DisplayName, SearchTags, NormalPrice, LargePrice, AvailableSides
		mainsList.add(new Main("Cheese Burger", new String[] {"cheese", "cheddar", "chese"}, 3.2f, 3.6f, burgerSides));

		drinksList.add(new Drink("Coke", new String[] {"coke", "cola", "pepsi"}, .75f, .9f)); //Add all of our mains
		drinksList.add(new Drink("Water", new String[] {"water"}, .0f, .0f)); //DisplayName, SearchTags, NormalPrice, LargePrice
		drinksList.add(new Drink("Coffee", new String[] {"coffee", "coffe", "cofee", "cofe", "latte", "cappuccino", "americano"}, 1.2f, 1.6f));
		*/
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
