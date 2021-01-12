package dev.tomdotbat.tasty_bytes.order_system;

import org.json.JSONObject;

import dev.tomdotbat.tasty_bytes.menu.*;

public class Meal {
	public Meal(Main main, Side side, Drink drink, boolean isSupersized) { //Meal constructor
		this.main = main;
		this.side = side;
		this.drink = drink;
		this.isSupersized = isSupersized;
	}
	
	public void setSupersize(boolean isSupersized) {this.isSupersized = isSupersized;} //Useful setters
	public void setMain(Main main) {this.main = main;}
	public void setSide(Side side) {this.side = side;}
	public void setDrink(Drink drink) {this.drink = drink;}
	
	public static String getFormattedMeal(boolean isSupersized, String main, String side, String drink) { //Format a meal from the data given
		if (main == null) return "";
		
		if (isSupersized) {
			if (side == null) {
				if (drink == null) return "large " + main;
				else return "large " + main + " with a drink of large " + drink;
			}
			else {
				if (drink == null) return "large " + main + " with a side of large " + side;
				else return "large " + main + " with large " + side + " and a drink of large " + drink;
			}
		}
		else {
			if (side == null) {
				if (drink == null) return main;
				else return main + " with a drink of " +drink;
			}
			else {
				if (drink == null) return main + " with a side of " + side;
				else return main + " with " + side + " and a drink of " + drink;
			}
		}		
	}
	
	public String getFormattedMeal() { //Format the selected meal into a readable string
		return getFormattedMeal(isSupersized,
				main.getName(),
				side == null ? null : side.getName(),
				drink == null ? null : drink.getName()
		);
	}
	
	public float getPrice() { //Calculates the meal's overall price taking super-size into account
		float totalPrice = 0f;

		if (main != null) totalPrice += main.getPrice(isSupersized);
		if (side != null) totalPrice += side.getPrice(isSupersized);
		if (drink != null) totalPrice += drink.getPrice(isSupersized);
		
		return totalPrice;
	}
	
	public boolean compareMains(Main compareMain) { //Returns true if two mains are the same
		return main.getName() == compareMain.getName();
	}
	
	public JSONObject serialize() { //Converts the item into a JSON object for easy storage
		JSONObject json = new JSONObject();

		if (main != null) json.put("main", main.getName());
		if (side != null) json.put("side", side.getName());
		if (drink != null) json.put("drink", drink.getName());
		json.put("isSupersized", isSupersized);
		
		return json;
	}
	
	private Main main;
	private Side side;
	private Drink drink;
	private boolean isSupersized = false;
}
