package dev.tomdotbat.tasty_bytes.menu;

public abstract class Item {
	public String getName() {return name;} //Useful getters
	public float getPrice(boolean isLarge) {return isLarge ? largePrice : price;}
	
	public boolean testSearchTerm(String searchTerm) { //Item searching
		if (searchableName.equals(searchTerm)) return true;
		if (searchableName.contains(searchTerm)) return true;
				
		for (int i = 0; i < searchTags.length; i++) {			
			if (searchTags[i].equals(searchTerm)) return true;
			if (searchTags[i].contains(searchTerm)) return true;
		}
		
		return false;
	}
	
	protected String name; //The display name of the item
	protected String searchableName; //The name used internally for finding the item in the menu
	protected String[] searchTags; //Other related words to this item to help find it
	protected float price; //The price of the item normally
	protected float largePrice; //The price of the large version of the item
}
