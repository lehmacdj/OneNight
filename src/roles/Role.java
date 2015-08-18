package roles;

/**
 * @author devin
 */
public abstract class Role {
    
    public final String name;
    
    Role(String name) {
        this.name = name;
    }
    
    @Override public String toString() {
        return name;
    }
    
}
