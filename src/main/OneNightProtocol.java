package main;

import java.util.Scanner;
import java.util.UUID;

/**
 * @author devin
 */
public class OneNightProtocol { 
    
    public final State state;
    
    public OneNightProtocol(State state) {
        this.state = state;
    }

    // Each query should be in the form of "id command options".
    public String processInput(String input) {   
        if (input == null) {
            System.err.print("Error: input string is null");
            return "Error: input string is null";
        }
        Scanner scan = new Scanner(input);
        String uuidString = scan.next();
        UUID uuid = UUID.fromString(uuidString);
        Player player = state.uuidToPlayer(uuid);
        String keyword = scan.next();
        switch (keyword) {
            case "ready":
                player.setName(scan.next());
                return "role=" + player.getRole().name;
            case "move":
                String position1 = scan.next();
                String position2 = scan.next();
                state.move(position1, position2);
                return "moved " + position1 + " and " + position2;
            case "look":
                String position = scan.next();
                return position + "=" + state.look(position).name;
            default:
                return "Unknown keyword!";
        }
    }
}
