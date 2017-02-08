package com.ccb.athena.security.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.ccb.athena.security.SecCode;
import com.ccb.athena.security.SecurityException;
import com.ccb.athena.security.client.SecurityClient;
import com.ccb.athena.security.model.SecData;
import com.ccb.athena.security.model.SecEncodeRequest;
import com.ccb.athena.security.model.SecEncodeResponse;
import com.ccb.athena.security.model.SecResult;

/**
 * Servlet implementation class SecVt1EnCode
 */
public class EncryptServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2601278524209119397L;

	private static final Logger log = LogManager.getLogger(EncryptServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		log.info("加密客户端收到加密请求");
		SecEncodeResponse encodeResponse = new SecEncodeResponse();
		SecResult result = new SecResult();
		InputStream input = request.getInputStream();
		OutputStream out = response.getOutputStream();
		SecData secData = null;

		String data = URLDecoder.decode(IOUtils.toString(input, "utf-8"), "utf-8");
		SecEncodeRequest encodeRequest = JSON.parseObject(data, SecEncodeRequest.class);
		try {
			secData = SecurityClient.encrypt(encodeRequest);			
			log.error("加密成功");
			log.debug("加密后的报文为:" + new String(secData.getSecEnc(), "utf-8"));
			result.setCode(SecCode.SUCCESS);
			result.setMessage("加密成功");
		} catch (SecurityException e) {
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} finally {
			String printValue = "";
			encodeResponse.setResult(result);
			encodeResponse.setSecData(secData);
			String json = JSON.toJSONString(encodeResponse);
			log.info("对加密后的结果进行URLEncode编码");
			printValue = URLEncoder.encode(json, "utf-8");
			log.info("对加密后的结果进行URLEncode编码成功");
			log.info("开始向客户端返回加密后的结果");
			out.write(printValue.getBytes("utf-8"));
			input.close();
			out.close();
			log.info("向客户端成功返回加密后的结果");
		}
	}

}
