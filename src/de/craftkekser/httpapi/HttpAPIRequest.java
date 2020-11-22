package de.craftkekser.httpapi;


import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

public class HttpAPIRequest {

	private HashMap<String, List<String>> headers;
	private HashMap<String, String> data;
	private String[] path;

	public HttpAPIRequest(HttpExchange e) {
		this.headers = new HashMap<>();
		this.data = new HashMap<>();
		e.getRequestHeaders().forEach((h, l) -> {
			headers.put(h, l);
		});
		try {
			byte[] body = e.getRequestBody().readAllBytes();
			if(body.length > 0) {
				String sBody = new String(body, StandardCharsets.UTF_8);
				String[] params = sBody.split("\\&");
				for(String s : params) {
					String[] sq = s.split("=");
					this.data.put(sq[0], sq[1]);
				}
			}
			// params
			String sParams = e.getRequestURI().getRawQuery();
			if(sParams!=null && sParams.length()>0) {
				String[] params = sParams.split("\\&");
				for(String s : params) {
					String[] sq = s.split("=");
					this.data.put(URLDecoder.decode(sq[0], StandardCharsets.UTF_8), URLDecoder.decode(sq[1], StandardCharsets.UTF_8));
				}
			}
		} catch (IOException e1) {}
		String context = e.getHttpContext().getPath();
		String restPath = e.getRequestURI().toString().substring(context.length());
		if(restPath.contains("?")) {
			restPath = restPath.substring(0, restPath.indexOf("?"));
		}
		if(restPath.startsWith("/")) {
			restPath = restPath.substring(1);
		}
		if(restPath.endsWith("/")) {
			restPath = restPath.substring(0, restPath.length()-1);
		}
		this.path = restPath.trim().length()>0?restPath.split("\\/"):new String[0];
	}

	public HashMap<String, List<String>> getHeaders() {
		return headers;
	}

	public HashMap<String, String> getData() {
		return data;
	}

	public String[] getPath() {
		return path;
	}


}
