package de.craftkekser.httpapi;


import java.util.HashMap;

import org.json.JSONObject;

public class HttpAPIResponse {
	
	private int responseCode;
	private JSONObject data;
	private HashMap<String, String> headers;
	
	public HttpAPIResponse(int responseCode, JSONObject data) {
		this.setResponseCode(responseCode);
		this.setData(data);
		this.headers = new HashMap<>();
	}
	
	public HttpAPIResponse() {
		this.setResponseCode(200);
		this.setData(new JSONObject());
		this.headers = new HashMap<>();
	}
	
	/**
	 * Sets an http header
	 * 
	 * Content-Type will always be set to text/json
	 * 
	 * @param header
	 * @param value
	 */
	public void setHeader(String header, String value) {
		this.headers.put(header, value);
	}
	
	/**
	 * Removes an http header from the request
	 * 
	 * @param header
	 */
	public void unsetHeader(String header) {
		this.headers.remove(header);
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

}
