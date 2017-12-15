package com.bool.jutils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 一些比较简单的类型转换器
 * ClassName: SimpleFormatter <br/>
 * date: 2017年1月13日 下午7:03:04 <br/>
 *
 * @author Bool
 * @version
 */
public class SimpleFormatter {
	
	private static final DecimalFormat DEC_FORMAT=new DecimalFormat("#.#");
	
	/**
	 * 
	 * convertFileSize:根据传入的字节数，计算文件的大小，返回xxK，xxM，xxG等文字描述
	 * @author Bool
	 * @param btyes
	 * @return
	 */
	public static String convertFileSize(Integer btyes){
		double k=btyes/1024.0;
		if(k<1024){
			return DEC_FORMAT.format(k)+"K";
		}
		double m=k/1024.0;
		
		if(m<1024.0){
			
			return DEC_FORMAT.format(m)+"M";
		}
		
		double g=m/1024.0;
		
		if(g<1024.0){
			return DEC_FORMAT.format(g)+"G";
			
		}
		
		double t=m/1024.0;
		
		if(t<1024.0){
			return DEC_FORMAT.format(g)+"T";
		}

		return "";
	}
	
	/**
	 * convertTime:根据给定的时间，计算出是几秒前、几分钟前、几个月前..
	 * @author Bool
	 * @param date
	 * @return
	 */
	public static String timeExpName(Date date){
		long timeMillions=date.getTime();
		long timeMillionsNow=new Date().getTime();
		long second=(timeMillionsNow-timeMillions)/1000;
		
		if(second<60){
			return second+"秒前";
		}
		
		long minute=second/60;
		
		if(minute<60){
			return minute+"分钟前";
		}
		long hour=minute/60;
		if(hour<24){
			return hour+"小时前";
		}
		long day=hour/24;
		if(day<30){
			return day+"天前";
		}
		long month=day/30;
		
		if(day<30){
			month =1;
		}
		
		if(month<12){
			return month+"个月前";
		}
		
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
		
	}
	
	
	/**
	 * convertCount:转换成数量的描述词，如多少K，多少W
	 * @author Bool
	 * @param count
	 * @return
	 */
	public static String convertCount(Integer count){
		
		if(count<9999){
			return count+"";
		}else{
			double d=count/10000.0;
			return new DecimalFormat("#").format(d)+"万";
		}
	}
	
}
