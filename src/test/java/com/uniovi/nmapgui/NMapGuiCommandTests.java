package com.uniovi.nmapgui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.uniovi.nmapgui.executor.CommandExecutor;
import com.uniovi.nmapgui.model.Command;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class NMapGuiCommandTests {

	
	@Test
	public void commandExecutorTest() throws InterruptedException, ExecutionException {
		
		CommandExecutor exec = new CommandExecutor(new Command("scanme.nmap.org"));
		
		Future<Boolean> future = exec.execute();
		exec.getCommandThread().join();
		assertTrue(future.get());
		assertEquals("scanme.nmap.org",exec.getCmd().getText());
		
		exec.setCmd(new Command("-oA"));
		future = exec.execute();
		exec.getCommandThread().join();
		assertTrue(future.get());

	}
	
	

}
