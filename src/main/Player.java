package main;

import java.net.*;
import java.io.*;
import java.util.UUID;
import roles.Role;

/**
 * @author devin
 */
public class Player implements CardLocation {
    
    private final Socket socket;
    private Role role;
    private String name;
    private final UUID uuid;
    public final PrintWriter out;
    public final BufferedReader in;
    
    
    public Player(Socket socket) throws IOException {
        this.socket = socket;
        role = null;
        uuid = UUID.randomUUID();
        out = new PrintWriter(this.socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }
    
    @Override public Role getRole() {
        return role;
    }
    
    @Override public void setRole(Role role) {
        this.role = role;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public UUID getUUID() {
        return uuid;
    }
    
    public Socket getSocket() {
        return socket;
    }
}
