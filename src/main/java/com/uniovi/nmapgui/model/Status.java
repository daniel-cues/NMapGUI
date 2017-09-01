package com.uniovi.nmapgui.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

public class Status {
	
	@XmlType(name="hostState")
	enum State {up, down, you};
	
	private State state = State.up;
	private String reason;

	@XmlAttribute(name="state")
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Status [state=" + state + "]";
	}
	
	@XmlAttribute(name="reason")
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	

}
