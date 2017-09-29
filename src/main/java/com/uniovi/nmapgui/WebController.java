package com.uniovi.nmapgui;



import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uniovi.nmapgui.executor.CommandExecutor;
import com.uniovi.nmapgui.executor.CommandExecutorImpl;
import com.uniovi.nmapgui.executor.CommandExecutorObserver;
import com.uniovi.nmapgui.model.Command;
import com.uniovi.nmapgui.util.Filefinder;

@Controller
public class WebController implements CommandExecutorObserver{
	private List<Command> ongoingCommands  = new ArrayList<Command>();
	private List<Command> finishedCommands = new ArrayList<Command>();
	private Command command;
	private boolean finishedCommandQueued=false;
	
	
    @GetMapping("/nmap")
    public String command(Model model) {
    	
    	command = new Command();
    	model.addAttribute("command", command);
    	model.addAttribute("commands", ongoingCommands);
    	model.addAttribute("commands", finishedCommands);
    	finishedCommandQueued=true;
        return "index";
    }
    
    @GetMapping("/nmap-exe")
    public String command(Model model, @RequestParam String code) {
    	command =  new Command(code);
    	ongoingCommands.add(0,command);
    	CommandExecutor executor = new CommandExecutorImpl(command);
    	executor.addObserver(this);
    	executor.execute();
    	model.addAttribute("command", command);
    	model.addAttribute("commands", ongoingCommands);


        return "fragments/contents :: ongoing";
    }
    
    @GetMapping("/nmap/removeCommand")
    public String removeCommand(Model model, @RequestParam int index) {
    	finishedCommands.remove(index);
    	model.addAttribute("command", command);
    	model.addAttribute("finishedCommands", finishedCommands);


        return "fragments/contents :: finished";
    }
    
    
    @GetMapping("/nmap/update")
    public String updateOut(Model model) {  
    	
    	model.addAttribute("command", command);
    	model.addAttribute("commands", ongoingCommands);

    	return "fragments/contents :: ongoing";
    }
    
    @GetMapping("/nmap/update-finished")
    public String updateEnded(Model model) {  
    	
    	model.addAttribute("command", command);
    	model.addAttribute("finishedCommands", finishedCommands);
    	finishedCommandQueued=false;
    	return "fragments/contents :: finished";
    }

    @GetMapping("/nmap/finishedQueued")
    public @ResponseBody Boolean updateEnd() {
    	return finishedCommandQueued;
    }
    @GetMapping("/nmap/stopUpdating")
    public @ResponseBody Boolean stopUpdating() {
    	return ongoingCommands.isEmpty();
    }
    
    @GetMapping("/nmap/download/{filename}")
    public ResponseEntity<InputStreamResource> download(@PathVariable("filename") String filename) {
    	
    	InputStream file;
		try {
			file = new Filefinder().find(filename);
			InputStreamResource resource = new InputStreamResource(file);

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType("application/octect-stream"))
	                .body(resource);
		} catch (FileNotFoundException e) {
			return ResponseEntity.notFound().build();
		}    	
    	
    }
    
    
    public void finishedCommand(Command cmd){
    	ongoingCommands.remove(cmd);
    	finishedCommands.add(0,cmd);
    	finishedCommandQueued = true;
    }

}
