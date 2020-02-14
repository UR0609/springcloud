package com.ljryh.common.tools.http;

import java.io.File;
import java.util.Map;

public class HttpRequest {

	private String url;
	
	private String method;
	
	private String token;
	
	private File file;
	
	private Map<String, Object> param;

	public HttpContext getAppContext() {
		return null;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	
}
