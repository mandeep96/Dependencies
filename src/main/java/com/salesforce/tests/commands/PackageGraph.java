package com.salesforce.tests.commands;

import java.io.*;
import java.util.*;

/**
* Instance methods of a package/Node
* Provides dependents and dependencies of a node
*
* @author  Mandeep Mann
* 
*/
public class PackageGraph {
	//global view of dependency graph for LIST Command
	public static Map<String, PackageGraph> graph = new LinkedHashMap<String, PackageGraph>();
	public static Map<String, PackageGraph> list = new LinkedHashMap<String, PackageGraph>();

	private String name;
	
	private Status status = Status.NOT_INSTALLED;
	
	//Packages depend on current instance
	private List<PackageGraph> inNodes = new LinkedList<>();
    //Current instance dependencies on other packages
	private List<PackageGraph> outNodes = new LinkedList<>();

	
   /**
   * Class constructor
   * @param name Name of package
   * @return N/A
   */
	public PackageGraph(String name){
		this.name = name;
	}
	
	
   /**
   * Creates a PackageGraph node and adds to graph
   * Hence why method is static
   * @param name Name of package
   * @return Instance of PackageGraph
   */
	public static PackageGraph getNode(String name) {
		PackageGraph node  = graph.get(name);
		if(node == null) {
			node = new PackageGraph(name);
			graph.put(name, node);
		}
		return node;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the inNodes
	 */
	public List<PackageGraph> getInNodes() {
		return inNodes;
	}

	/**
	 * @param Add a dependent node current instance 
	 */
	public void addToInNodes(PackageGraph node) {
		this.inNodes.add(node);
	}

	/**
	 * @return the outNodes
	 */
	public List<PackageGraph> getOutNodes() {
		return outNodes;
	}

	/**
	 * @param Current instance is dependent on node
	 */
	public void addToOutNodes(PackageGraph node) {
		this.outNodes.add(node);
	}


	/**
	 * @return status - Enum
	 */
	public Status getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
    
}