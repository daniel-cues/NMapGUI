package com.uniovi.nmapgui.model;

import java.util.Random;

public class Output {
	private String text="";
	private String xml="";
	private String filename;
	private Scan scan = new Scan();
	private String id =  "el3-"+Math.abs(new Random().nextInt());

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Scan getScan() {
		return scan;
	}

	public void setScan(Scan scan) {
		this.scan = scan;
	}

}
