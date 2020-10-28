package dev.tomdotbat.tasty_bytes.menu;

public class Main extends Item {
	public Main(String displayName, String searchTerm, String[] tags, float value, float largeValue, Side[] sides) { //Main constructor
		name = displayName;
		searchableName = searchTerm;
		searchTags = tags;
		price = value;
		largePrice = largeValue;
		availableSides = sides;
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
			for (int j = 0; j < availableSides.length; j++) if (availableSides[j].testSearchTerm(splitInput[i])) return availableSides[j];
		}
		
		return null;
	}
	
	private Side[] availableSides;
}
