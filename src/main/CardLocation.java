package main;

import roles.Role;

/**
 * An interface for things that store a the location of a card.
 * @author devin
 */
public interface CardLocation {
    
	/**
     * A method that should return the Role at the Location of this card.
     * @return a role corresponding to the current location of this card.
     */
	Role getRole();
    
	/**
	 * Sets the role of this object to role.
	 * @param role The role that should be stored in this object.
	 */
	void setRole(Role role);
    
}
