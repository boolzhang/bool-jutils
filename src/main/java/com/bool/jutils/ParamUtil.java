package com.bool.jutils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ParamUtil
{
  public static String getSignDataIgnoreBlank(Map<String, String> params)
  {
    StringBuffer content = new StringBuffer();

    List keys = new ArrayList(params.keySet());
    Collections.sort(keys);

    for (int i = 0; i < keys.size(); i++) {
      String key = (String)keys.get(i);
      if ("sign".equals(key)) {
        continue;
      }
      String value = (String)params.get(key);
      if (StringUtils.isBlank(value)) {
        continue;
      }
      content.append(new StringBuilder().append(i == 0 ? "" : "&").append(key).append("=").append(value).toString());
    }
    return content.toString();
  }

  public static String getSignData(Map<String, String> params)
  {
    StringBuffer content = new StringBuffer();

    List keys = new ArrayList(params.keySet());
    Collections.sort(keys);

    for (int i = 0; i < keys.size(); i++) {
      String key = (String)keys.get(i);
      if ("sign".equals(key)) {
        continue;
      }
      String value = (String)params.get(key);
      if (value != null)
        content.append(new StringBuilder().append(i == 0 ? "" : "&").append(key).append("=").append(value).toString());
      else {
        content.append(new StringBuilder().append(i == 0 ? "" : "&").append(key).append("=").toString());
      }

    }

    return content.toString();
  }

  public static String mapToUrl(Map<String, String> params)
    throws UnsupportedEncodingException
  {
    StringBuilder sb = new StringBuilder();
    boolean isFirst = true;
    for (String key : params.keySet()) {
      String value = (String)params.get(key);
      if (isFirst) {
        sb.append(new StringBuilder().append(key).append("=").append(URLEncoder.encode(value, "utf-8")).toString());
        isFirst = false;
      }
      else if (value != null) {
        sb.append(new StringBuilder().append("&").append(key).append("=").append(URLEncoder.encode(value, "utf-8")).toString());
      } else {
        sb.append(new StringBuilder().append("&").append(key).append("=").toString());
      }
    }

    return sb.toString();
  }

  public static String mapToUrlNotEncode(Map<String, String> params)
  {
    StringBuilder sb = new StringBuilder();
    boolean isFirst = true;
    for (String key : params.keySet()) {
      String value = (String)params.get(key);
      if (isFirst) {
        sb.append(new StringBuilder().append(key).append("=").append(value).toString());
        isFirst = false;
      }
      else if (value != null) {
        sb.append(new StringBuilder().append("&").append(key).append("=").append(value).toString());
      } else {
        sb.append(new StringBuilder().append("&").append(key).append("=").toString());
      }
    }

    return sb.toString();
  }

  public static String getParameter(String url, String name)
  {
    if ((name == null) || (name.equals(""))) {
      return null;
    }
    name = new StringBuilder().append(name).append("=").toString();
    int start = url.indexOf(name);
    if (start < 0) {
      return null;
    }
    start += name.length();
    int end = url.indexOf("&", start);
    if (end == -1) {
      end = url.length();
    }
    return url.substring(start, end);
  }
}