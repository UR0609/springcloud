package com.ljryh.common.tools.http;


public class HttpContext {

	public final static int PAGE_SIZE = 10;//默认分页大小
	
	public final static int PAGE_FIX_SIZE = 20;//固定分页大小
	private final static int CACHE_TIME = 60*60000;//缓存失效时间
	
	public final static String QQ_API_ID = "222222";
	public static HttpContext getAppContext() {
		return null;
	}

}
