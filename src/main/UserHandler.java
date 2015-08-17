package main;

import java.net.*;
import java.io.*;

/**
 * @author devin
 */
public class UserHandler implements Runnable {
    
    private final Socket socket;
    private final Player user;
    
    public UserHandler(Player user) {
        this.user = user;
        socket = this.user.SOCKET;
    }
    
    @Override public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
        ) {
            String fromUser = null;
            while ( (fromUser = in.readLine()) != null)  {
                System.out.println(fromUser);
                out.println(OneNightProtocol.processInput(fromUser, user));
                out.flush();
            }
        } catch (IOException e) {
            
        }
    }
}
