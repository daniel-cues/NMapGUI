package com.uniovi.nmapgui;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uniovi.nmapgui.executor.CommandExecutor;
import com.uniovi.nmapgui.model.Command;

@Controller
public class WebController {
	List<Command> commands;
	Command command;

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
    	new CommandExecutor().execute(command);
    	model.addAttribute("command", command);
    	model.addAttribute("commands", commands);

        return "index :: output";
    }
    
    
    @GetMapping("/nmap/update")
    public String updateOut(Model model) {  
    	model.addAttribute("command", command);
    	model.addAttribute("commands", commands);


    	return "index :: output";
    }

    @GetMapping("/nmap/update-finished")
    public @ResponseBody Boolean updateEnd(Model model) {
    	for(Command cmd : commands)
    		if(!cmd.isFinished())
    			return false;
    	return true;
    }

}
