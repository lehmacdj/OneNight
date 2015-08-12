package main;

import java.io.*;
import java.util.*;
import java.net.*;

/**
 * @author devin
 */
public class Main {
    
    static final int PORT = 9100;
    
    public static void main(String[] args) {
        Server server = new Server(PORT, 3);
        
        server.getConnections().stream().forEach( (s) -> {new Thread(new UserHandler(s)).start();} );
   
    }
}
