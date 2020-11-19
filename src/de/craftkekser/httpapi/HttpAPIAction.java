package de.craftkekser.httpapi;

public interface HttpAPIAction {

	public HttpAPIResponse handle(HttpAPIRequest request);
	
}
