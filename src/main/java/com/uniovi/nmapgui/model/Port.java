package com.uniovi.nmapgui.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

public class Port {
	@XmlType(name="portState")
	public enum State{open, closed, filtered, unfiltered};
	
	private String protocol, reason;
	private int portId;
	private State state;
	
	
	@XmlAttribute(name="protocol")
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@XmlAttribute(name="reason")
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@XmlAttribute(name="portid")
	public int getPortId() {
		return portId;
	}
	public void setPortId(int portId) {
		this.portId = portId;
	}
	
	@XmlAttribute(name="state")
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	// TODO Setvice, etc.
	
	
	



}
