package com.uniovi.nmapgui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.context.ConfigurableApplicationContext;

public class NMapLoaderWindow extends JFrame {
	
	private static final String IMG_PATH = "src/main/resources/static/img/header.jpg";
	
	private JLabel image;
	private JButton start;
	private JButton stop;
	private ConfigurableApplicationContext springContext;

	
	public NMapLoaderWindow(){
		super("NMapGUI");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = getContentPane();
		BorderLayout layout = new BorderLayout();
		cp.setLayout(layout);
		cp.add(image(), BorderLayout.CENTER);		
		cp.add(buttons(), BorderLayout.SOUTH);		

	}

	private JPanel image() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(IMG_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
         ImageIcon icon = new ImageIcon(img);
         image = new JLabel(icon);
         panel.add(image);
         
         return panel;
	}

	private JPanel buttons() {
		JPanel buttons = new JPanel();
		buttons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		GridLayout gl = new GridLayout(2,1);
		gl.setHgap(10);
		gl.setVgap(5);
		buttons.setLayout(gl);
		start = new JButton("Start NMapGUI");
		start.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				start.setEnabled(false);
				String[] args = {};
				springContext = NMapGuiApplication.mainExec(args);
				start.setText("NMapGUI running");
				stop.setText("Stop NMapGUI");
				stop.setEnabled(true);
			}
		});
		buttons.add(start);

		stop = new JButton("Nmap not running");
		stop.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				stop.setEnabled(false);
				springContext.close();
				stop.setText("NMapGUI not running");
				start.setText("Start NMapGUI");
				start.setEnabled(true);
			}
		});
		stop.setEnabled(false);
		buttons.add(stop);
		return buttons;
	}

}
