package com.uniovi.nmapgui.model;

public class Output {
	private String text="";
	private String xml="";
	private String filename;

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

}
