package dev.tomdotbat.tasty_bytes.order_system;

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
	public void setDrink(Drink drink) {this.drink =  drink;}
	
	public String getFormattedMeal() { //Format the selected meal into a readable string
		if (main == null) return "";
		
		if (isSupersized) {
			if (side == null) {
				if (drink == null) return "large " + main.getName();
				else return "large " + main.getName() + " with a drink of large " + drink.getName();
			}
			else {
				if (drink == null) return "large " + main.getName() + " with a side of large " + side.getName();
				else return "large " + main.getName() + " with large " + side.getName() + " and a drink of large " + drink.getName();
			}
		}
		else {
			if (side == null) {
				if (drink == null) return main.getName();
				else return main.getName() + " with a drink of " + drink.getName();
			}
			else {
				if (drink == null) return main.getName() + " with a side of " + side.getName();
				else return main.getName() + " with " + side.getName() + " and a drink of " + drink.getName();
			}
		}		
	}
	
	public float getPrice() { //Calculates the meal's overall price taking super-size into account
		float totalPrice = 0;

		if (main != null) totalPrice += main.getPrice(isSupersized);
		if (side != null) totalPrice += side.getPrice(isSupersized);
		if (drink != null) totalPrice += drink.getPrice(isSupersized);
		
		return totalPrice;
	}
	
	public boolean compareMains(Main compareMain) {
		return main.getName() == compareMain.getName();
	}
	
	private Main main;
	private Side side;
	private Drink drink;
	private boolean isSupersized = false;
}
