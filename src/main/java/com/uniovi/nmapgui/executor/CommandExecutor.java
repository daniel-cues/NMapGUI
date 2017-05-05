package com.uniovi.nmapgui.executor;

import java.io.*;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.uniovi.nmapgui.model.*;

@Service
public class CommandExecutor {
	private Command cmd;

	
	@Async
	public void execute(Command command){
		cmd=command;
		String[] commands = (String[])ArrayUtils.addAll(new String[]{"nmap"},command.getText().split(" "));
		try {
			
			 Process p = Runtime.getRuntime().exec(commands);
			  final InputStream stream = p.getInputStream();
			  new Thread(new Runnable() {
			    public void run() {
			      BufferedReader reader = null;
			      try {
			        reader = new BufferedReader(new InputStreamReader(stream));
			        String line = null;
			        while ((line = reader.readLine()) != null) {
			        	cmd.getOutput().setText(cmd.getOutput().getText()+line+"<br/>");
			        }
			      } catch (Exception e) {
			        // TODO
			      } finally {
			    	cmd.setFinished(true);
			        if (reader != null) {
			          try {
			            reader.close();
			          } catch (IOException e) {
					    cmd.setFinished(true);
			          }
			        }
			      }
			    }
			  }).start();
					    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
