package main;

import java.net.*;
import roles.Role;

/**
 * @author devin
 */
public class Player {
    public final Socket SOCKET;
    
    private Role role;
    private String name;
    
    public Player(Socket socket) {
        SOCKET = socket;
        role = null;
    }
    
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
