package de.craftkekser.httpapi;


import org.json.JSONObject;

public class HttpAPIResponse {
	
	private int responseCode;
	private JSONObject data;
	
	public HttpAPIResponse(int responseCode, JSONObject data) {
		this.setResponseCode(responseCode);
		this.setData(data);
	}
	
	public HttpAPIResponse() {
		this.setResponseCode(200);
		this.setData(new JSONObject());
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

}
