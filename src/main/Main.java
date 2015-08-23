package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import org.apache.commons.cli.*;
import roles.Registry;
import roles.Role;

/**
 * @author devin
 */
public class Main {
    
    static final int PORT = 9100;
    
    static final ArrayList<Role> centerCards = new ArrayList<>();
    
    public static void main(String[] args) { //pass roles via command line parameter
        //set up command line option parsing
        //see http://commons.apache.org/proper/commons-cli/usage.html
        
        //create the command line parser using DefaultParser
        CommandLineParser parser = new DefaultParser();
        
        //create the options to be used
        Option playerNumber = Option.builder("n")
                                    .longOpt("player-number")
                                    .hasArg()
                                    .argName("number")
                                    .desc("set the number of players. defaults to 5")
                                    .build();
        
        Option setList = Option.builder("s")
                           .longOpt("set")
                           .hasArg()
                           .argName("list")
                           .desc("use a set specified by a list of cards. cards should be separated by commas")
                           .build();
        
        Option setFile = Option.builder("f")
                               .longOpt("file")
                               .hasArg()
                               .argName("file")
                               .desc("use a set specified by a file")
                               .build();
        
        Option setRandom = Option.builder("r")
                                 .longOpt("random")
                                 .desc("use a random set. the default")
                                 .build();
        
        //create the options object and add the options to it
        Options options = new Options();
        options.addOption(setList);
        options.addOption(setFile);
        options.addOption(setRandom);
        options.addOption(playerNumber);
        
        //create an associative map of roles
        Map<String,Role> stringToRole = new HashMap<>();
        stringToRole.put(Registry.getInstance().alphaWolf.name, Registry.getInstance().alphaWolf);
        stringToRole.put(Registry.getInstance().apprenticeSeer.name, Registry.getInstance().apprenticeSeer);
        stringToRole.put(Registry.getInstance().auraSeer.name, Registry.getInstance().auraSeer);
        stringToRole.put(Registry.getInstance().bodyguard.name, Registry.getInstance().bodyguard);
        stringToRole.put(Registry.getInstance().curator.name, Registry.getInstance().curator);
        stringToRole.put(Registry.getInstance().cursed.name, Registry.getInstance().cursed);
        stringToRole.put(Registry.getInstance().doppelganger.name, Registry.getInstance().doppelganger);
        stringToRole.put(Registry.getInstance().dreamWolf.name, Registry.getInstance().dreamWolf);
        stringToRole.put(Registry.getInstance().drunk.name, Registry.getInstance().drunk);
        stringToRole.put(Registry.getInstance().hunter.name, Registry.getInstance().hunter);
        stringToRole.put(Registry.getInstance().insomniac.name, Registry.getInstance().insomniac);
        stringToRole.put(Registry.getInstance().mason.name, Registry.getInstance().mason);
        stringToRole.put(Registry.getInstance().minion.name, Registry.getInstance().minion);
        stringToRole.put(Registry.getInstance().mysticWolf.name, Registry.getInstance().mysticWolf);
        stringToRole.put(Registry.getInstance().paranormalInvestigator.name, Registry.getInstance().paranormalInvestigator);
        stringToRole.put(Registry.getInstance().prince.name, Registry.getInstance().prince);
        stringToRole.put(Registry.getInstance().revealer.name, Registry.getInstance().revealer);
        stringToRole.put(Registry.getInstance().robber.name, Registry.getInstance().robber);
        stringToRole.put(Registry.getInstance().seer.name, Registry.getInstance().seer);
        stringToRole.put(Registry.getInstance().sentinel.name, Registry.getInstance().sentinel);
        stringToRole.put(Registry.getInstance().tanner.name, Registry.getInstance().tanner);
        stringToRole.put(Registry.getInstance().troublemaker.name, Registry.getInstance().troublemaker);
        stringToRole.put(Registry.getInstance().villageIdiot.name, Registry.getInstance().villageIdiot);
        stringToRole.put(Registry.getInstance().villager.name, Registry.getInstance().villager);
        stringToRole.put(Registry.getInstance().werewolf.name, Registry.getInstance().werewolf);
        stringToRole.put(Registry.getInstance().witch.name, Registry.getInstance().witch);
        
        //the number of players that will play the game
        //the default number of players = 5
        int numberOfPlayers = 5;
        
        //the list of the roles that will be used
        ArrayList<Role> roles = new ArrayList<>();
        
        //parse the command line options
        try {
            CommandLine line = parser.parse(options, args);
            
            if (line.hasOption("n")) {
                //maybe catch this exception
                numberOfPlayers = Integer.parseInt(line.getOptionValue("n"));
            }
            if (line.hasOption("s")) {
                //generate roles from a list
                Scanner list = new Scanner(line.getOptionValue("s"));
                list.useDelimiter(",");
                while (list.hasNext()) {
                    String roleString = list.next().trim();
                    Role role = stringToRole.get(roleString);
                    if ( role != null) {
                        roles.add(role);
                    } else {
                        System.err.println("Illegal role: " + roleString);
                        System.exit(1);
                    }
                }
                if (roles.size() > numberOfPlayers + 3) {
                    System.err.printf("Too many roles specified: %d specified, %d required\n",
                            roles.size(), numberOfPlayers + 3);
                    System.exit(1);
                } else if (roles.size() < numberOfPlayers + 3) {
                    System.err.printf("Too few roles specified: %d specified, %d required\n",
                            roles.size(), numberOfPlayers + 3);
                    System.exit(1);
                }
            } else if (line.hasOption("f")) {
                File file = new File(line.getOptionValue("f"));
                Scanner list = new Scanner(file);
                while (list.hasNext()) {
                    String roleString = list.nextLine().trim();
                    Role role = stringToRole.get(roleString);
                    if ( role != null) {
                        roles.add(role);
                    } else {
                        System.err.println("Illegal role: " + roleString);
                        System.exit(1);
                    }
                }
                if (roles.size() > numberOfPlayers + 3) {
                    System.err.printf("Too many roles specified: %d specified, %d required\n",
                            roles.size(), numberOfPlayers + 3);
                    System.exit(1);
                } else if (roles.size() < numberOfPlayers + 3) {
                    System.err.printf("Too few roles specified: %d specified, %d required\n",
                            roles.size(), numberOfPlayers + 3);
                    System.exit(1);
                }
            } else {
                System.err.println("Random set generation is not yet supported.");
                System.exit(1);
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("Unreported exception: " + e.getMessage());
            System.exit(1);
        }
        catch (NumberFormatException e) {
            System.err.println("Invalid number of players. arg of -n must be an integer");
            System.exit(1);
        }
        catch (ParseException e) {
            System.err.println("Unexpected exeption: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("The roles are:");
        for (Role r : roles) {
            System.out.println(r.name);
        }        
        
        State state = new State(PORT, numberOfPlayers, roles); //pass the Roles array to the server and assign each player a role during initialization
        
        OneNightProtocol protocol = new OneNightProtocol(state);

        state.getPlayerReadyState(protocol);
        
        
        //have the protocol use the roles assigned to players to personalize the protocol
        state.getPlayers().stream().forEach( (p) -> {new Thread(new UserHandler(p, state, protocol)).start();} );
   
    }
}
