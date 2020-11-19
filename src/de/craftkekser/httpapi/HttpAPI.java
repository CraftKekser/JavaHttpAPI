package de.craftkekser.httpapi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpAPI {

	private HttpServer httpServer;
	private int serverPort;

	private List<HttpAPIScope> scopes;


	public HttpAPI(int httpServerPort) {
		this.serverPort = httpServerPort;
		this.scopes = Collections.synchronizedList(new ArrayList<>());
	}

	public void start() throws IOException {
		this.httpServer = HttpServer.create(new InetSocketAddress(this.getServerPort()), 0);
		this.httpServer.createContext("/", new HttpHandler() {

			@Override
			public void handle(HttpExchange exch) throws IOException {
				// Parse URL
				URI uri = exch.getRequestURI();
				String sUri = uri.toString().trim().split("\\?")[0];
				if(sUri.endsWith("/"))
					sUri = sUri.substring(0, sUri.length()-1);
				if(!sUri.startsWith("/"))
					sUri = "/"+sUri;
				System.out.println(sUri);
				String splittingRegex = "^\\/([^\\/]+){1}\\/([^\\/]+){1}\\/*(([^\\/]+\\/*)+)*$";

				String scope = sUri.replaceAll(splittingRegex, "$1");
				String command = sUri.replaceAll(splittingRegex, "$2");
				String action = sUri.replaceAll(splittingRegex, "$3");
				String query = "";
				try { query = uri.toString().trim().split("\\?")[1]; }catch(IndexOutOfBoundsException e) {}
				
				// Select scope
				HttpAPIScope httpScope = getScopeByName(scope);
				
				// Select command
				HttpAPICommand httpCommand = httpScope.getCommandByName(command);
				
				// Select action
				HttpAPIAction httpAction = action.isEmpty()?httpCommand.getRootHandler():httpCommand.getAction(action);
				
				// Execute action
				HttpAPIResponse response = httpAction.handle(new HttpAPIRequest(HttpMethod.valueOf(exch.getRequestMethod()), exch.getRequestHeaders()));

				// Send response
				response.getHeaders().forEach((header, list) -> {
					exch.getResponseHeaders().put(header, list);
				});
				
				System.out.printf("Path: " + "%s->%s->%s", scope, command, action);
				System.out.println();
				System.out.println("Query: " + query);
				System.out.println("-------------------------------");
			}
		});
		this.httpServer.start();
	}

	public void stop() {
		if(this.httpServer != null) {
			this.httpServer.stop(1); // stop after 1 sec
		}
	}

	public void registerScope(HttpAPIScope scope) {
		if(getScopeByName(scope.getName()) == null)
			this.scopes.add(scope);
	}

	public void unregisterScope(HttpAPIScope scope) {
		if(getScopeByName(scope.getName()) != null)
			this.scopes.remove(scope);
	}	
	public void unregisterScope(String name) {
		HttpAPIScope scope;
		if((scope = getScopeByName(name)) != null)
			this.scopes.remove(scope);
	}

	public HttpAPIScope getScopeByName(String name) {
		for(HttpAPIScope scope : this.scopes) {
			if(scope.getName().trim().equalsIgnoreCase(name.trim())) {
				return scope;
			}
		}
		return null;
	}

	public int getServerPort() {
		return serverPort;
	}


	public static void printarray(String[] array) {
		for(String s : array) {
			System.out.print(s + "  ");
		}
		System.out.println();
	}
}
