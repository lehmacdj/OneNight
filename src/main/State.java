package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import roles.Role;

/**
 * @author Devin Lehmacher
 */
public class State {
    
    private final int PORT;
    private final List<Player> players;
    private final List<CenterCard> centerCards;
    private Map<String, Player> nameToPlayer;
    private Map<UUID, Player> uuidToPlayer;
    
    public State(int port, int playerNumber, List<Role> roles) {
        PORT = port;
                
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
        //and inititialize the player array
        players = new ArrayList<>();
        try (
            ServerSocket serverSocket = new ServerSocket(PORT);
        ) {
            while (players.size() < playerNumber) {
                Socket socket = serverSocket.accept();
                Player player = new Player(socket);
                player.setRole(roles.remove(0));
                player.out.println("uuid=" + player.getUUID());
                player.out.println("set=" + roleString);
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
    
    public void getPlayerReadyState(OneNightProtocol protocol) {
        //get ready state from players
        //kind of hackish -- need to find a better way to do this
        //maybe this is fine
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
            p.out.println("players=" + playerString);
        });
        
        //init stringToPlayer for stringToPlayer(String)
        nameToPlayer = new HashMap<>();
        players.stream().forEach( (p) -> {nameToPlayer.put(p.getName(), p);} );
    }
        
    public List<Player> getPlayers() { // add a method to add a player
        return players;
    }
    
    public List<CenterCard> getCenterCards() {
        return centerCards;
    }
    
    public Player uuidToPlayer(UUID uuid) {
        return uuidToPlayer.get(uuid);
    }
    
    private Player nameToPlayer(String name) {
        return nameToPlayer.get(name);
    }
    
    //pos i s in the form of C-[index] or P-[name]
    private CardLocation parsePosition(String pos) {
        Scanner parse = new Scanner(pos);
        parse.useDelimiter("-");
        boolean isCenter = parse.next().equals("C");
        if (isCenter) {
            int index = parse.nextInt();
            return centerCards.get(index);
        } else {
            String name = parse.next().trim();
            return nameToPlayer(name);
        }
    }
    
    //Pos1 and Pos2 are in the form of C-[index] or P-[name]
    public void move(String pos1, String pos2) {
        CardLocation location1 = parsePosition(pos1);
        CardLocation location2 = parsePosition(pos2);
        Role tmpRole = location1.getRole();
        location1.setRole(location2.getRole());
        location2.setRole(tmpRole);
    }
    
    public Role look(String pos) {
        return parsePosition(pos).getRole();
    }
    
    public boolean nameIsTaken(String name) {
        // if nameToPlayer returns null the key is not in the map
        return nameToPlayer(name) != null;
    }
    
}
