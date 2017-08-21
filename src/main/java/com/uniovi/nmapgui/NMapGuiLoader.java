package com.uniovi.nmapgui;

import javax.swing.JFrame;

public class NMapGuiLoader {
	
	public static void main(String[] args) {
		JFrame f = new NMapLoaderWindow();
		f.setResizable(false);
	    f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
