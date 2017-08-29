package com.uniovi.nmapgui.model;

import java.util.LinkedList;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Host {
	
	private Address address;
	
	
	private Set<Hostname> hostName;
	
	private LinkedList<Hop> trace;
	
	@XmlElement(name="address")
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@XmlElementWrapper(name="hostnames")
	@XmlElement(name="hostname")
	public Set<Hostname> getHostName() {
		return hostName;
	}

	public void setHostName(Set<Hostname> hostName) {
		this.hostName = hostName;
	}

	@XmlElementWrapper(name="trace")
	@XmlElement(name="hop")
	public LinkedList<Hop> getTrace() {
		return trace;
	}

	public void setTrace(LinkedList<Hop> trace) {
		this.trace = trace;
	}

	@Override
	public String toString() {
		return "\nHost [address=" + address + ", hostName=" + hostName + ", trace=" + trace + "]";
	}
	
	
	
}
