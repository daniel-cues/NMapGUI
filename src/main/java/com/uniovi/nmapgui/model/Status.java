package com.uniovi.nmapgui.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Status {
	enum State {up, down, you};
	
	private State state = State.up;

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
	
	

}
