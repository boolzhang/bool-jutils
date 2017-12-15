package com.bool.jutils;

import java.util.Map;

/**
 * 一些字符的处理，如：将map转换成xml，xml转换成map，从文字中提取中文，判断文字是否全中文
 * ClassName: StringUtil <br/>
 * date: 2018年1月13日 下午7:07:28 <br/>
 *
 * @author Bool
 * @version
 */
public class StringUtil {
	
	
	 /**
     * 将MAP转换成一个xml格式，格式为<xml><key>value</key>...</xml>
     * @param params
     * @return
     */
    public static String mapToXml(Map<String, String> params){
    	StringBuffer sb = new StringBuffer("<xml>");
    	for(String key:params.keySet()){
    		sb.append("<")
    		.append(key).append(">")
    		.append(params.get(key))
    		.append("</").append(key).append(">");
    	}
    	
    	sb.append("</xml>");
    	return sb.toString();
    }

    
    /**
     * 
     * isChineseChar:判断一个字符是否是中文的
     * @author Bool
     * @param c
     * @return
     */
    public static boolean isChineseChar(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
 

    /**
     * isChinese:判断一些字符是否全部是中文的；
     * @author Bool
     * @param strName
     * @return
     */
    public static boolean isChineseStr(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChineseChar(c)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * extractChinese:从一段字符中，把中文提取出来；
     * @author Bool
     * @param strName
     * @return
     */
    public static String extractChinese(String strName) {
        char[] ch = strName.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChineseChar(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
    	System.out.println(extractChinese("你好，我是聪明笨狗，It's me!"));
	}

}
