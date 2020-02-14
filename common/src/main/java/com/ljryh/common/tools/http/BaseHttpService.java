package com.ljryh.common.tools.http;

import java.util.List;


public abstract class BaseHttpService {

	protected static final String ENCODING_UTF8 = "UTF-8";

	protected static final String ENCODING_GBK = "GBK";

	protected static final String ENCODING_ISO = "ISO-8859-1";

	protected static final String PAGE_NUM_KEY = "page";

	protected static final String PAGE_SIZE_KEY = "rows";

	protected static final long serialVersionUID = 1L;
	
	protected HttpRequest request;
	
	protected HttpResponse response;

	// 传入参数
	protected Long id;

	// 传入参数
	protected List<Long> ids;
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}
	
}
