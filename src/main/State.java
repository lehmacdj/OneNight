package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import roles.Role;

/**
 * @author Devin Lehmacher
 */
public class State {
    
    private final int PORT;
    private final List<Player> players;
    private final List<CenterCard> centerCards;
    
    
    public State(int port, int playerNumber, List<Role> roles) {
        PORT = port;
        
        //shuffle the roles
        Collections.shuffle(roles);
        
        //initialize the center cards
        centerCards = new ArrayList<>();
        centerCards.add(new CenterCard(roles.remove(0)));
        centerCards.add(new CenterCard(roles.remove(0)));
        centerCards.add(new CenterCard(roles.remove(0)));        
        
        //connect to all of the clients
        //and inititialize the player array
        players = new ArrayList<>();
        try (
            ServerSocket serverSocket = new ServerSocket(PORT);
        ) {
            while (players.size() < playerNumber) {
                Socket socket = serverSocket.accept();
                Player player = new Player(socket);
                player.setRole(roles.remove(0));
                player.out.println("uuid=" + player.getUUID());
                player.out.flush();
                players.add(player);
            }
        } catch (IOException e) {
            System.err.println("Error: Could not connect to client.");
            System.exit(1);
        }

    }
        
    public List<Player> getPlayers() {
        return players;
    }
    
    public List<CenterCard> getCenterCards() {
        return centerCards;
    }
    
    private Map<UUID, Player> uuidToPlayer = null;
    public Player uuidToPlayer(UUID uuid) {
        if (uuidToPlayer == null) {
            uuidToPlayer = new HashMap<>();
            players.stream().forEach( (p) -> {uuidToPlayer.put(p.getUUID(), p);} );
        }
        return uuidToPlayer.get(uuid);
    }
    
    private Map<String, Player> stringToPlayer = null;
    private Player nameToPlayer(String name) {
        if (stringToPlayer == null) {
            stringToPlayer = new HashMap<>();
            players.stream().forEach( (p) -> {stringToPlayer.put(p.getName(), p);} );
        }
        return stringToPlayer.get(name);
    }
    
    private CardLocation parsePosition(String pos) {
        Scanner parse = new Scanner(pos);
        parse.useDelimiter("-");
        boolean isCenter = parse.next().equals("C");
        if (isCenter) {
            int index = parse.nextInt();
            return centerCards.get(index);
        } else {
            String name = parse.next().trim();
            return nameToPlayer(name);
        }
    }
    
    //Pos1 and Pos2 are in the form of C-[index] or P-[name]
    public void move(String pos1, String pos2) {
        CardLocation location1 = parsePosition(pos1);
        CardLocation location2 = parsePosition(pos2);
        Role tmpRole = location1.getRole();
        location1.setRole(location2.getRole());
        location2.setRole(tmpRole);
    }
    
    public Role look(String pos) {
        return parsePosition(pos).getRole();
    }
    
    
}
