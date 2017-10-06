package com.uniovi.nmapgui.model;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="menu")
public class Menu {
	
	Set<Submenu> submenus = new HashSet<>();

	@XmlElement(name="submenu")
	public Set<Submenu> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(Set<Submenu> submenus) {
		this.submenus = submenus;
	}

}
