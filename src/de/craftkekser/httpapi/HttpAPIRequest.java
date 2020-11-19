package de.craftkekser.httpapi;

import java.util.HashMap;

import com.sun.net.httpserver.Headers;

public class HttpAPIRequest {
	
	private HttpMethod method;
	private Headers headers;
	private HashMap<String, String> query;
	private HashMap<String, String> body;
	private HashMap<String, String> content; // mix of query and body
	
	public HttpAPIRequest(HttpMethod method, Headers headers) {
		this.method = method;
		this.headers = headers;
		this.query = new HashMap<>();
		this.body = new HashMap<>();
		this.content = new HashMap<>();
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public Headers getHeaders() {
		return headers;
	}

	public void setHeaders(Headers headers) {
		this.headers = headers;
	}

	public HashMap<String, String> getQuery() {
		return query;
	}

	public void setQuery(HashMap<String, String> query) {
		this.query = query;
	}

	public HashMap<String, String> getBody() {
		return body;
	}

	public void setBody(HashMap<String, String> body) {
		this.body = body;
	}

	public HashMap<String, String> getContent() {
		return content;
	}

	public void setContent(HashMap<String, String> content) {
		this.content = content;
	}

}
