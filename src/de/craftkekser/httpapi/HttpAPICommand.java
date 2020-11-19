package de.craftkekser.httpapi;

import java.util.HashMap;

public class HttpAPICommand {

	private String name;
	private HashMap<String, HttpAPIAction> actionHandlers;
	private HttpAPIAction rootHandler;
	

	public HttpAPICommand(String name, HttpAPIAction rootHandler) {
		this.name = name;
		this.actionHandlers = new HashMap<>();
		this.rootHandler = rootHandler;
	}

	public void setAction(String name, HttpAPIAction action) {
		String nameNew = name.trim().toLowerCase();
		this.actionHandlers.put(nameNew, action);
	}
	
	public void removeAction(String name) {
		String nameNew = name.trim().toLowerCase();
		if(this.actionHandlers.containsKey(nameNew))
			this.actionHandlers.remove(nameNew);
	}

	public HttpAPIAction getAction(String name) {
		String nameNew = name.trim().toLowerCase();
		if(this.actionHandlers.containsKey(nameNew))
			return this.actionHandlers.get(nameNew);
		return null;
	}

	public String getName() {
		return name;
	}

	public HttpAPIAction getRootHandler() {
		return rootHandler;
	}

	public void setRootHandler(HttpAPIAction rootHandler) {
		this.rootHandler = rootHandler;
	}
	
}
