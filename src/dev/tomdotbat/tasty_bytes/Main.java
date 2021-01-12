package dev.tomdotbat.tasty_bytes;

import java.util.Scanner;

import dev.tomdotbat.tasty_bytes.menu.Builder;
import dev.tomdotbat.tasty_bytes.order_system.MessageHandler;

public class Main {
	public static void main(String[] args) { //Program entry point
		Builder.buildMenu(); //Build our menu

		Scanner reader = new Scanner(System.in);		
		
		MessageHandler.handleInput("", false); //Run the main loop once before taking inputs
		while (MessageHandler.handleInput(reader.nextLine(), false)); //Run the message handler until it returns false

		reader.close();
	}
}