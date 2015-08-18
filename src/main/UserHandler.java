package main;

import java.net.*;
import java.io.*;

/**
 * @author devin
 */
public class UserHandler implements Runnable {
    
    private final State state;
    private final Player player;
    private final OneNightProtocol protocol;
    
    public UserHandler(Player user, State state, OneNightProtocol protocol) {
        this.player = user;
        this.protocol = protocol;
        this.state = state;
    }
    
    @Override public void run() {
        try {
            String fromUser = null;
            while ( (fromUser = player.in.readLine()) != null)  {
                System.out.println(fromUser);
                player.out.println(protocol.processInput(fromUser));
                player.out.flush();
            }
        } catch (IOException e) {
            System.err.println("Failed to read from input stream.");
        }
    }
}
