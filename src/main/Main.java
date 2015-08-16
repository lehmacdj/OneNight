package main;

import java.io.*;
import java.util.*;
import java.net.*;

/**
 * @author devin
 */
public class Main {
    
    static final int PORT = 9100;
    
    public static void main(String[] args) { //pass roles via command line parameter
        
        //parse command line parameters and use them to initialize a Roles array
        
        Server server = new Server(PORT, 3); //pass the Roles array to the server and assign each player a role during initialization
        
        //have the protocol use the roles assigned to players to personalize the protocol
        server.getPlayers().stream().forEach( (p) -> {new Thread(new UserHandler(p)).start();} );
   
    }
}
