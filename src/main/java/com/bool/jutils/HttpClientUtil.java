package com.bool.jutils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	/**
	 * GET方法返回JSON数据
	 * 
	 * @param url
	 * @return
	 */
	public static String getResJson(String url, Map<String, String> headers, Map<String, String> params) {
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			
			String fullUrl = buildParamsUrl(url, params);
			HttpGet httpGet = new HttpGet(fullUrl);

			// 循环加入文件头
			if (headers != null && !headers.isEmpty()) {
				for (String key : headers.keySet()) {
					httpGet.addHeader(key, headers.get(key));
				}
			}

			CloseableHttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String body = EntityUtils.toString(entity, "UTF-8");
			
			return body;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	
	/**
	 * 构造一个带参数的GET形式URL
	 * @param url
	 * @param params
	 * @return
	 */
	public static String buildParamsUrl(String url, Map<String, String> params) {

		// 拼接参数
		if (params != null && !params.isEmpty()) {
			
			StringBuffer sb = new StringBuffer(url);

			//判断URL是否已经有问题号了
			if (url.indexOf("?") == -1) {
				sb.append("?");
			}
			
			for (String key : params.keySet()) {

				if(!sb.toString().endsWith("?")) {
					sb.append("&");
				}
				
				String value = params.get(key);
				if (StringUtils.isBlank(value)) {
					value = "";
				} else {
					// 值做一下URL转码
					try {
						value = URLEncoder.encode(value, "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
				}
				sb.append(key).append("=").append(value);
			}
			
			
			
			return sb.toString();
		}
		
		return url;
	}

	/**
	 * GET方法返回JSON数据
	 * 
	 * @param url
	 * @return
	 */
	public static String getResJson(String url) {
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpGet post = new HttpGet(url);

			CloseableHttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String body = EntityUtils.toString(entity, "UTF-8");
			System.out.println(body);

			return body;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 参数通过xml格式post到某个URL上
	 * 
	 * @param params
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String postAsXML(Map<String, String> params, String url) throws Exception {

		// 将Map转换成字符串
		String xmlString = StringUtil.mapToXml(params);

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);

		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody.setContent(new ByteArrayInputStream(xmlString.getBytes("UTF-8")));
		requestBody.setContentType("text/xml");
		post.setEntity(requestBody);

		CloseableHttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		String body = EntityUtils.toString(entity, "UTF-8");

		return body;
	}
}