package main;

import org.apache.commons.cli.*;

/**
 * @author devin
 */
public class Main {
    
    static final int PORT = 9100;
    static int numberOfPlayers;
    
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
        
        Option setList = Option.builder("l")
                           .longOpt("set-list")
                           .hasArg()
                           .argName("list")
                           .desc("use a set specified by a list of cards")
                           .build();
        
        Option setFile = Option.builder("f")
                               .longOpt("set-file")
                               .hasArg()
                               .argName("file")
                               .desc("use a set specified by a file")
                               .build();
        
        Option setRandom = Option.builder("r")
                                 .longOpt("set-random")
                                 .desc("use a random set. the default")
                                 .build();
        
        //create the options object and add the options to it
        Options options = new Options();
        options.addOption(setList);
        options.addOption(setFile);
        options.addOption(setRandom);
        options.addOption(playerNumber);
        
        //parse the command line options
        try {
            CommandLine line = parser.parse(options, args);
            
            if (line.hasOption("n")) {
                //maybe catch this exception
                numberOfPlayers = Integer.parseInt(line.getOptionValue("n"));
            }
            if (line.hasOption("l")) {
                //Parse option value for l to get a ArrayList<Role>
            }
            if (line.hasOption("f")) {
                //Parse file for ArrayList<Role>
            }
            //if neither l nor f is specified or if r is specified
            if ( !(line.hasOption("l") && line.hasOption("f")) || line.hasOption("r")) {
                //Generate a random ArrayList<Role>
            }
        }
        catch (NumberFormatException e) {
            System.err.println("Invalid number of players. arg of -n must be an integer");
            System.exit(1);
        }
        catch (ParseException e) {
            System.err.println("Unexpected exeption: " + e.getMessage());
        }
        
                
        Server server = new Server(PORT, 3); //pass the Roles array to the server and assign each player a role during initialization
        
        //have the protocol use the roles assigned to players to personalize the protocol
        server.getPlayers().stream().forEach( (p) -> {new Thread(new UserHandler(p)).start();} );
   
    }
}
