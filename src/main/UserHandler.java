package main;

import java.net.*;
import java.io.*;

/**
 *
 * @author devin
 */
public class UserHandler implements Runnable {
    
    private final Socket socket;
    
    public UserHandler(Socket user) {
        socket = user;
    }
    
    @Override public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String fromUser = null;
            while ( (fromUser = in.readLine()) != null)  {
                System.out.println(fromUser);
                out.println(OneNightProtocol.processInput(fromUser));
            }
        } catch (IOException e) {
            
        }
    }
}
