package com.uniovi.nmapgui.executor;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.uniovi.nmapgui.model.*;
import com.uniovi.nmapgui.util.TransInfoHtml;

@Service
public class CommandExecutor {
	private Command cmd;
	private String tempPath;
	private String filename;

	
	public CommandExecutor() {
		this.setTempPath(System.getProperty("user.dir")+"/src/main/resources/static/temp/");
	}


	@Async
	public void execute(Command command){
		cmd=command;
		filename= "nmap-scan_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
				.format(new Date()) + ".xml";
		tempPath=tempPath + filename;
		List<String> commandsList = new ArrayList<String>();
		commandsList.add("nmap");
		commandsList.addAll(Arrays.asList(command.getText().split(" ")));
		commandsList.addAll(Arrays.asList(new String[]{"-oX" , tempPath}));
		try {			
			 Process p = Runtime.getRuntime().exec(commandsList.toArray(new String[]{}));
			  final InputStream stream = p.getInputStream();
			  final InputStream errors = p.getErrorStream();
			  new Thread(new Runnable() {
			    public void run() {
			      BufferedReader reader = null;
			      BufferedReader errorReader = null;

			      try {
			        reader = new BufferedReader(new InputStreamReader(stream));
			        String line = null;
			        while ((line = reader.readLine()) != null) {
			        	cmd.getOutput().setText(cmd.getOutput().getText()+line+"<br/>");
			        }
			        errorReader = new BufferedReader(new InputStreamReader(errors));
			        while ((line = errorReader.readLine()) != null) {
			        	cmd.getOutput().setText(cmd.getOutput().getText()+"<i>"+line+"</i><br/>");
			        }

			      } catch (Exception e) {
			        // TODO
			      } finally {
			    	readXML();
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


	public String getTempPath() {
		return tempPath;
	}


	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
	
	public void readXML() {

			StringBuilder sb = new StringBuilder();
		    try (BufferedReader br = new BufferedReader(new FileReader(tempPath))){
		    	String sCurrentLine;
		        while ((sCurrentLine = br.readLine()) != null) {
		            sb.append(sCurrentLine);
		        }		    
		    cmd.getOutput().setXml(TransInfoHtml.transformToHtml(sb.toString()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
