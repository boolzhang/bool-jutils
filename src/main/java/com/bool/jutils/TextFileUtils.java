package com.bool.jutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;

/**
 * 文本处理类，用于处理读写一些文件内容，写一些日志等
 * ClassName: TextFileUtils <br/>
 * date: 2018年1月13日 下午6:49:04 <br/>
 *
 * @author Bool
 * @version
 */
public class TextFileUtils {
	
	

	/**
	 * 
	 * readText:将指定路径的文件读出里面的内容，返回一个字符串
	 * @author Bool
	 * @param filePath
	 * @return
	 */
	public static String readText(String filePath){
		
		String s;
		StringBuffer sb=new StringBuffer();
		
		try{
			File f = new File(filePath);
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedReader input = new BufferedReader(new FileReader(f));
			while ((s = input.readLine()) != null) {
				sb.append(s);
			}
			input.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return sb.toString();
	}
	

	/**
	 * append:将一个字符写入一个已有的文件中，通过追加的方法追加到内容的末尾；
	 * @author Bool
	 * @param filePath
	 * @param data
	 */
	public static synchronized void append(String filePath , String data){
		
		String s;
		StringBuffer sb=new StringBuffer();
		
		try {
			
			//创建文件夹
			String dirPath=filePath.substring(0,filePath.lastIndexOf("/")+1);
			File dir=new File(dirPath);
			
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			File f = new File(filePath);
			if (!f.exists()) {
				f.createNewFile();
			}
			;
			BufferedReader input = new BufferedReader(new FileReader(f));
					

			while ((s = input.readLine()) != null) {
				sb.append(s);
				sb.append("\n");
			}
			input.close();
			sb.append(data);
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(sb.toString());
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
