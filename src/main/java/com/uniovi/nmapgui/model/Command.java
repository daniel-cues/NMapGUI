package com.uniovi.nmapgui.model;

public class Command {
	
	private String text;
	private boolean finished = false;
	private Output output = new Output();
	private boolean chkUpdateFlag = false;

	public Command() {}

    public Command(String text) {
    	this();
    	setText(text);	
    }

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

	public void setOutput(Output output) {
		this.output = output;
	}

	public boolean isChkUpdateFlag() {
		return chkUpdateFlag;
	}

	public void setChkUpdateFlag(boolean chkUpdateFlag) {
		this.chkUpdateFlag = chkUpdateFlag;
	}
    

}
