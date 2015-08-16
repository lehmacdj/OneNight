package main;

import java.net.*;

/**
 * @author devin
 */
public class Player {
    public final Socket SOCKET;
    
    public Player(Socket socket) {
        SOCKET = socket;
    }
}
