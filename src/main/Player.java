package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

import roles.Role;

/**
 * An implementation of CardLocation that represents a Player and their card.
 * @author devin
 */
public class Player implements CardLocation {
    
    private final Socket socket;
    private Role role;
    private String name;
    private final UUID uuid;
    
    /**
     * A PrintWriter that prints to the client.  Provided for convenience.
     */
    public final PrintWriter out;
    
    /**
     * A buffered reader that reads from the client.
     */
    public final BufferedReader in;
    
    /**
     * Constructs a new instance of Player.
     * @param socket The socket that is used to communicate with the client.
     * @throws IOException when the provided socket cannot be used to get a in and out stream.
     */
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
    
    /**
     * A method that provides the name of the player.
     * @return the name of the Player object.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the player's name to name.
     * @param name The name that the player's name should be set to.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the UUID of the player.
     * @return the UUID of the player.
     */
    public UUID getUUID() {
        return uuid;
    }
    
}
