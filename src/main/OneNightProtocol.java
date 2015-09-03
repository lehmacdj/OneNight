package main;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author devin
 */
public class OneNightProtocol implements Protocol { 
    
    public final State state;
    private Phase phase;
    
    public OneNightProtocol(State state) {
        this.state = state;
        phase = Phase.Waiting;
    }

    // Each query should be in the form of "id command [options]".
    public String processInput(String input) {   
    	
    	//Check for errors in the input string
        if (input == null) {
            return "Error: input string is null \n>>>";
        }
        if (input == "") {
        	return "Error: input string is \"\"\n>>>";
        }
        
        //Add the elements of the input string to an array
        Scanner parse = new Scanner(input);
        ArrayList<String> args = new ArrayList<>();
        while (parse.hasNext()) {
            args.add(parse.next());
        }
        parse.close();
        
        //Check if enough arguments are provided
        if (args.size() < 2) {
        	return "Not enough arguments: At least a UUID and a Keyword are required.\n>>>";
        }
        
        //Get the UUID and use it to get a reference to the player
        UUID uuid;
        try {
             uuid = UUID.fromString(args.get(0));
        } catch (Exception e) {
            return "Invalid UUID: " + args.get(0) + "\n>>>";
        }
        Player player = state.uuidToPlayer(uuid);
        
        String keyword = args.get(1);
        switch (keyword) {
            case "ready":
            	
            	//Check if currently in the correct phase
            	if (phase != Phase.Waiting) {
            		return "Invalid Action: ready state has already been sent.\n>>>";
            	}
            	
            	//Check if enough arguments are provided
            	if (args.size() < 3) {
            		return "Not enough arguments: a player name is required";
            	}
            	
            	//Check if name is taken
                String name = args.get(2);
                if (state.nameIsTaken(name)) {
                    return "Error: name already taken.\n>>>";
                }
                
                //Set the name for the player object and return a role to the player
                player.setName(name);
                return "role=" + player.getRole().name;
                
            case "move":
            	
            	//Check if enough arguments are provided
            	if (args.size() < 4) {
            		return "Not enough arguments: a location and destination are required";
            	}
            	
            	//Assign move the cards as specified and return a message
                String position1 = args.get(2);
                String position2 = args.get(3);
                state.move(position1, position2);
                return "moved " + position1 + " and " + position2 + "\n>>>";
                
            case "look":
            	
            	//Check if enough arguments are provided
            	if (args.size() < 3) {
            		return "Not enough arguments: a location is required";
            	}
            	
            	//return the card at the specified position
                String position = args.get(2);
                return position + "=" + state.look(position).name + "\n>>>";
                
            default:
            	
            	//return a standard message to acknowledge that command does not exist
                return "Unknown keyword!\n>>>";
                
        }
    }
        
    private enum Phase {
    	Waiting, Night, Day;
    }
    
}
