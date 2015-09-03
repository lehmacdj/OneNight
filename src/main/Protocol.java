package main;

/**
 * A protocol to be used for the processing of user input.
 * @author devin
 */
public interface Protocol {
	
	/**
	 * Returns a string that is a response from the server to the client.
	 * @param s The user input.
	 * @return the String to be sent back to the client.
	 */
	String processInput(String s);

}
