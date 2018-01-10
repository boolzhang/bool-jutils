package com.bool.jutils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

/**
 * IP获取工具类，用户获取网络请求过来的真实IP
 * ClassName: IpUtils <br/>
 * date: 2018年2月13日 下午7:27:52 <br/>
 *
 * @author Bool
 * @version
 */
public class IpUtils {
	
	/**
	 * 
	 * getClientIp:通过请求获取客户端的真实IP地址
	 * @author Bool
	 * @param request
	 * @return
	 */
	public static final String extractClientIp(HttpServletRequest request) {
		
		String ip = request.getHeader("x-forwarded-for");
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();  
        } 

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        }  
        
        System.out.println("++++++++++++++++ip:"+ip);
        //请求可能经过了代理，可能会出现多个IP段，如：121.34.144.6, 117.25.139.54
        //前面这段是真实用户IP，后面则是代理，可能有多个：真实IP,代理IP,代理2IP
        if(!StringUtils.isEmpty(ip) && ip.indexOf(",")!=-1){
        	//只获取第一段
        	ip = ip.split(",")[0].trim();
        }
        
		return ip;
	}


}
