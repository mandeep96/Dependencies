package com.salesforce.tests.dependency;

import java.io.*;
import java.util.*;
import com.salesforce.tests.commands.*;

/**
 * The entry point for the Test program
 */
public class Main {

    public static void main(String[] args) {

        //read input from stdin
        Scanner scan = new Scanner(System.in);

        while (true) {
            String line = scan.nextLine();

            //no action for empty input
            if (line == null || line.length() == 0) {
                continue;
            }

            //the END command to stop the program
            if ("END".equals(line)) {
                System.out.println("END");
                break;
            }

            //Please provide your implementation here
            executeCommand(line);
        }

    }
    

    /**
     * Calls the related class depending on the argument read
     * To do: Use Static Constants  
     * @param line to be read
     */
    public static void executeCommand(String line) {
    	String lines[] = line.split("\\r?\\n");
    	String command = "";
    	List<String> args = new ArrayList<String>();
    	for(String commands: lines) {
            String[] arguments = commands.split("\\s+");
            command = arguments[0];
            args = new ArrayList<String>(Arrays.asList(arguments));
            System.out.println(commands);
            args.remove(0);
            //System.out.println("Command is: " + command);
            //System.out.println("args are: ");
            //for(String s: args)
            //	System.out.println(s);
    	

	        Map<String, String> result = null; 
	        
	        if(command.equals("DEPEND")) {
	        	CommandStrategy cmd = new DependCommand();
	        	result = cmd.command(args);
	        }
	        else if(command.equals("INSTALL")) {
	        	CommandStrategy cmd = new InstallCommand();
	        	result = cmd.command(args);
	        }
	        else if(command.equals("REMOVE")) {
	        	CommandStrategy cmd = new RemoveCommand();
	        	result = cmd.command(args);
	        }
	        else if(command.equals("LIST")) {
	        	CommandStrategy cmd = new ListCommand();
	        	result = cmd.command(args);
	        }
	        else if(command.equals("END")) {
	        	//for Unit test only
	        	return;
	        }
	        else {
	        	result = null;
	        	System.out.println("Command invalid: " + command);
	        }
	                
	        //print results
	        for(Map.Entry<String,String> map: result.entrySet()) {
	        	//System.out.println(map.getKey() + " " + map.getValue());
	        	System.out.println(map.getKey().trim() + map.getValue());
	        }
    	}
    }
}