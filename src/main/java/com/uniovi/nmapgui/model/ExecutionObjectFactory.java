package com.uniovi.nmapgui.model;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ExecutionObjectFactory {

	public ExecutionObjectFactory() {
	}
	
	public Scan createScan() {
	    return new Scan();
	}
	
	public ScriptHelp createScriptHelp() {
	    return new ScriptHelp();
	}
}
