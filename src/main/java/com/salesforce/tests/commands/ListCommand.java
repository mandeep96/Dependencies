package com.salesforce.tests.commands;

import java.io.*;
import java.util.*;

/**
* Used for the LIST Command
* Lists out all currently installed packages
*
* @author  Mandeep Mann
* 
*/
public class ListCommand implements CommandStrategy{

   /**
   * Implements interface method
   * Lists out all currently installed packages
   * @param Contains only LIST command
   * @return Map<PackageName, Phrase> to be displayed on command line
   */
	@Override
	public Map<String, String> command(List<String> items) {
		Map<String, String> result = new LinkedHashMap<String, String>();
		Set<PackageGraph> set = new HashSet<>();
		
		for(PackageGraph node: PackageGraph.list.values()) {
			if(node.getStatus() == Status.EXPLICIT_INSTALLED || node.getStatus() == Status.IMPLICIT_INSTALLED) {
				result.put(node.getName(), "");
			}
		}
		
		//for(PackageGraph node: set) {
		//	result.put(node.getName(), "");
		//} 
		return result;
	}	

}
