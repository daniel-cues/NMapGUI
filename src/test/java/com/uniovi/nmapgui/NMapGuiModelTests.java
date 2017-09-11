package com.uniovi.nmapgui;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Test;
import com.uniovi.nmapgui.model.*;

public class NMapGuiModelTests {

	private Address address;
	private Hop hop;
	private Status status;
	private Hostname name;
	private Port port;

	@Test
	public void addressTest(){
		address = new Address("1.1.1.1");
		Address nul=null;
		assertEquals("Address [address=1.1.1.1]", address.toString());
		assertEquals(true,address.equals(address));
		assertEquals(false,address.equals(nul));
		assertEquals(false,address.equals(new Port()));
		assertEquals(false,new Address().equals(address));
		assertEquals(false,address.equals(new Address("2.2.2.2")));
		assertEquals(true,address.equals(new Address("1.1.1.1")));
	}
	
	@Test
	public void hopTest(){
		hop = new Hop();
		hop.setAddress("1.1.1.1");
		hop.setHost("www.example.com");
		assertEquals("Hop [address=1.1.1.1, host=www.example.com]", hop.toString());
		
		Host host = hop.toHost();
		assertEquals("1.1.1.1", host.getAddress().getAddress());
		assertEquals("www.example.com", host.getHostNames().iterator().next().getHostname());
	}
	
	@Test
	public void statusTest(){
		status = new Status();
		status.setState(Status.State.down);
		status.setReason("Timed-out");
		assertEquals("Status [state=down, reason=Timed-out]", status.toString());		
	}
	
	@Test
	public void hostnameTest(){
		name = new Hostname("www.example.com");
		assertEquals("Hostname [hostname=www.example.com]", name.toString());		
	}
	
	@Test
	public void portTest(){
		port = new Port();
		port.setPortId(80);
		port.setProtocol("tcp");
		port.setState(Port.StateEnum.open);
		assertEquals("Port [portId=80, protocol=tcp, state=open]", port.toString());			
	}
	
	@Test
	public void hostTest(){
		addressTest();
		statusTest();
		hostnameTest();
		portTest();
		hopTest();
		
		Host host = new Host();
		host.setAddress(address);
		host.setStatus(status);
		Set<Hostname> names = new HashSet<Hostname>();
		names.add(name);
		host.setHostNames(names);
		Set<Port> ports = new HashSet<Port>();
		ports.add(port);
		host.setPorts(ports);
		LinkedList<Hop> trace = new LinkedList<Hop>();
		trace.add(hop);
		host.setTrace(trace);
		
		Host nul=null;
		assertEquals(true,host.equals(host));
		assertEquals(false,host.equals(nul));
		assertEquals(false,host.equals(new Port()));
		assertEquals(false,new Host().equals(host));
		assertEquals(false,host.equals(new Host(new Address("2.2.2.2"))));
		assertEquals(true,host.equals(new Host(new Address("1.1.1.1"))));
		
		assertEquals("\nHost [address=Address [address=1.1.1.1], hostName=[Hostname [hostname=www.example.com]], "
				+ "trace=[Hop [address=1.1.1.1, host=www.example.com]], status=Status [state=down, reason=Timed-out]" 
				+ ", ports=[Port [portId=80, protocol=tcp, state=open]]]", host.toString());
	}
	
	@Test
	public void linkTest(){
		Host source = new Host();
		Host target = new Host();
		source.setAddress(new Address("1.1.1.1"));
		target.setAddress(new Address("2.2.2.2"));
		Link link = new Link(source, target);
		assertEquals("Link [source=\nHost [address=Address [address=1.1.1.1], hostName=[], "
				+ "trace=null, status=Status [state=up, reason=null], ports=[]], "
				+ "target=\nHost [address=Address [address=2.2.2.2], hostName=[], "
				+ "trace=null, status=Status [state=up, reason=null], ports=[]]]", link.toString());
	}
	
	@Test
	public void scanTest(){
		Host source = new Host();
		Host target = new Host();
		source.setAddress(new Address("1.1.1.1"));
		target.setAddress(new Address("2.2.2.2"));
		hop = new Hop();
		Hop hopS = new Hop();
		hop.setAddress("10.10.10.10");
		hopS.setAddress("1.1.1.1");
		LinkedList<Hop> hops = new LinkedList<>();
		hops.add(hop);
		hops.add(hopS);
		source.setTrace(hops);
		ArrayList<Host> list = new ArrayList<>() ;
		list.add(source);
		list.add(target);
		Scan scan = new Scan();
		scan.setHosts(list);
		assertEquals("Scan [Hosts:[\nHost [address=Address [address=1.1.1.1], hostName=[], "
				+ "trace=[Hop [address=10.10.10.10, host=null], Hop [address=1.1.1.1, host=null]], status=Status [state=up, reason=null], ports=[]], "
				+ "\nHost [address=Address [address=2.2.2.2], hostName=[], "
				+ "trace=null, status=Status [state=up, reason=null], ports=[]]]]", scan.toString());
		
		assertEquals("[Link [source="+
				"\nHost [address=Address [address=You], hostName=[], trace=null, status=Status [state=you, reason=null], ports=[]], target="+
				"\nHost [address=Address [address=10.10.10.10], hostName=[Hostname [hostname=null]], trace=null, status=Status [state=up, reason=null], ports=[]]], "+
		 "Link [source="+
				"\nHost [address=Address [address=10.10.10.10], hostName=[Hostname [hostname=null]], trace=null, status=Status [state=up, reason=null], ports=[]], target="+
				"\nHost [address=Address [address=1.1.1.1], hostName=[], trace=[Hop [address=10.10.10.10, host=null], Hop [address=1.1.1.1, host=null]], status=Status [state=up, reason=null], ports=[]]], "+
		 "Link [source="+
				"\nHost [address=Address [address=You], hostName=[], trace=null, status=Status [state=you, reason=null], ports=[]], target="+
				"\nHost [address=Address [address=2.2.2.2], hostName=[], trace=null, status=Status [state=up, reason=null], ports=[]]]]",scan.getLinkTraceroute().toString());	
	}
	
	
	@Test
	public void commandTest() {
		Command command = new Command("test");
		Output output = new Output();
		command.setFinished(true);
		command.setOutput(output);
		command.setChkUpdateFlag(true);
		
		assertTrue(command.isFinished());
		assertTrue(command.isChkUpdateFlag());
		assertEquals(output, command.getOutput());
		assertEquals("test", command.getText());
	}
	
	@Test
	public void ouputTest() {
		Output output = new Output();
		output.setFilename("testFile");
		output.setText("testText");
		output.setXml("testXML");
		output.setId("ID1");
		assertEquals("testFile", output.getFilename());
		assertEquals("testText", output.getText());
		assertEquals("testXML", output.getXml());
		assertEquals("ID1", output.getId());
	}

}
