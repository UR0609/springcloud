package com.ljryh.common.tools.http;

import com.ljryh.common.utils.FastjsonUtil;

import java.util.List;
import java.util.Map;

public class HttpService extends BaseHttpService {
	private static HttpService instance;
	
	private HttpService(){

    }
    
    public static synchronized HttpService getInstance(){
        if(instance==null){
            instance=new HttpService();
        }
        return instance;
    }
    
	public static HttpResponse execute(HttpRequest request) {
		
		if (request == null) {
			System.out.println( "[HttpService execute]request is null, url:" + request.getUrl());
			return null;
		}
		try {
			String responseBody = null;
			//"http://116.62.69.42:10010/shop/search"
			String method = request.getMethod();
			if (method.equalsIgnoreCase(HttpClientAPI.METHOD_GET)) {
				responseBody = HttpClientAPI.http_get(request.getAppContext(), request.getUrl(), request.getToken());
			}else {
				if (request.getFile() != null) {
					responseBody = HttpClientAPI.http_post_file(request.getAppContext(), request.getUrl(), request.getParam(), request.getFile());
				}else {
					responseBody = HttpClientAPI.http_post(request.getAppContext(), request.getUrl(), request.getParam(), request.getToken());
				}
			}
			if (responseBody == null) {
				System.out.println( "[HttpService execute]responseBody is null, url:" + request.getUrl());
			}
			Map<String, Object> result = null;
			//解析
			try {
				result = FastjsonUtil.stringToCollect(responseBody);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result == null) {
				System.out.println( "[HttpService execute]result is null, url:" + request.getUrl());
			}
			HttpResponse response = new HttpResponse();
			if (!result.containsKey("code")) {
				System.out.println( "[HttpService execute]result status is null, url:" + request.getUrl());
				return null;
			}
			response.setStatus(Integer.valueOf(result.get("code").toString()));
			response.setIsSuccess(Boolean.valueOf(result.get("isSuccess").toString()));
			if (result.containsKey("msg")) {
				response.setMsg((result.get("msg").toString()));
			}
			if (result.containsKey("data")) {
				try {
					Object obj = result.get("data");
					if (obj instanceof Map) {
						response.setResultMap((Map)obj);
					}else if (obj instanceof List) {
						response.setResultList((List)obj);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println( "[HttpService execute]request url:" + request.getUrl() +",result status:" + response.getStatus());

			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
