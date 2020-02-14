package com.ljryh.common.tools.http;

import org.apache.commons.httpclient.HttpException;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * 应用程序异常类：用于捕获异常和提示错误信息
 * @author  ufobject
 * @version 1.0
 * @created 2014-08-01
 */
public class IcsException extends Exception{

	private static final long serialVersionUID = 3088876143648422059L;

	private final static boolean Debug = false;//是否保存错误日志

	/** 定义异常类型 */
	public final static byte TYPE_NETWORK 	= 0x01;
	public final static byte TYPE_SOCKET	= 0x02;
	public final static byte TYPE_HTTP_CODE	= 0x03;
	public final static byte TYPE_HTTP_ERROR= 0x04;
	public final static byte TYPE_XML	 	= 0x05;
	public final static byte TYPE_IO	 	= 0x06;
	public final static byte TYPE_RUN	 	= 0x07;
	public final static byte TYPE_JSON	 	= 0x08;
	public final static byte TYPE_CHECK 	= 0x09;
	private byte type;
	private int code;

	/** 系统默认的UncaughtException处理类 */
	private UncaughtExceptionHandler mDefaultHandler;

	private IcsException(){
//		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	private IcsException(byte type, int code, Exception excp) {
		super(excp);
		this.type = type;
		this.code = code;

	}
	public int getCode() {
		return this.code;
	}
	public int getType() {
		return this.type;
	}

	public static IcsException check(int code) {
		return new IcsException(TYPE_CHECK, code, null);
	}

	public static IcsException http(int code) {
		return new IcsException(TYPE_HTTP_CODE, code, null);
	}

	public static IcsException http(Exception e) {
		return new IcsException(TYPE_HTTP_ERROR, 0 ,e);
	}

	public static IcsException socket(Exception e) {
		return new IcsException(TYPE_SOCKET, 0 ,e);
	}


	public static IcsException io(Exception e) {
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			return new IcsException(TYPE_NETWORK, 0, e);
		}
		else if(e instanceof IOException){
			return new IcsException(TYPE_IO, 0 ,e);
		}
		return run(e);
	}

	public static IcsException xml(Exception e) {
		return new IcsException(TYPE_XML, 0, e);
	}

	public static IcsException json(Exception e) {
		return new IcsException(TYPE_JSON, 0, e);
	}



	public static IcsException run(Exception e) {
		return new IcsException(TYPE_RUN, 0, e);
	}

	public static IcsException network(Exception e) {
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			return new IcsException(TYPE_NETWORK, 0, e);
		}
		else if(e instanceof HttpException){
			return http(e);
		}
		else if(e instanceof SocketException){
			return socket(e);
		}
		return http(e);
	}

	/**
	 * 获取APP异常崩溃处理对象
	 * @param
	 * @return
	 */
	public static IcsException getAppExceptionHandler(){
		return new IcsException();
	}


}
