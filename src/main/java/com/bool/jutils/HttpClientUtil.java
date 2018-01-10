package com.bool.jutils;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil
{
  public static String getResJson(String url)
  {
    CloseableHttpClient client = HttpClients.createDefault();
    try
    {
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

  public static String postAsXML(Map<String, String> params, String url)
    throws Exception
  {
    String xmlString = StringUtil.mapToXml(params);

    CloseableHttpClient client = HttpClients.createDefault();
    HttpPost post = new HttpPost(url);

    BasicHttpEntity requestBody = new BasicHttpEntity();
    requestBody.setContent(new ByteArrayInputStream(xmlString.getBytes("UTF-8")));
    requestBody.setContentType("text/xml");
    post.setEntity(requestBody);

    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String body = EntityUtils.toString(entity , "UTF-8");

    System.out.println(body);

    return body;
  }
}