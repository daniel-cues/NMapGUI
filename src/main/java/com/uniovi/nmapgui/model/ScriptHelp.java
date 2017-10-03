package com.uniovi.nmapgui.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "nse-scripts")
public class ScriptHelp {
	
	private List<Script> scripts;

	@XmlElement(name="script")
	public List<Script> getScripts() {
		return scripts;
	}

	public void setScripts(List<Script> scripts) {
		this.scripts = scripts;
	}

	@Override
	public String toString() {
		return "Scripts [" + getScripts() + "]";
	}
}
