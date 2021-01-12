package dev.tomdotbat.tasty_bytes.order_system;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import org.json.JSONArray;

public class History {
	private static final String historyFileName = "order-history.json";
	
	public static void addOrder(Order order) { //Adds an order to the order history file
		if (order.getMealList().size() < 1) return;
		
		JSONArray orderHistory = getOrders();
		if (orderHistory == null) orderHistory = new JSONArray(); //Create a new history if one wasn't given
		
		orderHistory.put(order.serialize()); //Add the new order to history
		
		File file = new File(historyFileName);
		//Make the file if it's not there already
		if (!file.exists()) try {
			file.createNewFile();
		} catch (Exception ex) {
			return;
		}
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(historyFileName);
			fileWriter.write(orderHistory.toString()); //Overwrite the JSON data with the new order history
			fileWriter.close();
		} catch (Exception ignored) {}
	}
	
	private static JSONArray getOrders() { //Gets previous orders from a file.
		File file = new File(historyFileName);
		if (!file.exists() || !file.canRead() || file.isDirectory()) { 
			return null; //If the file doesn't exist, can't be read, or is somehow a directory, return null
		}

		Scanner fileReader;
		try {
			fileReader = new Scanner(file);
			
			StringBuilder jsonData = new StringBuilder();
			while (fileReader.hasNextLine()) {
				jsonData.append(fileReader.nextLine());
			}
			
			fileReader.close();
			return new JSONArray(jsonData.toString());
		}
		catch (Exception ignored) {
			return null;
		}
	}
	
	public static JSONArray getLatestOrder() { //Gets the latest order in history
		JSONArray orders = getOrders();
		if (orders == null) return null;
		return orders.getJSONArray(0);
	}
	 
	public static String getFormattedOrders() { //Gets a formatted list of the user's previous orders
		JSONArray orderHistory = getOrders();
		if (orderHistory == null) return "Nothing found.";
		
		StringBuilder response = new StringBuilder();
		
		for (int i = 0; i < orderHistory.length(); i++) {
			response.append(Order.getFormattedItemList(orderHistory.getJSONArray(i))).append(".\n");
		}
		
		return response.toString();
	}
}
