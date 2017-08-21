package com.uniovi.nmapgui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.context.ConfigurableApplicationContext;


public class NMapLoaderWindow extends JFrame {
	
	
	private static final long serialVersionUID = 3028171478038465651L;


	private static final String IMG_PATH = "static/img/header.jpg";

	
	private JLabel image;
	private JButton start;
	private JButton stop;
	private ConfigurableApplicationContext springContext;
	private JButton go;

	
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
			img = ImageIO.read(NMapLoaderWindow.class.getClassLoader().getResource(IMG_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
         ImageIcon icon = new ImageIcon(img);
         image = new JLabel(icon);
         image.setLayout(new FlowLayout(FlowLayout.RIGHT));

         
         go= new JButton("Go!");
		 go.setEnabled(false);
		 
		 go.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					openWebpage(new URL("http://localhost:8080/nmap"));
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}
		 });
		          image.add(go);
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
				go.setEnabled(true);
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
				go.setEnabled(false);
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

	
	
	
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	public static void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
}
