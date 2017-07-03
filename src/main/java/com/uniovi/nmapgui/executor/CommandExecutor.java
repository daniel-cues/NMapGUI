package com.uniovi.nmapgui.executor;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.apache.commons.lang.ArrayUtils;
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
		String[] commands = (String[])ArrayUtils.addAll(new String[]{"nmap"},command.getText().split(" "));
		commands = (String[]) ArrayUtils.addAll(commands, new String[]{"-oX" , tempPath});
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
