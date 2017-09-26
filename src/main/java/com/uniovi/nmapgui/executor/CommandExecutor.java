package com.uniovi.nmapgui.executor;

public interface CommandExecutor {
	boolean execute();
	public void addObserver(CommandExecutorObserver observer) ;	
	public void removeObserver(CommandExecutorObserver observer);

}
