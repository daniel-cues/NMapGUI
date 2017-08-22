package com.uniovi.nmapgui;

import static org.junit.Assert.*;

import org.junit.Test;
import com.uniovi.nmapgui.model.*;

public class NMapGuiModelTests {

	@Test
	public void commandTest() {
		Command command = new Command("test");
		Output output = new Output();
		command.setFinished(true);
		command.setOutput(output);
		command.setChkUpdateFlag(true);
		
		assertTrue(command.isFinished());
		assertTrue(command.isChkUpdateFlag());
		assertEquals(output, command.getOutput());
		assertEquals("test", command.getText());

	}
	
	@Test
	public void ouputTest() {
		Output output = new Output();
		output.setFilename("testFile");
		output.setText("testText");
		output.setXml("testXML");
		
		assertEquals("testFile", output.getFilename());
		assertEquals("testText", output.getText());
		assertEquals("testXML", output.getXml());

	}

}
