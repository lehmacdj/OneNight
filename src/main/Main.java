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
        
        ArrayList<BufferedReader> in = new ArrayList<>();
        ArrayList<PrintStream> out = new ArrayList<>();
        
        try {
            //open the in and out streams
            for (Socket s : server.getConnections()) {
               out.add(new PrintStream(s.getOutputStream(), true));
               in.add(new BufferedReader(new InputStreamReader(s.getInputStream())));
            }
            
            out.stream().forEach( (o) -> {o.println("Everyone has connected to the one night server let the games begin!");} );
            
            
            
            
        } catch (IOException e) {
            System.err.println("Uh-oh there was an IO Exception.");
        } finally {
            //close in and out streams
            try {
                for (BufferedReader i : in) {
                    i.close();
                }
                for (PrintStream o : out) {
                    o.close();
                }
            } catch (IOException e) {
                System.err.println("There was an IO Exception while closing in and out streams.");
            }
        }
    }
}
