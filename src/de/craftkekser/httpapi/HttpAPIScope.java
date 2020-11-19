package de.craftkekser.httpapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HttpAPIScope {

	private String name;
	private List<HttpAPICommand> commands;

	public HttpAPIScope(String name) {
		this.name = name;
		this.commands = Collections.synchronizedList(new ArrayList<>());
	}

	public void registerCommand(HttpAPICommand command) {
		if(getCommandByName(command.getName()) == null)
			this.commands.add(command);
	}

	public void unregisterCommand(HttpAPICommand command) {
		if(getCommandByName(command.getName()) != null)
			this.commands.remove(command);
	}
	public void unregisterCommand(String name) {
		HttpAPICommand command;
		if((command = getCommandByName(name)) != null)
			this.commands.remove(command);
	}

	public HttpAPICommand getCommandByName(String name) {
		for(HttpAPICommand command : this.commands) {
			if(command.getName().trim().equalsIgnoreCase(name.trim())) {
				return command;
			}
		}
		return null;
	}
	public String getName() {
		return name;
	}

}
