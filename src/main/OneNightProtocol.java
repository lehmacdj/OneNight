package main;

import java.util.ArrayList;
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
        Scanner parse = new Scanner(input);
        ArrayList<String> args = new ArrayList<>();
        while (parse.hasNext()) {
            args.add(parse.next());
        }
        UUID uuid;
        try {
             uuid = UUID.fromString(args.get(0));
        } catch (Exception e) {
            return "Invalid UUID: " + args.get(0);
        }
        Player player = state.uuidToPlayer(uuid);
        String keyword = args.get(1);
        switch (keyword) {
            case "ready":
                String name = args.get(2);
                if (!state.nameIsTaken(name)) {
                    player.setName(name);
                    return "role=" + player.getRole().name;
                } else {
                    return "Error: name already taken.";
                }
            case "move":
                String position1 = args.get(2);
                String position2 = args.get(3);
                state.move(position1, position2);
                return "moved " + position1 + " and " + position2;
            case "look":
                String position = args.get(2);
                return position + "=" + state.look(position).name;
            default:
                return "Unknown keyword!";
        }
    }
    
}
