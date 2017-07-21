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
import com.uniovi.nmapgui.model.Command;
import com.uniovi.nmapgui.util.Filefinder;

@Controller
public class WebController {
	private List<Command> commands;
	private Command command;
	
    @GetMapping("/nmap")
    public String command(Model model) {
    	command = new Command();
    	commands= new ArrayList<Command>();
    	model.addAttribute("command", command);
    	model.addAttribute("commands", commands);


        return "index";
    }
    
    @GetMapping("/nmap-exe")
    public String command(Model model, @RequestParam String code) {
    	command =  new Command(code);
    	commands.add(0,command);
    	new CommandExecutor(command).execute();
    	model.addAttribute("command", command);
    	model.addAttribute("commands", commands);

        return "fragments/contents :: output";
    }
    
    
    @GetMapping("/nmap/update")
    public String updateOut(Model model, @RequestParam boolean allowDel) {  
    	
    	model.addAttribute("command", command);
    	model.addAttribute("commands", commands);
    	boolean notFinished=false;
    	for(Command cmd : commands)
    		if(notFinished=!cmd.isFinished())
    			break;
    	if(!notFinished && allowDel)
    		commands=new ArrayList<>();
    	

    	return "fragments/contents :: output";
    }

    @GetMapping("/nmap/update-finished")
    public @ResponseBody Boolean updateEnd() {
    	for(Command cmd : commands)
    		if(!cmd.isFinished())
    			return false;
    	return true;
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
    
//    @GetMapping("/nmap/update-finished-list")
//    public @ResponseBody List<Integer> updateEndList() {
//    	List<Integer> ids = new ArrayList<Integer>();
//    	int index=0;
//    	for(Command cmd : commands)
//    	{
//    		if(!cmd.isFinished())
//    			ids.add(++index);
//    		else if(!cmd.isChkUpdateFlag()){
//    			cmd.setChkUpdateFlag(true);
//    			ids.add(++index);
//    		}
//    	}
//    	return ids;
//    }
//    
//    @GetMapping("/nmap/updateid")
//    public String updateOut(Model model, @RequestParam int id) {  
//    	model.addAttribute("command", command);
//    	model.addAttribute("commands", commands);
//
//
//    	return "index :: out"+id;
//    }
//    
//    

}
