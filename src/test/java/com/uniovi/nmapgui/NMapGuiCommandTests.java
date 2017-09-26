package com.uniovi.nmapgui;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.uniovi.nmapgui.executor.CommandExecutorImpl;
import com.uniovi.nmapgui.model.Command;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class NMapGuiCommandTests {

	
	@Test
	public void commandExecutorTest() throws InterruptedException, ExecutionException {
		
		CommandExecutorImpl exec = new CommandExecutorImpl(new Command("scanme.nmap.org"));
		
		exec.execute();
		exec.getCommandThread().join();
		assertEquals("scanme.nmap.org",exec.getCmd().getText());
		
		exec.setCmd(new Command("-oA"));
		exec.execute();
		exec.getCommandThread().join();

	}
	
	

}
