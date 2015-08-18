package main;

import roles.Role;

/**
 * @author Devin Lehmacher <lehmacdj@gmail.com>
 */
public interface CardLocation {
    Role getRole();
    void setRole(Role role);
}
