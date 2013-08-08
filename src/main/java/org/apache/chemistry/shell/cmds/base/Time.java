package org.apache.chemistry.shell.cmds.base;

import org.apache.chemistry.shell.app.Application;
import org.apache.chemistry.shell.app.Console;
import org.apache.chemistry.shell.command.Cmd;
import org.apache.chemistry.shell.command.Command;
import org.apache.chemistry.shell.command.CommandLine;

@Cmd(syntax = "time", synopsis = "ActivateTimeStamp")
public class Time extends Command {

	@Override
	public void run(Application app, CommandLine cmdLine) throws Exception {
		boolean state = Console.getDefault().isPrintTimeStamps() ? false : true;
		// Toggle time state
		Console.getDefault().setPrintTimeStamps(state);
		if(state)			
			println("Timestamps on");
		else
			println("Timestamps off");
	}

}
