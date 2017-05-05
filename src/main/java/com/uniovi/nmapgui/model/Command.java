package com.uniovi.nmapgui.model;

public class Command {
	
	private String text;
	private boolean finished = false;
	private Output output = new Output();
   
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

	public Output getOutput() {
		return output;
	}

	public void setCommand(Output output) {
		this.output = output;
	}
    

}
