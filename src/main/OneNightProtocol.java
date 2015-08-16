package main;

import java.util.Scanner;

/**
 * @author devin
 */
public class OneNightProtocol { 
    
    
    public static String processInput(String input) {   
        if (input == null) {
            System.err.print("Error: input string is null");
            return "Error: input string is null";
        }
        Scanner scan = new Scanner(input);
        String keyword = scan.next();
        switch (keyword) {
            case "ready":
                String name = scan.next();
                return "Hello " + name + " you are connected to the server.";
            
            default:
                return "Unknown keyword!";
        }
    }
}
