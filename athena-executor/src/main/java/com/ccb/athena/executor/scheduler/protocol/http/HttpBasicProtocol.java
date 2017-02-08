package com.ccb.athena.executor.scheduler.protocol.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpBasicProtocol {

	protected static int timeOut = 300000;
	protected static boolean isTimeOut = true;

	protected CloseableHttpClient httpClient;
	protected RequestConfig requestConfig;

	public HttpBasicProtocol() {
		httpClient = HttpClients.createDefault();
		requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeOut).setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
	}

	protected byte[] send(String address, HttpEntity entity) {
		byte[] resultBytes = null;

		try {
			HttpPost httpPost = new HttpPost(address);
			httpPost.setConfig(requestConfig);

			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			if (200 != response.getStatusLine().getStatusCode())
				throw new RuntimeException("往执行机发送请求异常：" + response.getStatusLine().getStatusCode());

			resultBytes = EntityUtils.toByteArray(responseEntity);
		} catch (ClientProtocolException e) {
			throw new RuntimeException("向目标地址[" + address + "]发送http请求出现异常", e);
		} catch (IOException e) {
			throw new RuntimeException("向目标地址[" + address + "]发送http请求出现异常", e);
		} finally {
		}
		return resultBytes;
	}

	public void close() {
		if (httpClient != null) {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
