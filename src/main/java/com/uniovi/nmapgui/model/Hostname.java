package com.uniovi.nmapgui.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Hostname {

	private String hostname;

	public Hostname(String hostname) {
		this.hostname=hostname;
	}
	public Hostname(){}

	@XmlAttribute(name="name")
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	@Override
	public String toString() {
		return "Hostname [hostname=" + hostname + "]";
	}
	
	
}
