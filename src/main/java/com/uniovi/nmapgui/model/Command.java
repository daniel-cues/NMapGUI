package com.uniovi.nmapgui.model;

public class Command {
	
	private String text;
	private boolean finished = false;
   
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
    

}
