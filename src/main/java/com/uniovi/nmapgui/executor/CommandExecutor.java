package com.uniovi.nmapgui.executor;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.uniovi.nmapgui.model.*;
import com.uniovi.nmapgui.util.TransInfoHtml;

@Service
public class CommandExecutor {
	private Command cmd;
	private String tempPath = System.getProperty("java.io.tmpdir")+"/";
	private Thread commandThread; 

	
	public CommandExecutor(Command command) {
		
		cmd=command;
	}


	@Async
	public Future<Boolean> execute(){
		String filename= "nmap-scan_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
				.format(new Date())+ ".xml";
        		
		this.cmd.getOutput().setFilename(filename);
		tempPath=tempPath + filename;
		List<String> commandsList = new ArrayList<String>();
		commandsList.add("nmap");
		commandsList.addAll(Arrays.asList(cmd.getText().split(" ")));
		commandsList.addAll(Arrays.asList(new String[]{"-oX" , getTempPath(), "--webxml"}));
		try {			
			 Process p = Runtime.getRuntime().exec(commandsList.toArray(new String[]{}));
			  final InputStream stream = p.getInputStream();
			  final InputStream errors = p.getErrorStream();
			  commandThread = new Thread(new Runnable() {
			    public void run() {
			      BufferedReader reader = null;
			      BufferedReader errorReader = null;

			      try {
			        reader = new BufferedReader(new InputStreamReader(stream));
			        String line = null;
			        cmd.getOutput().setText("<pre></pre>");
			        while ((line = reader.readLine()) != null) {
			        	line = line.replace("&", "&amp;");
			        	line = line.replace( "\"", "&quot;");
			        	line = line.replace( "<", "&lt;");
			        	line = line.replace( ">", "&gt;");
			        	if (line.contains( " open "))
			        		line="<span class=\"open\">"+line+"</span>";
			        	else if (line.contains( " closed "))
			        		line="<span class=\"closed\">"+line+"</span>";
			        	else if (line.contains( " filtered "))
			        		line="<span class=\"filtered\">"+line+"</span>";
			        	cmd.getOutput().setText(cmd.getOutput().getText().replaceAll("</pre>", "\n")+line+"</pre>");
			        }
			        errorReader = new BufferedReader(new InputStreamReader(errors));
			        while ((line = errorReader.readLine()) != null) {
			        	line = line.replace("&", "&amp;");
			        	line = line.replace( "\"", "&quot;");
			        	line = line.replace( "<", "&lt;");
			        	line = line.replace( ">", "&gt;");
		        		line="<span class=\"closed\">"+line+"</span>";
			        	cmd.getOutput().setText(cmd.getOutput().getText().replaceAll("</pre>", "\n")+"<i>"+line+"</i></pre>");
			        }

			      } catch (Exception e) {
			        e.printStackTrace();
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
			  });
			  commandThread.start();
					    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new AsyncResult<Boolean>(true);
    }


	public Command getCmd() {
		return cmd;
	}


	public void setCmd(Command cmd) {
		this.cmd = cmd;
	}


	public Thread getCommandThread() {
		return commandThread;
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
