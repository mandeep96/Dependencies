package com.salesforce.tests.dependency;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import com.salesforce.tests.commands.*;


/**
 * Place holder for your unit tests
 */
public class YourUnitTest {
	
	/**
	 * Clear graph after each test
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//clear graph
		PackageGraph.graph.clear();
		PackageGraph.list.clear();
	}

	/**
   * Should really be in PackageGraph but ran out of time to refactor other code
   * @return Set<PackageGraph> Returns a set of all installed packages
   */
    public static Set<PackageGraph> getInstalledPackages() {
        Set<PackageGraph> installed = new HashSet<PackageGraph>();
        for (PackageGraph module : PackageGraph.list.values()) {
            if (module.getStatus() == Status.EXPLICIT_INSTALLED || module.getStatus() == Status.IMPLICIT_INSTALLED)
                installed.add(module);
        }
        return installed;
    }
	

	@Test
	public void installCommandtest() throws Exception {
        String install = "DEPEND TELNET TCPIP NETCARD \n" +
                "DEPEND TCPIP NETCARD \n" +
                "DEPEND DNS TCPIP NETCARD \n" +
                "DEPEND BROWSER TCPIP HTML \n" +
                "INSTALL NETCARD \n" +
                "INSTALL TELNET \n" +
                "INSTALL foo \n" +
                "END";
      
        Main.executeCommand(install);                
        assertEquals(4, PackageGraph.list.values().size());
	}
	
    @Test
    public void dependCommandtest() throws Exception {
        String input = "DEPEND TELNET TCPIP NETCARD\n" +
                "DEPEND TCPIP NETCARD\n" +
                "DEPEND DNS TCPIP NETCARD\n" +
                "DEPEND BROWSER TCPIP HTML\n" +
                "END";

        Main.executeCommand(input);

        PackageGraph module = PackageGraph.getNode("TELNET");
        assertEquals(2, module.getOutNodes().size());
        assertTrue(module.getOutNodes().containsAll(asList(PackageGraph.getNode("TCPIP"), PackageGraph.getNode("NETCARD"))));

        module = PackageGraph.getNode("TCPIP");
        assertEquals(1, module.getOutNodes().size());
        assertTrue(module.getOutNodes().iterator().next().equals(PackageGraph.getNode("NETCARD")));

        module = PackageGraph.getNode("NETCARD");
        assertEquals(0, module.getOutNodes().size());


        module = PackageGraph.getNode("DNS");
        assertEquals(2, module.getOutNodes().size());
        assertTrue(module.getOutNodes().containsAll(asList(PackageGraph.getNode("TCPIP"), PackageGraph.getNode("NETCARD"))));

        module = PackageGraph.getNode("BROWSER");
        assertEquals(2, module.getOutNodes().size());
        assertTrue(module.getOutNodes().containsAll(asList(PackageGraph.getNode("TCPIP"), PackageGraph.getNode("HTML"))));
    }
    
    
    @Test
    public void removeCommandtest() throws Exception {
        String input = "DEPEND TELNET TCPIP NETCARD\n" +
                "DEPEND TCPIP NETCARD\n" +
                "DEPEND DNS TCPIP NETCARD\n" +
                "DEPEND BROWSER TCPIP HTML\n" +
                "INSTALL NETCARD\n" +
                "INSTALL TELNET\n" +
                "INSTALL foo\n" +
                "REMOVE NETCARD\n" +
                "INSTALL BROWSER\n" +
                "INSTALL DNS\n" +
                "END";

        Main.executeCommand(input);
        
        List<PackageGraph> result = new ArrayList<>(getInstalledPackages());
        assertEquals(7, result.size());
        assertTrue(result.containsAll(asList(PackageGraph.getNode("NETCARD"), PackageGraph.getNode("TCPIP"), PackageGraph.getNode("TELNET"),
        		PackageGraph.getNode("foo"), PackageGraph.getNode("BROWSER"), PackageGraph.getNode("HTML"), PackageGraph.getNode("DNS"))));
    } 
    
}
