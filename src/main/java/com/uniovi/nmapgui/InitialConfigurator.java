package com.uniovi.nmapgui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uniovi.nmapgui.executor.CommandExecutor;
import com.uniovi.nmapgui.executor.CommandExecutorImpl;
import com.uniovi.nmapgui.executor.CommandExecutorObserver;
import com.uniovi.nmapgui.model.Command;
import com.uniovi.nmapgui.model.Script;
import com.uniovi.nmapgui.model.ScriptHelp;

public class InitialConfigurator implements CommandExecutorObserver{

	private Map<String,List<Script>> scriptCategories = new HashMap<>();
	
	public Map<String, List<Script>> getScriptCategories() {
		return scriptCategories;
	}

	public void setScriptCategories(Map<String, List<Script>> scriptCategories) {
		this.scriptCategories = scriptCategories;
	}

	public void configure(){
		Command command =  new Command("--script-help all");
		CommandExecutor executor = new CommandExecutorImpl(command);
    	executor.addObserver(this);
    	executor.execute();
	}

	@Override
	public void finishedCommand(Command cmd) {
		computeMap(cmd.getOutput().getScriptHelp());		
	}
	
	private void computeMap(ScriptHelp scriptHelp){
		if (scriptHelp!=null){
			for (Script script : scriptHelp.getScripts()){
				for (String category : script.getCategories()){
					if (!scriptCategories.containsKey(category))
						scriptCategories.put(category,new ArrayList<Script>());
					scriptCategories.get(category).add(script);
				}
			}
		}		
	}
}
