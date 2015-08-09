package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Devin Lehmacher
 */
public class Server {
    
    public static final int PORT = 9100;
    public static boolean listening = true;
    public static ArrayList<Socket> connections = new ArrayList<>();
    
    public void main(String[] args) {
        try (
            ServerSocket server = new ServerSocket(PORT);
        ) {
            new Thread(new Main()).start();
            while (listening) {
                connections.add(server.accept());
            }
        }
        catch (IOException e) {
            System.err.println("Could not connect to client.");
        }
    }
}
