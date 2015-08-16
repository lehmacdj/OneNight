package roles;

/**
 * @author devin
 */
public abstract class Role {
    private String name;
    
    Role(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
