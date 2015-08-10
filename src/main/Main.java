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
               out.add(new PrintStream(s.getOutputStream(), false));
               in.add(new BufferedReader(new InputStreamReader(s.getInputStream())));
            }
            String input = null;
            boolean inProgress = true;
            while (inProgress) {
                for (PrintStream o : out) {
                    o.print(OneNightProtocol.processInput(input));
                    o.flush();
                }
                for (BufferedReader i : in) {
                    input = i.readLine();
                }
            }
            
            
            
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
