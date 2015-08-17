package main;

import java.net.*;
import java.io.*;

/**
 * @author devin
 */
public class UserHandler implements Runnable {
    
    private final Socket socket;
    private final State state;
    private final Player user;
    private final OneNightProtocol protocol;
    
    public UserHandler(Player user, State state, OneNightProtocol protocol) {
        this.user = user;
        socket = this.user.socket;
        this.protocol = protocol;
        this.state = state;
    }
    
    @Override public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
        ) {
            String fromUser = null;
            while ( (fromUser = in.readLine()) != null)  {
                System.out.println(fromUser);
                out.println(protocol.processInput(fromUser, user));
                out.flush();
            }
        } catch (IOException e) {
            
        }
    }
}
