package com.ccb.athena.executor.scheduler.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpClient {
	/**
	 * 向指定的http地址发送消息,接收返回结果
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	
	private static String DEFAULT_CHAR_SET = "UTF-8";
	
	private static int timeOut = 300000;
	private static boolean isTimeOut = true;
	
	
	
	
	public static byte[] sendHttp(Map<String, String> params,String url,String sndEncoding,String rcvEncoding) throws RuntimeException{
		byte [] resultBytes = null;
		
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			    HttpPost httpPost = new HttpPost(url);
			    if(isTimeOut){
			    	RequestConfig requestConfig =  RequestConfig.custom().setConnectionRequestTimeout(timeOut).setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
			    	httpPost.setConfig(requestConfig);
			    }
			    List<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>();
			    if(params!=null&&params.size()>0){
			    	  BasicNameValuePair param =null;
			    	  
			    	  Set<String> keys = params.keySet();
			    	  for (String key : keys) {
			    		  param = new BasicNameValuePair(key, params.get(key));
			    		  paramList.add(param);
					}
			    }
			    httpPost.setEntity(new UrlEncodedFormEntity(paramList,rcvEncoding));
			    response = httpclient.execute(httpPost);
			   HttpEntity entity = response.getEntity();
			   if (200==response.getStatusLine().getStatusCode()){
					   resultBytes = EntityUtils.toByteArray(entity);
				
			   } else {
				   throw new RuntimeException("往执行机发送请求异常："+response.getStatusLine().getStatusCode());
			   }
			   
		} catch (Exception e) {
			 throw new RuntimeException("往执行机发送请求异常："+e.getMessage());
		} finally{
			if(httpclient!=null){
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultBytes;
	}
	
	
	public static String sendHttp(String message,String address) {
		return sendHttp(message,address,DEFAULT_CHAR_SET,DEFAULT_CHAR_SET);
	}
	
	public static String sendHttp(String message,String address,String encoding) {
		return sendHttp(message,address,encoding,encoding);
	}
	
	public static String sendHttp(String message,String address,String sndEncoding,String rcvEncoding) throws RuntimeException{
		String result = null;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(address);
		if(isTimeOut){
	    	RequestConfig requestConfig =  RequestConfig.custom().setConnectionRequestTimeout(timeOut).setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
	    	post.setConfig(requestConfig);
	    }
		HttpEntity requestEntity = null;
		requestEntity = new StringEntity(message,sndEncoding);
		post.setEntity(requestEntity);

		HttpResponse res = null;
		try {
			res = client.execute(post);
			HttpEntity resEntity = res.getEntity();
			result = EntityUtils.toString(resEntity, rcvEncoding);
		} catch (ClientProtocolException e) {
			throw new RuntimeException("向目标地址["+address+"]发送http请求出现异常", e);
		} catch (IOException e) {
			throw new RuntimeException("向目标地址["+address+"]发送http请求出现异常", e);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					throw new RuntimeException("向目标地址["+address+"]发送http请求出现异常", e);
				}
			}
		}
		return result;
	}
	
	
	public static byte[] sendHttp(byte []message,String address) {
		return sendHttp(message,address,DEFAULT_CHAR_SET,DEFAULT_CHAR_SET);
	}
	
	public static byte[] sendHttp(byte []message,String address,String encoding) {
		return sendHttp(message,address,encoding,encoding);
	}
	
	public static byte[] sendHttp(byte[]message,String address,String sndCharSet,String rcvCharSet) throws RuntimeException{
		byte resultBytes[] = null;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(address);
		if(isTimeOut){
	    	RequestConfig requestConfig =  RequestConfig.custom().setConnectionRequestTimeout(timeOut).setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
	    	post.setConfig(requestConfig);
	    }
		HttpEntity requestEntity = new ByteArrayEntity(message);
		post.setEntity(requestEntity);

		HttpResponse res = null;
		try {
			res = client.execute(post);
			HttpEntity resEntity = res.getEntity();
			resultBytes = EntityUtils.toByteArray(resEntity);
		} catch (ClientProtocolException e) {
			throw new RuntimeException("向目标地址["+address+"]发送http请求出现异常", e);
		} catch (IOException e) {
			throw new RuntimeException("向目标地址["+address+"]发送http请求出现异常", e);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					throw new RuntimeException("向目标地址["+address+"]发送http请求出现异常", e);
				}
			}
		}
		return resultBytes;
	}
	
	
}
