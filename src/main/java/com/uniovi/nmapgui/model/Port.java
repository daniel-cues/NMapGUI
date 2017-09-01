package com.uniovi.nmapgui.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

public class Port {
	
	@XmlElement(name="state")
	private State state;
	
	private String protocol;
	private int portId;
	
	public enum StateEnum{open, closed, filtered, unfiltered};

	
	@XmlAttribute(name="protocol")
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	
	@XmlAttribute(name="portid")
	public int getPortId() {
		return portId;
	}
	public void setPortId(int portId) {
		this.portId = portId;
	}
	
	@XmlTransient
	public StateEnum getState() {
		return state.stateVal;
	}
	public void setState(StateEnum state) {
		this.state = new State();
		this.state.setStateVal(state);
	}
	
	// TODO Setvice, etc.
	
	@XmlType(name="portState")
	private static class State{
		

		private StateEnum stateVal;

		public void setStateVal(StateEnum state) {
			this.stateVal = state;
		}
		
		@XmlAttribute(name="state")
		public StateEnum getStateVal() {
			return stateVal;
		}
	}



}
