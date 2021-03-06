package main;

import roles.Role;

/**
 * @author devin
 */
public class CenterCard implements CardLocation {

    private Role role;
    
    public CenterCard(Role role) {
        this.role = role;
    }
    
    @Override public Role getRole() {
        return role;
    }

    @Override public void setRole(Role role) {
        this.role = role;
    }

}
