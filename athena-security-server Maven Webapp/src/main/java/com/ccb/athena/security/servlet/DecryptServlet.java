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
import com.ccb.athena.security.model.SecDecodeRequest;
import com.ccb.athena.security.model.SecDecodeResponse;
import com.ccb.athena.security.model.SecResult;

/**
 * 解密 Servlet implementation class SecVt1DeCode
 */
public class DecryptServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3955883726262887231L;
	
	private static final Logger log = LogManager.getLogger(DecryptServlet.class);

	public DecryptServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		log.info("解密客户端收到解密密请求");
		SecDecodeResponse decodeResponse = new SecDecodeResponse();
		SecResult result = new SecResult();
		InputStream input = request.getInputStream();
		OutputStream out = response.getOutputStream();
		byte decodeMessage[] = null;// 解密后的报文
		
		String data = URLDecoder.decode(IOUtils.toString(input, "utf-8"), "utf-8");
		SecDecodeRequest decodeRequest = JSON.parseObject(data, SecDecodeRequest.class);
		
		try {
			decodeMessage = SecurityClient.decrypt(decodeRequest);
			log.info("解密成功");
			log.debug("解密后的报文为:" + new String(decodeMessage, "utf-8"));
			result.setCode(SecCode.SUCCESS);
			result.setMessage("解密成功");
		} catch (SecurityException e) {
			log.error("解密失败", e);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setCode(SecCode.UN_KNOWN_ERROR);
			result.setMessage("解密密失败:" + e.getLocalizedMessage());
			log.error("解密失败:未知异常", e);
		} finally {
			String printValue = "";
			decodeResponse.setMessage(decodeMessage);
			decodeResponse.setResult(result);
			String json = JSON.toJSONString(decodeResponse);
			// log.info("将响应对象转换成json："+json);
			log.info("对解密后的结果进行URLEncode编码");
			printValue = URLEncoder.encode(json, "utf-8");
			log.info("对输出报文进行URLEncode编码成功");
			log.info("开始向客户端返回解密后的结果");
			out.write(printValue.getBytes("utf-8"));
			input.close();
			out.close();
			log.info("向客户端成功返回解密后的结果");
		}
	}
}
