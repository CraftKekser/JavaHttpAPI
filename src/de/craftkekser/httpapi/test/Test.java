package de.craftkekser.httpapi.test;

import java.io.IOException;

import de.craftkekser.httpapi.HttpAPI;

public class Test {

	public static void main(String[] args) throws IOException {
		
		HttpAPI api = new HttpAPI(8080);
		
		api.start();
	}

}
