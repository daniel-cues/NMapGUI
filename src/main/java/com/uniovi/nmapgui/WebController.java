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
        model.addAttribute("output",executor.out);

        return "index";
    }
    
    @GetMapping("/nmap-exe")
    public String command(Model model, @RequestParam String code) {
    	command.setText(code);
		executor.execute(command);
        model.addAttribute("output",executor.out);
    	model.addAttribute("command", command);
        return "index :: out-fragment";
    }
    
    
    @GetMapping("/nmap/update")
    public String updateOut(Model model) {  
    	model.addAttribute("output",executor.out);
    	model.addAttribute("command", command);

    	return "index :: out-fragment";
    }

    @GetMapping("/nmap/update-finished")
    public @ResponseBody Boolean updateEnd(Model model) {
    	return command.isFinished();
    }

}
