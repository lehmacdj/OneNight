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
    private final List<Socket> connections;
    
    public Server(int port, int players) {
        PORT = port;
        connections = new ArrayList<>();
        //connect to all of the clients
        try (
            ServerSocket serverSocket = new ServerSocket(PORT);
        ) {
            while (connections.size() < players) {
                Socket socket = serverSocket.accept();
                connections.add(socket);
            }
        } catch (IOException e) {
            System.err.println("Could not connect to client.");
        }

    }
        
    public List<Socket> getConnections() {
        return connections;
    }
    
}
