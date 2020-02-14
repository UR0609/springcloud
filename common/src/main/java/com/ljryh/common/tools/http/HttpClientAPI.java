package com.ljryh.common.tools.http;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;
import java.util.Map;

public class HttpClientAPI {

	public static final String UTF_8 = "UTF-8";
	public static final String DESC = "descend";
	public static final String ASC = "ascend";
	
	public static final String METHOD_GET = "get";
	
	public static final String METHOD_POST = "post";
	
	private final static int TIMEOUT_CONNECTION = 20000;
	private final static int TIMEOUT_SOCKET = 20000;
	public final static int RETRY_TIME = 3;

	public static HttpClient getHttpClient() {        
        HttpClient httpClient = new HttpClient();
		// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        // 设置 默认的超时重试处理策略
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// 设置 连接超时时间
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT_CONNECTION);
		// 设置 读数据超时时间 
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT_SOCKET);
		// 设置 字符集
		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}	
	
	public static GetMethod getHttpGet(String url, String cookie, String userAgent) {
		GetMethod httpGet = new GetMethod(url);
		// 设置 请求超时时间
		httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpGet.setRequestHeader("Host", "");
		httpGet.setRequestHeader("Connection","Keep-Alive");
		httpGet.setRequestHeader("Cookie", cookie);
		httpGet.setRequestHeader("User-Agent", userAgent);
		return httpGet;
	}
	
	public static GetMethod getHttpGet(String url, String cookie, String userAgent, String token) {
		GetMethod httpGet = new GetMethod(url);
		// 设置 请求超时时间
		httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpGet.setRequestHeader("Host", "");
		httpGet.setRequestHeader("Connection","Keep-Alive");
		httpGet.setRequestHeader("Cookie", cookie);
		httpGet.setRequestHeader("User-Agent", userAgent);
		
		if (token != null) {
			httpGet.setRequestHeader("Authorization", token);
		}
		return httpGet;
	}
	
	private static PostMethod getHttpPost(String url, String cookie, String userAgent) {
		PostMethod httpPost = new PostMethod(url);
		// 设置 请求超时时间
		httpPost.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpPost.setRequestHeader("Host", "");
		httpPost.setRequestHeader("Connection","Keep-Alive");
		httpPost.setRequestHeader("Cookie", cookie);
		httpPost.setRequestHeader("User-Agent", userAgent);
		return httpPost;
	}
	
	private static PostMethod getHttpPost(String url, String cookie, String userAgent, String token) {
		PostMethod httpPost = new PostMethod(url);
		// 设置 请求超时时间
		httpPost.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpPost.setRequestHeader("Host", "");
		httpPost.setRequestHeader("Connection","Keep-Alive");
		httpPost.setRequestHeader("Cookie", cookie);
		httpPost.setRequestHeader("User-Agent", userAgent);
		
		if (token != null) {
			httpPost.setRequestHeader("Authorization", token);
		}
		return httpPost;
	}
	
	
	private static String _MakeURL(String p_url, Map<String, Object> params) {
		StringBuilder url = new StringBuilder(p_url);
		if(url.indexOf("?")<0)
			url.append('?');

		for(String name : params.keySet()){
			url.append('&');
			url.append(name);
			url.append('=');
			url.append(String.valueOf(params.get(name)));
			//不做URLEncoder处理
//			url.append(URLEncoder.encode(String.valueOf(params.get(name)), UTF_8));
		}

		return url.toString().replace("?&", "?");
	}
	
	public static String http_get(HttpContext appContext, String url, String token) throws HttpException {
		String cookie = appContext == null ? "": HttpUtils.getCookie(appContext);
		String userAgent = appContext == null ? "": HttpUtils.getUserAgent(appContext);

		HttpClient httpClient = null;
		GetMethod httpGet = null;

		String responseBody = null;
		int time = 0;
		do{
			try
			{
				httpClient = getHttpClient();
				httpGet = getHttpGet(url, cookie, userAgent, token);
				int statusCode = httpClient.executeMethod(httpGet);
				if (statusCode != HttpStatus.SC_OK) {
					throw HttpException.http(statusCode);
				}
				responseBody = httpGet.getResponseBodyAsString();
				break;
			} catch (HttpException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw HttpException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw HttpException.network(e);
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		}while(time < RETRY_TIME);

		return responseBody;
	}

	public static String http_post(HttpContext appContext, String url, Map<String, Object> params, String token) throws HttpException {
		//System.out.println("post_url==> "+url);
		String cookie = appContext == null ? "": HttpUtils.getCookie(appContext);
		String userAgent = appContext == null ? "": HttpUtils.getUserAgent(appContext);

		HttpClient httpClient = null;
		PostMethod httpPost = null;

		//post表单参数处理
		int length = (params == null ? 0 : params.size());
		Part[] parts = new Part[length];
		int i = 0;
        if(params != null)
        for(String name : params.keySet()){
        	parts[i++] = new StringPart(name, String.valueOf(params.get(name)), UTF_8);
        	//System.out.println("post_key==> "+name+"    value==>"+String.valueOf(params.get(name)));
        }

		String responseBody = "";
		int time = 0;
		do{
			try
			{
				httpClient = getHttpClient();
				httpPost = getHttpPost(url, cookie, userAgent, token);
		        httpPost.setRequestEntity(new MultipartRequestEntity(parts,httpPost.getParams()));
		        int statusCode = httpClient.executeMethod(httpPost);
		        if(statusCode != HttpStatus.SC_OK)
		        {
		        	throw HttpException.http(statusCode);
		        }
		        else if(statusCode == HttpStatus.SC_OK)
		        {
		            Cookie[] cookies = httpClient.getState().getCookies();
		            String tmpcookies = "";
		            for (Cookie ck : cookies) {
		                tmpcookies += ck.toString()+";";
		            }
		            //保存cookie
	        		if(appContext != null && tmpcookies != ""){
//	        			appContext.setProperty("cookie", tmpcookies);
//	        			appCookie = tmpcookies;
	        		}
		        }
		     	responseBody = httpPost.getResponseBodyAsString();
		        //System.out.println("XMLDATA=====>"+responseBody);
		     	break;
			} catch (HttpException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw HttpException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw HttpException.network(e);
			} finally {
				// 释放连接
				httpPost.releaseConnection();
				httpClient = null;
			}
		}while(time < RETRY_TIME);

        return responseBody;

	}


	public static String http_post_file(HttpContext appContext, String url, Map<String, Object> params, File f) throws HttpException {
		//System.out.println("post_url==> "+url);
		String cookie = appContext == null ? "": HttpUtils.getCookie(appContext);
		String userAgent = appContext == null ? "": HttpUtils.getUserAgent(appContext);

		HttpClient httpClient = null;
		PostMethod httpPost = null;

		//post表单参数处理
		int length = (params == null ? 0 : params.size());
		Part[] parts = new Part[length + 1];
		int i = 0;
        if(params != null)
        for(String name : params.keySet()){
        	parts[i++] = new StringPart(name, String.valueOf(params.get(name)), UTF_8);
        	//System.out.println("post_key==> "+name+"    value==>"+String.valueOf(params.get(name)));
        }

        try {
			FilePart fp = new FilePart("uploadFile", f);
			parts[length] = fp;
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		String responseBody = "";
		int time = 0;
		do{
			try
			{
				httpClient = getHttpClient();
				httpPost = getHttpPost(url, cookie, userAgent);
		        httpPost.setRequestEntity(new MultipartRequestEntity(parts,httpPost.getParams()));
		        int statusCode = httpClient.executeMethod(httpPost);
		        if(statusCode != HttpStatus.SC_OK)
		        {
		        	throw HttpException.http(statusCode);
		        }
		        else if(statusCode == HttpStatus.SC_OK)
		        {
		            Cookie[] cookies = httpClient.getState().getCookies();
		            String tmpcookies = "";
		            for (Cookie ck : cookies) {
		                tmpcookies += ck.toString()+";";
		            }
		            //保存cookie
	        		if(appContext != null && tmpcookies != ""){
//	        			appContext.setProperty("cookie", tmpcookies);
//	        			appCookie = tmpcookies;
	        		}
		        }
		     	responseBody = httpPost.getResponseBodyAsString();
		        //System.out.println("XMLDATA=====>"+responseBody);
		     	break;
			} catch (HttpException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw HttpException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw HttpException.network(e);
			} finally {
				// 释放连接
				httpPost.releaseConnection();
				httpClient = null;
			}
		}while(time < RETRY_TIME);

        return responseBody;

	}

	/**
	 * get请求URL
	 * @param url
	 * @throws HttpException
	 */
	private static InputStream _get(HttpContext appContext, String url) throws HttpException {
		String cookie = HttpUtils.getCookie(appContext);
		String userAgent = HttpUtils.getUserAgent(appContext);

		HttpClient httpClient = null;
		GetMethod httpGet = null;

		String responseBody = "";
		int time = 0;
		do{
			try
			{
				httpClient = getHttpClient();
				httpGet = getHttpGet(url, cookie, userAgent);
				int statusCode = httpClient.executeMethod(httpGet);
				if (statusCode != HttpStatus.SC_OK) {
					throw HttpException.http(statusCode);
				}
				responseBody = httpGet.getResponseBodyAsString();
				break;
			} catch (HttpException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw HttpException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw HttpException.network(e);
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		}while(time < RETRY_TIME);

		//responseBody = responseBody.replaceAll("\\p{Cntrl}", "\r\n");
//		if(responseBody.contains("result") && responseBody.contains("errorCode") && appContext.containsProperty("user.uid")){
//			try {
////				Result res = Result.parse(new ByteArrayInputStream(responseBody.getBytes()));
////				if(res.getErrorCode() == 0){
////					appContext.Logout();
////					appContext.getUnLoginHandler().sendEmptyMessage(1);
////				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		return new ByteArrayInputStream(responseBody.getBytes());
	}

	/**
	 * 公用post方法
	 * @param url
	 * @param params
	 * @param files
	 * @throws HttpException
	 */
	private static InputStream _post(HttpContext appContext, String url, Map<String, Object> params, Map<String,File> files) throws HttpException {
		//System.out.println("post_url==> "+url);
		String cookie = HttpUtils.getCookie(appContext);
		String userAgent = HttpUtils.getUserAgent(appContext);

		HttpClient httpClient = null;
		PostMethod httpPost = null;

		//post表单参数处理
		int length = (params == null ? 0 : params.size()) + (files == null ? 0 : files.size());
		Part[] parts = new Part[length];
		int i = 0;
        if(params != null)
        for(String name : params.keySet()){
        	parts[i++] = new StringPart(name, String.valueOf(params.get(name)), UTF_8);
        	//System.out.println("post_key==> "+name+"    value==>"+String.valueOf(params.get(name)));
        }
        if(files != null)
        for(String file : files.keySet()){
        	try {
				parts[i++] = new FilePart(file, files.get(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
        	//System.out.println("post_key_file==> "+file);
        }

		String responseBody = "";
		int time = 0;
		do{
			try
			{
				httpClient = getHttpClient();
				httpPost = getHttpPost(url, cookie, userAgent);
		        httpPost.setRequestEntity(new MultipartRequestEntity(parts,httpPost.getParams()));
		        int statusCode = httpClient.executeMethod(httpPost);
		        if(statusCode != HttpStatus.SC_OK)
		        {
		        	throw HttpException.http(statusCode);
		        }
		        else if(statusCode == HttpStatus.SC_OK)
		        {
		            Cookie[] cookies = httpClient.getState().getCookies();
		            String tmpcookies = "";
		            for (Cookie ck : cookies) {
		                tmpcookies += ck.toString()+";";
		            }
		            //保存cookie
	        		if(appContext != null && tmpcookies != ""){
//	        			appContext.setProperty("cookie", tmpcookies);
//	        			appCookie = tmpcookies;
	        		}
		        }
		     	responseBody = httpPost.getResponseBodyAsString();
		        //System.out.println("XMLDATA=====>"+responseBody);
		     	break;
			} catch (HttpException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw HttpException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw HttpException.network(e);
			} finally {
				// 释放连接
				httpPost.releaseConnection();
				httpClient = null;
			}
		}while(time < RETRY_TIME);
        
        responseBody = responseBody.replaceAll("\\p{Cntrl}", "");
//		if(responseBody.contains("result") && responseBody.contains("errorCode") && appContext.containsProperty("user.uid")){
//			try {
////				Result res = Result.parse(new ByteArrayInputStream(responseBody.getBytes()));	
////				if(res.getErrorCode() == 0){
////					appContext.Logout();
////					appContext.getUnLoginHandler().sendEmptyMessage(1);
////				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}			
//		}
        return new ByteArrayInputStream(responseBody.getBytes());
	}
	
}
