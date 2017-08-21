package com.uniovi.nmapgui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Filefinder {

	public InputStream find(String filename) throws FileNotFoundException {
		File xml =new File(System.getProperty("java.io.tmpdir")+filename+".xml");
		return new FileInputStream(xml);
	}

}
