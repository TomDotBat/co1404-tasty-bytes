package dev.tomdotbat.tasty_bytes.order_system;

import java.util.ArrayList;

import dev.tomdotbat.tasty_bytes.menu.Builder;
import dev.tomdotbat.tasty_bytes.menu.Drink;
import dev.tomdotbat.tasty_bytes.menu.Main;

public class MessageHandler {
	public static boolean handleInput(String input) { //Input loop handler
		input = input.toLowerCase(); //Handle the input in lower case to avoid problems with casing
		
		if (input.contains(", ")) { //Handle each part of a comma separated message one by one
			String[] splitInput = input.split(", ");

			for (int i = 0; i < splitInput.length; i++) if (!handleInput(splitInput[i])) return false;			
			return true;
		}
		
		if (currentOrder == null) return orderOrExit(input); //The user hasn't created an order yet, we should give them the option to make one or exit
		return placeOrder(input); //Start/continue taking the user's order
	}
	
	private static Order currentOrder;
	private static String lastPath = "";
	
	private static boolean orderOrExit(String input) { //Starting point, the user can only exit or order at this point
		boolean shouldHelp = lastPath.equals("orderOrExit"); //Print a help message if the user fails to give a valid answer
		lastPath = "orderOrExit";
		
		if (checkForOptionAliases(input, new String[] {"exit", "quit", "cancel", "leave", "can't", "cant", "wrong"})) { //The user wants to leave
			printMessage("** Thank you for calling, have a good day.");
			return false; //This will cause the program to exit
		}
		else if (checkForOptionAliases(input, new String[] {"yes", "place", "order"})) { //The user wants to place an order, send them to that method
			currentOrder = new Order();
			placeOrder("");
		}
		else { //Initial message/invalid option
			printMessage("** Welcome to Tasty Bytes, how can I help you?"); //Print the welcome message on startup/invalid input
			if (shouldHelp) printMessage("?? To proceed, type something like \"I'd like to place an order.\" or \"Wrong number!\".");
		}
		
		return true;
	}
	
	private static boolean placeOrder(String input) { //Order placement, the user can add and remove items from their order, cancel or finalise it from here
		boolean shouldHelp = lastPath.equals("placeOrder");
		lastPath = "placeOrder";
		
		if (shouldHelp) {			
			if (checkForOptionAliases(input, new String[] {"that's it", "thanks", "thats", "everything", "finish", "finalise"})) { //The user wants to finish the order
				String response = "** Excellent! I have placed your order for:\n";
				ArrayList<Meal> mealList = currentOrder.getMealList();
				
				for (int i = 0; i < mealList.size(); i++) { //List all of the meals that were ordered
					response += mealList.get(i).getFormattedMeal() + (i == mealList.size() - 1 ? "." : ", ");
				}
				
				response += "\nIn total that will be " + String.format("£%,.2f", currentOrder.calculateTotalCost()) + ". Thank you for calling.";
				
				printMessage(response);
				return false;
			}
		}
		
		if (checkForOptionAliases(input, new String[] {"cancel", "nevermind", "exit", "leave"})) { //The user would like to cancel their order
			printMessage("** Okay, I've cancelled your order. Thank you for calling.");
			return false;
		}
		else if (input.contains("menu")) { //The user would like more info on the menu			
			shouldHelp = lastPath.equals("menuHelp");
			lastPath = "menuHelp";
			
			if (input.contains("main")) { //The user wants a list of mains
				printMessage("** The mains we have available are: " + Builder.getFormattedMainsList() + ".");
			}
			else if (input.contains("side")) { //The user wants a list of sides for a specific main
				shouldHelp = lastPath.equals("menuHelpSides");
				lastPath = "menuHelpSides";
								
				Main selectedMain = checkForValidMain(input);
				if (selectedMain == null) { //We can't find the name of a main in whatever they typed
					printMessage("** We don't have that main.");
					if (shouldHelp) printMessage("?? Try asking \"What sides are on your menu for Cheese Burgers?\".");
					return true;
				}
				
				printMessage("** The sides we do for the " + selectedMain.getName() + " are: " + selectedMain.getFormattedAvailableSides() + ".");
			}
			else if (input.contains("drink")) { //The user wants a list of drinks
				printMessage("** The drinks we have available are: " + Builder.getFormattedDrinksList() + ".");
			}
			else {
				printMessage("** What would you like to know about our menu?.");
				printMessage("?? You try asking \"What sides on your menu are for the cheese burger?\"");
			}
		}
		else if (input.contains("remove") || input.contains("delete") ) {
			shouldHelp = lastPath.equals("removeItem");
			lastPath = "removeItem";
			
			Main selectedMain = checkForValidMain(input);
			if (selectedMain == null) { //We can't find the name of a main in whatever they typed
				printMessage("** I can't remove anything from your order with that name.");
				if (shouldHelp) printMessage("?? Try saying \"Remove a cheese burger from my order\".");
				return true;
			}
			
			ArrayList<Meal> mealList = currentOrder.getMealList();
			for (int i = 0; i < mealList.size(); i++) {
				if (mealList.get(i).compareMains(selectedMain)) {
					mealList.remove(i);
					printMessage("** Okay, I have removed one " + selectedMain.getName() + " from your order.");
					return true;
				}
			}
			
			printMessage("** You dont have any " + selectedMain.getName() + " on order.");
		}
		else {
			Main selectedMain = checkForValidMain(input);
			if (input == "" || selectedMain == null) { //We can't find the name of a main in whatever they typed, show the initial/invalid option message
				printMessage(shouldHelp ? "** Is there anything else you'd like to add?" : "** What would you like to order?");
				if (shouldHelp) printMessage("?? Try asking \"I''ll have cheese burger with fries and coke\", \"What sides are on your menu for Cheese Burgers?\",\n?? \"Cancel my order\" or \"That's everything thanks\".");
				return true;
			}
			
			Meal addedMeal = new Meal(selectedMain, selectedMain.checkForValidSide(input), checkForValidDrink(input), input.contains("large") || input.contains("supersize")); //Find out if they asked for sides, drinks and super-size then add it to the order
			
			currentOrder.addMeal(addedMeal);
			printMessage("** Okay, I have added the " + addedMeal.getFormattedMeal() + " to your order.");
		}
		
		return true;
	}
	
	private static Main checkForValidMain(String input) { //Finds a main from the message the user gave
		ArrayList<Main> mainsList = Builder.getMainsList();
		
		String[] splitInput = input.split(" ");
		for (int i = 0; i < splitInput.length; i++) {
			for (int j = 0; j < mainsList.size(); j++) if (mainsList.get(j).testSearchTerm(splitInput[i])) return mainsList.get(j);
		}
		
		return null;
	}

	private static Drink checkForValidDrink(String input) { //Finds a main from the message the user gave
		ArrayList<Drink> drinksList = Builder.getDrinksList();
		
		String[] splitInput = input.split(" ");
		for (int i = 0; i < splitInput.length; i++) {
			for (int j = 0; j < drinksList.size(); j++) if (drinksList.get(j).testSearchTerm(splitInput[i])) return drinksList.get(j);
		}
		
		return null;
	}
	
	private static boolean checkForOptionAliases(String input, String[] options) { //Returns true if the input contains one of the options given in the array
		for (int i = 0; i < options.length; i++) if (input.contains(options[i])) return true;
		return false;
	}
	
	private static void printMessage(String msg) { //Typing 'animation' helper
		for (int i = 0; i < msg.length(); i++) {
			System.out.print(msg.substring(i, i + 1));
			
			//try {
			//	Thread.sleep(30);
			//}
			//catch (Exception ex) {}
		}
		
		System.out.println();
	}
}