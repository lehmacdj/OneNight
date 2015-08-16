package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author Devin Lehmacher
 */
public class Server {
    
    private final int PORT;
    private final List<Player> players;
    
    public Server(int port, int players) {
        PORT = port;
        this.players = new ArrayList<>();
        //connect to all of the clients
        try (
            ServerSocket serverSocket = new ServerSocket(PORT);
        ) {
            while (this.players.size() < players) {
                Socket socket = serverSocket.accept();
                this.players.add(new Player(socket));
            }
        } catch (IOException e) {
            System.err.println("Could not connect to client.");
        }

    }
        
    public List<Player> getPlayers() {
        return players;
    }
    
}
