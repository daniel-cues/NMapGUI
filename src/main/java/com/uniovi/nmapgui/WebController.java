package com.uniovi.nmapgui;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uniovi.nmapgui.executor.CommandExecutor;
import com.uniovi.nmapgui.model.Command;

@Controller
public class WebController {
	private CommandExecutor executor = new CommandExecutor();
	Command command = new Command();

    @GetMapping("/nmap")
    public String command(Model model) {
    	command = new Command();
    	executor = new CommandExecutor();
    	model.addAttribute("command", command);

        return "index";
    }
    
    @GetMapping("/nmap-exe")
    public String command(Model model, @RequestParam String code) {
    	command.setText(code);
		executor.execute(command);
    	model.addAttribute("command", command);
        return "index :: output";
    }
    
    
    @GetMapping("/nmap/update")
    public String updateOut(Model model) {  
    	model.addAttribute("command", command);

    	return "index :: output";
    }

    @GetMapping("/nmap/update-finished")
    public @ResponseBody Boolean updateEnd(Model model) {
    	return command.isFinished();
    }

}
