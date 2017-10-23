package com.uniovi.nmapgui.model.menu;

import javax.xml.bind.annotation.XmlAttribute;

public class Option {
	String name="";
	String command;
	String parameters="";
	
	@XmlAttribute(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute(name="command")
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
	@XmlAttribute(name="parameters")
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	

}
