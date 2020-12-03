package com.salesforce.tests.commands;

import java.io.*;
import java.util.*;

/**
* Used for the DEPEND Command
* Adds to nodes dependencies and dependants lists
*
* @author  Mandeep Mann
* 
*/
public class DependCommand implements CommandStrategy{

   /**
   * Implements interface method
   * Adds dependencies to parentNode and dependant to childNode
   * @param items Package item1 depends on package item2 (and any additional packages).
   * @return Map<PackageName, Phrase> to be displayed on command line
   */
	@Override
	public Map<String, String> command(List<String> items) {
		Map<String, String> result = new HashMap<String, String>();

		String dependant = items.get(0);
		
	/*	//create parents to give correct ordering during LIST command
		for(String dependencies : items.subList(1, items.size())) {
			PackageGraph parentNode = PackageGraph.getNode(dependencies);
		} */
		
		for(String dependencies : items.subList(1, items.size())) {			
			//add to inNodes, outNodes
			PackageGraph parentNode = PackageGraph.getNode(dependencies);
			PackageGraph childNode = PackageGraph.getNode(dependant);

			//if parent is in child inNodes, Ignore command
			if(childNode.getInNodes().contains(parentNode)) {
				result.put(parentNode.getName() + " depends on " + childNode.getName()+",", " ignoring command");
				continue;
			}
			//childNode is dependent to parentNode
			childNode.addToOutNodes(parentNode);
			//parentNode now has node that is dependent 
			parentNode.addToInNodes(childNode);
		}
		
		//Command does not return anything
		return result;
	}

	
}