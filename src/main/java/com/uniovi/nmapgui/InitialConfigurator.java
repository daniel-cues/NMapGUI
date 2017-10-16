package com.uniovi.nmapgui;

import java.io.InputStream;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.uniovi.nmapgui.executor.CommandExecutor;
import com.uniovi.nmapgui.executor.CommandExecutorImpl;
import com.uniovi.nmapgui.executor.CommandExecutorObserver;
import com.uniovi.nmapgui.model.Command;
import com.uniovi.nmapgui.model.Script;
import com.uniovi.nmapgui.model.ScriptHelp;
import com.uniovi.nmapgui.model.menu.Menu;

public class InitialConfigurator implements CommandExecutorObserver{

	private Map<String,List<Script>> scriptCategories = new HashMap<>();
	private Menu menu;
	
	public Map<String, List<Script>> getScriptCategories() {
		return scriptCategories;
	}

	public void setScriptCategories(Map<String, List<Script>> scriptCategories) {
		this.scriptCategories = scriptCategories;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public void configure(){
		
		try {
			loadMenu();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Command command =  new Command("--script-help all");
		CommandExecutor executor = new CommandExecutorImpl(command);
    	executor.addObserver(this);
    	executor.execute();
	}

	private void loadMenu() throws JAXBException {
		InputStream xml = InitialConfigurator.class.getClassLoader()
			.getResourceAsStream("menu.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Menu.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Menu menu = (Menu)unmarshaller.unmarshal(xml);
        this.setMenu(menu);
       
	
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
