package com.salesforce.tests.commands;

import java.io.*;
import java.util.*;

/**
* Used for the REMOVE Command
* Uninstalls a package and any dependant packages if they are no longer used
*
* @author  Mandeep Mann
* 
*/
public class RemoveCommand implements CommandStrategy{

   /**
   * Implements interface method
   * Gets the PackageGraph instance of package and calls uninstallDfs to uninstall
   * To do: Use Static Constants  
   * @param Package to be uninstalled
   * @return Map<PackageName, Phrase> to be displayed on command line
   */
	@Override
	public Map<String, String> command(List<String> items) {
		//LinkedHashMap will iterate in the order in which the entries were put into the map
		Map<String, String> result = new LinkedHashMap<>();
		
		for(String node: items){
			PackageGraph nodeToUninstall = PackageGraph.getNode(node);
			uninstallDfs(nodeToUninstall, result, "REMOVE"); //--> Use CONST

		}
		return result;
	}
	
	
   /**
   * Method for uninstalling Packages. Explicit packages can only be uninstalled by REMOVE command (not implictly)
   * Packages are uninstalled if they have no dependant nodes
   * Uses depth-first search
   * To do: Use Static Constants  
   * @param nodeToUninstall Node to be uninstalled
   * @param result to store result and display on command line <Name, phrase>
   * @param command will decide if nodeToUninstall is explicitly or implicitly uninstalled
   * @return Map<PackageName, Phrase> to be displayed on command line
   */
	private Map<String, String> uninstallDfs(PackageGraph nodeToUninstall, Map<String, String> result, String command){
		if(nodeToUninstall.getStatus() == Status.NOT_INSTALLED) {
			result.put(nodeToUninstall.getName(), " is not installed");
			return result;
		}
		
		List<PackageGraph> dependantNodesList = nodeToUninstall.getInNodes();
		Set<PackageGraph> dependantInstalledNodes = new HashSet<>();
		
		for(PackageGraph node: dependantNodesList) {
			if(node.getStatus() == Status.EXPLICIT_INSTALLED || node.getStatus() == Status.IMPLICIT_INSTALLED)
				dependantInstalledNodes.add(node);
		}
		
		
		if(dependantInstalledNodes.size() == 0) {
			//base cases 
			if((nodeToUninstall.getStatus() == Status.EXPLICIT_INSTALLED ||
				nodeToUninstall.getStatus() == Status.IMPLICIT_INSTALLED) &&
				command.equals("REMOVE")) {
				//only explicitly remove with REMOVE command
				nodeToUninstall.setStatus(Status.NOT_INSTALLED);
			}
			else if(nodeToUninstall.getStatus() == Status.IMPLICIT_INSTALLED && command.equals("")) { 
				//only implicitly remove with "" command
				nodeToUninstall.setStatus(Status.NOT_INSTALLED);
			}
			
			//only add to map if node is removed
			if(nodeToUninstall.getStatus() == Status.NOT_INSTALLED) {
				result.put("Removing"+" " + nodeToUninstall.getName(), ""); //CONSTS

				
				//check if any outnodes/dependecies have any connections
				//remove implicit nodes
				for(PackageGraph node: nodeToUninstall.getOutNodes()) {
					uninstallDfs(node, result, "");
				}
			}
			
		}
		//else if to match sample output given in document
		else if((nodeToUninstall.getStatus() == Status.EXPLICIT_INSTALLED || 
				nodeToUninstall.getStatus() == Status.IMPLICIT_INSTALLED) &&
				command.equals("REMOVE")){
			
			result.put(nodeToUninstall.getName(), " is still needed");
		}
		
		return result;
	}

}
