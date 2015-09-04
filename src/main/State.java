package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import roles.Role;

/**
 * An instance of this class represents the game state for One Night Ultimate Werewolf.
 * @author devin
 */
public class State {
    
    private final List<Player> players; //the players that are playing the game !null
    private final List<CenterCard> centerCards; //the center cards.  3 or 4 elements.
    private Map<String, Player> nameToPlayer; //a map to convert a name to a player
    private Map<UUID, Player> uuidToPlayer; //a map to convert a UUID to a player
    private Protocol protocol; //a protocol used to process string input from the user
    
    /**
     * Constructor: Creates a new instance of this object.  The object is represents the game state 
     * and stores the state of all of the players.
     * Precondition: roles is not null, player number is > 0, port is in the range 1024–49151.
     * @param port The port number to listen on.
     * @param playerNumber The number of players to play the game.
     * @param roles The roles to be used for the game.
     */
    public State(int port, int playerNumber, List<Role> roles) {
                
        //generate a role string
        String roleString = roles.stream()
                .map(r -> r.name)
                .collect(Collectors.joining(", "));
        
        //shuffle the roles
        Collections.shuffle(roles);
        
        //initialize the center cards
        centerCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            centerCards.add(new CenterCard(roles.remove(0)));
        }
        
        //connect to all of the clients
        //and initialize the player array
        players = new ArrayList<>();
        try (
            ServerSocket serverSocket = new ServerSocket(port);
        ) {
            while (players.size() < playerNumber) {
                Socket socket = serverSocket.accept();
                Player player = new Player(socket);
                player.setRole(roles.remove(0));
                player.out.println("uuid=" + player.getUUID());
                player.out.println("set=" + roleString + "\n>>>");
                players.add(player);
            }
        } catch (IOException e) {
            System.err.println("Error: Could not connect to client.");
            System.exit(1);
        }

        //initialize the maps
        uuidToPlayer = Collections.synchronizedMap(new HashMap<>());

        //name to player should be initialized while getting ready state from the players
        nameToPlayer = Collections.synchronizedMap(new HashMap<>());
        
        //init the hash map for uuid to player
        players.stream().forEach( (p) -> {uuidToPlayer.put(p.getUUID(), p);} );        
    }
    
    /**
     * Waits for each player to send a ready signal with their name.  This class is not considered completely
     * initialized until a call to this method.
     */
    public void getPlayerReadyState() {
    	
    	//initialize the protocol
    	protocol = new OneNightProtocol(this);
    	
    	//wait for a message from each player
        final CountDownLatch latch = new CountDownLatch(players.size());
        players.stream().forEach((p) -> {
            new Thread(() -> {
                try {
                    boolean ready = false;
                    while (!ready) {
                        String fromUser = p.in.readLine();
                        System.out.println(fromUser);
                        String result = protocol.processInput(fromUser);
                        p.out.println(result);
                        if (result.contains("Error")) {
                            
                        } else {
                            ready = true;
                        }
                    }
                    nameToPlayer.put(p.getName(), p);
                }catch(IOException e) {
                    System.err.println("Failed to read from player while waiting for ready state.");
                }
                latch.countDown();
            }).start();
        });
        
        try {
            latch.await();
        }
        catch (InterruptedException e) {
            System.err.println("Latch was interrupted while trying to get a ready state from all of the players");
        }
        
        //generate a player string
        String playerString = players.stream()
                .map(p -> p.getName())
                .collect(Collectors.joining(", "));
                
        //send the player list to all of the players
        players.stream().forEach((p) -> {
            p.out.println("players=" + playerString + "\n>>>");
        });
        
        //init stringToPlayer for stringToPlayer(String)
        nameToPlayer = new HashMap<>();
        players.stream().forEach( (p) -> {nameToPlayer.put(p.getName(), p);} );
    }
        
    /**
     * Returns a list of the players in the game.
     * @return a list of the players in the game.
     */
    public List<Player> getPlayers() { // add a method to add a player
        return players;
    }
    
    /**
     * Returns a list that contains the center cards.
     * @return a list of the center cards.
     */
    public List<CenterCard> getCenterCards() {
        return centerCards;
    }
    
    /**
     * Returns a reference to a player that corresponds with the given UUID.
     * @param uuid The UUID to translate into a reference to a player.
     * @return A reference to a player.
     */
    public Player uuidToPlayer(UUID uuid) {
        return uuidToPlayer.get(uuid);
    }
    
    private Player nameToPlayer(String name) {
        return nameToPlayer.get(name);
    }
    
    //pos i s in the form of C-[index] or P-[name]
    private CardLocation parsePosition(String pos) {
        
    	//parse the position into an ArrayList 
    	Scanner parse = new Scanner(pos);
        parse.useDelimiter("-");
        ArrayList<String> args = new ArrayList<>();
        while (parse.hasNext()) {
        	args.add(parse.next().trim());
        }
        parse.close();
        
        //Return the card location based on the args array
        if (args.get(0).equals("C")) {
            int index = Integer.parseInt(args.get(1));
            return centerCards.get(index);
        } else {
            String name = args.get(1);
            return nameToPlayer(name);
        }
        
    }
    
    /**
     * Moves a card from one location to another.  Positions are in the from of P-[Player name]
     * or C-[number].
     * @param pos1 Position of the first card to be moved.
     * @param pos2 Position of the second card to be moved.
     */
    public void move(String pos1, String pos2) {
        CardLocation location1 = parsePosition(pos1);
        CardLocation location2 = parsePosition(pos2);
        Role tmpRole = location1.getRole();
        location1.setRole(location2.getRole());
        location2.setRole(tmpRole);
    }
    
    /**
     * Returns the Role at the specified location. Positions are in the form of P-[Player name]
     * or C-[number].
     * @param pos
     * @return
     */
    public Role look(String pos) {
        return parsePosition(pos).getRole();
    }
    
    /**
     * Returns a protocol to be used for the manipulation of this class and processing of the user input.
     * @return A Protocol that can process user input and manipulate this class.
     */
    public Protocol getProtocol() {
    	return protocol;
    }
    
    /**
     * Returns true if a given name is taken and false if it isn't.
     * Precondition: name is not null and contains at least one character.
     * @param A name to be tested.
     * @return name true if the name is taken and false if it isn't.
     */
    public boolean nameIsTaken(String name) {
        // if nameToPlayer returns null the key is not in the map
        return nameToPlayer(name) != null;
    }
    
}
