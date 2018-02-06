package com.bool.jutils.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * WEB跨域过滤器，如有跨域需求，增加此过滤器就行了
 * ClassName: CrossdFilter <br/>
 * date: 2018年1月13日 下午7:26:43 <br/>
 *
 * @author Bool
 * @version
 */
public class CrossdFilter extends OncePerRequestFilter {

	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		//增加跨域标识
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		//允许全部头
		response.addHeader("Access-Control-Allow-Headers", "xxk-token");
		
		//允许全部头
		response.addHeader("Access-Control-Allow-Headers", "user-token");
		
		chain.doFilter(request, response);
	}


}