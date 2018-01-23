package com.bool.jutils;

import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云市场快递查询工具类
 * 详情参见：https://market.aliyun.com/products/57126001/cmapi011120.html?spm=5176.2020520132.101.6.ndw2WX#sku=yuncode512000008
 * @author 18365
 */
public class AliExpressUtils {

	
	//查找快递公司列表的URL
	private static final String EXPRESS_TYPE_URL = "http://jisukdcx.market.alicloudapi.com/express/type";
	
	//查询快递进度的URL
	private static final String EXPRESS_PROCESS_URL = "http://jisukdcx.market.alicloudapi.com/express/query";
	
	//请求头
	private Map<String, String> headers;
	
	//单利获取对象
	public static AliExpressUtils getInstance(String appCode) {
		return new AliExpressUtils(appCode);
	}
	
	private AliExpressUtils() {
		
	}
	
	private AliExpressUtils(String appCode) {
		headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + appCode);
	}

	/**
	 * 获取所有快递
	 * @return
	 */
	public String getAllExpressType() {
		//请求头
		return HttpClientUtil.getResJson(EXPRESS_TYPE_URL, headers, null);
	}
	
	/**
	 * 获取快递进度
	 * @param type 快递代码
	 * @param number 快递单号
	 * @return
	 */
	public String queryProcess(String type, String number) {
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("type", type);
		params.put("number", number);
		return HttpClientUtil.getResJson(EXPRESS_PROCESS_URL, headers, params);
	}
	
	
	public static void main(String[] args) {
		
		AliExpressUtils expressUtil = AliExpressUtils.getInstance("9d77634329ef4eb182620496e76f1990");
		
		String types = expressUtil.getAllExpressType();
		System.out.println(types);
		
		String process = expressUtil.queryProcess("auto", "780090657591");
		System.out.println(process);
	}
	
	
	
}
