package main;

import java.util.Scanner;

/**
 * @author devin
 */
public class OneNightProtocol { 

    public static String processInput(String input, Player player) {   
        if (input == null) {
            System.err.print("Error: input string is null");
            return "Error: input string is null";
        }
        Scanner scan = new Scanner(input);
        String keyword = scan.next();
        switch (keyword) {
            case "ready":
                player.setName(scan.next());
                return "Hello " + player.getName() + " you are a " + player.getRole().getName() + "\n" + "Test statement";
            
            default:
                return "Unknown keyword!";
        }
    }
}
