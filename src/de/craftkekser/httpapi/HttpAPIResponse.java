package de.craftkekser.httpapi;

import com.sun.net.httpserver.Headers;

public class HttpAPIResponse {
	
	private int responseCode;
	private String content;
	private Headers headers;
	
	public HttpAPIResponse(int responseCode, String content, Headers headers) {
		this.responseCode = responseCode;
		this.content = content;
		this.headers = headers;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Headers getHeaders() {
		return headers;
	}

	public void setHeaders(Headers headers) {
		this.headers = headers;
	}

}
