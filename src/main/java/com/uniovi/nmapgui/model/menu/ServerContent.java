package com.uniovi.nmapgui.model.menu;

import javax.xml.bind.annotation.XmlAttribute;

public class ServerContent {
	
	private String id;

	@XmlAttribute(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
