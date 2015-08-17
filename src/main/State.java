package main;

import java.io.IOException;
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
    private final List<Role> centerCards;
    
    
    public State(int port, int playerNumber, List<Role> roles) {
        PORT = port;
        
        //shuffle the roles
        Collections.shuffle(roles);
        
        //initialize the center cards
        centerCards = new ArrayList<>();
        centerCards.add(roles.remove(0));
        centerCards.add(roles.remove(0));
        centerCards.add(roles.remove(0));        
        
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
                players.add(player);
            }
        } catch (IOException e) {
            System.err.println("Could not connect to client.");
        }

    }
        
    public List<Player> getPlayers() {
        return players;
    }
    
    public List<Role> getCenterCards() {
        return centerCards;
    }
    
}
