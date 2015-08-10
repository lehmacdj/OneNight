package main;

/**
 * @author devin
 */
public class Main {
    
    static final int PORT = 9100;
    
    public static void main(String[] args) {
        Server server = new Server(PORT, 3);
    }
}
