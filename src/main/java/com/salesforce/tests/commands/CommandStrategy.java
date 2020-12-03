package com.salesforce.tests.commands;

import java.io.*;
import java.util.*;

/**
* Strategy Pattern to decouple client
*
* @author  Mandeep Mann
* 
*/
public interface CommandStrategy {
	
   /**
   * Method will be implemented by subclasses
   * @param items A command and one or mpre package names 
   * @return Map<PackageName, Phrase> to be displayed on command line
   */
	public Map<String, String> command(List<String> items);
}
