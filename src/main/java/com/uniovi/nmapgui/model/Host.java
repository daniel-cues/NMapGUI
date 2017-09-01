package com.uniovi.nmapgui.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Host {
	
	private Address address;
	
	
	private Set<Hostname> hostNames = new HashSet<Hostname>();
	private Set<Port> ports = new HashSet<Port>();

	
	private LinkedList<Hop> trace;
	
	private Status status = new Status();
	
	@XmlElement(name="address")
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@XmlElementWrapper(name="hostnames", required=false)
	@XmlElement(name="hostname", required=false)
	public Set<Hostname> getHostNames() {
		return hostNames;
	}

	public void setHostNames(Set<Hostname> hostNames) {
		this.hostNames = hostNames;
	}

	@XmlElementWrapper(name="trace")
	@XmlElement(name="hop")
	public LinkedList<Hop> getTrace() {
		return trace;
	}

	public void setTrace(LinkedList<Hop> trace) {
		this.trace = trace;
	}


	@XmlElement(name="status")
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@XmlElementWrapper(name="ports")
	@XmlElement(name="port")
	public Set<Port> getPorts() {
		return ports;
	}

	public void setPorts(Set<Port> ports) {
		this.ports = ports;
	}

	@Override
	public String toString() {
		return "\nHost [address=" + address + ", hostName=" + hostNames + ", trace=" + trace + ", status=" + status + "]";
	}
		
}
