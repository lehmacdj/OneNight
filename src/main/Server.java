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
        
        try (
            ServerSocket server = new ServerSocket(PORT);
        ) {
            Socket socket;
            while (connections.size() < players) {
                connections.add(socket = server.accept());
                try ( 
                    final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ) {
                    out.println("You have been connected to the One Night Server");
                } catch (IOException e) {
                    System.err.println("Could not get output stream for socket.");
                }
            }
        }
        catch (IOException e) {
            System.err.println("Could not connect to client.");
        }

    }
        
    public List<Socket> getConnections() {
        return connections;
    }
    
}
