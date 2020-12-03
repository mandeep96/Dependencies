package com.salesforce.tests.commands;

import java.io.*;
import java.util.*;

/**
* Used for the INSTALL Command
* Installs a package and any dependant packages needed
*
* @author  Mandeep Mann
* 
*/
public class InstallCommand implements CommandStrategy{

   /**
   * Implements interface method
   * Gets the PackageGraph instance of package and calls installDfs to install
   * To do: Use Static Constants  
   * @param Package to be installed
   * @return Map<PackageName, Phrase> to be displayed on command line
   */
	@Override
	public Map<String, String> command(List<String> items) {
		//LinkedHashMap will iterate in the order in which the entries were put into the map
		Map<String, String> result = new LinkedHashMap<>();
		
		for(String node: items){
			PackageGraph nodeToInstall = PackageGraph.getNode(node);
			installDfs(nodeToInstall, result, "INSTALL"); //--> Use CONST

		}
		return result;

	}
	
	
   /**
   * Method for installing Packages. Explicit packages are installed using the INSALL command
   * Implicit Packages are installed as they are required by the explicitly installed package
   * Uses topological sort and depth-first search
   * To do: Use Static Constants  
   * @param nodeToInstall Node to be installed
   * @param result to store result and display on command line <Name, phrase>
   * @param command will decide if nodeToInstall is explicitly or implicitly installed
   * @return Map<PackageName, Phrase> to be displayed on command line
   */
	private Map<String, String> installDfs(PackageGraph nodeToInstall, Map<String, String> result, String command){
		if(nodeToInstall.getStatus() == Status.NOT_INSTALLED){
			//base case
			if(command.equals("INSTALL")) { 
				nodeToInstall.setStatus(Status.EXPLICIT_INSTALLED);
			}
			else {
				nodeToInstall.setStatus(Status.IMPLICIT_INSTALLED);
				
			}
			
			//check if current node has any dependencies
			//dfs to give us topological sort
			for(PackageGraph dependencies: nodeToInstall.getOutNodes()) {
				installDfs(dependencies, result, "");
			}
			//--> Adding to list
			PackageGraph.list.put(nodeToInstall.getName(), nodeToInstall);

			//overwriting key each time
			//result.put("Installing", nodeToInstall.getName());
			result.put("Installing " + nodeToInstall.getName(),"");
		}
		//else if to match sample output given in document
		else if((nodeToInstall.getStatus() == Status.EXPLICIT_INSTALLED || 
				nodeToInstall.getStatus() == Status.IMPLICIT_INSTALLED ) &&
				command.equals("INSTALL")){
			
			result.put(nodeToInstall.getName(), " is already installed");
		}
		
		return result;
	}

}
