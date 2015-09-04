package main;

import java.io.IOException;

/**
 * @author devin
 */
public class UserHandler implements Runnable {
    
	@SuppressWarnings("unused")
    private final State state;
    private final Player player;
    private final Protocol protocol;
    
    public UserHandler(Player user, State state) {
        this.player = user;
        this.protocol = state.getProtocol();
        this.state = state;
    }
    
    @Override public void run() {
        try {
            String fromUser = null;
            while ( (fromUser = player.in.readLine()) != null)  {
                System.out.println(fromUser);
                player.out.println(protocol.processInput(fromUser));
            }
        } catch (IOException e) {
            System.err.println("Failed to read from input stream.");
        }
    }
}
