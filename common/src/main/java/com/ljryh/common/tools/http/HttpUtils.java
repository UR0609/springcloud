package com.ljryh.common.tools.http;


public class HttpUtils {

	private static String appCookie;
	private static String appUserAgent;
	
	public final static int RESULT_CODE_NULL = 0;
	public final static int RESULT_CODE_LOGIN = 100;
	public final static int RESULT_CODE_SEAFOOD = 200;
	public final static int RESULT_CODE_SEAFOOD_REF = 202;
	public final static int RESULT_CODE_SEAFOOD_CART = 201;
	public final static int RESULT_CODE_USERORDER = 300;
	public final static int RESULT_CODE_USERORDERCOMMENT = 301;
	public final static int RESULT_CODE_USERORDERPAY = 302;
	public final static int RESULT_CODE_MESSAGE = 400;
	public final static int RESULT_CODE_COLSE = 999;

	public void cleanCookie() {
		appCookie = "";
	}
	
	public static String getCookie(HttpContext appContext) {
		if(appCookie == null || appCookie == "") {
//			appCookie = appContext.getProperty("cookie");
		}
		return appCookie;
	}
	
	public static void setCookie(HttpContext appContext, String cookies) {
		if(appCookie == null || appCookie == "") {
		}
	}
	
	public static String getUserAgent(HttpContext appContext) {
		if(appUserAgent == null || appUserAgent == "") {
//			StringBuilder ua = new StringBuilder("a");
//			ua.append('/'+appContext.getPackageInfo().versionName+'_'+appContext.getPackageInfo().versionCode);//App版本
//			ua.append("/Android");//手机系统平台
//			ua.append("/"+android.os.Build.VERSION.RELEASE);//手机系统版本
//			ua.append("/"+android.os.Build.MODEL); //手机型号
//			ua.append("/"+appContext.getAppId());//客户端唯一标识
//			appUserAgent = ua.toString();
		}
		return appUserAgent;
	}
}
