package com.uniovi.nmapgui.executor;

import com.uniovi.nmapgui.model.Command;

public interface CommandExecutorObserver {
	
	 public void finishedCommand(Command cmd);

}
