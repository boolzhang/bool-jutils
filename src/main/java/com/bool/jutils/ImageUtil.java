package com.bool.jutils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 图形处理工具类，用于处理图片的压缩、缩放等操作
 * ClassName: ImageUtil <br/>
 * date: 2018年1月16日 下午6:41:34 <br/>
 *
 * @author Bool
 * @version
 */
public class ImageUtil {
	
	/**
	 * 
	 * reSizeImage:对图像进行等比例缩放，限制最小的一边，其他一边自动计算；
	 * @author Bool
	 * @param inputPath 图片路径
	 * @param outputPath 输出路径
	 * @param maxWidth 最大一边的宽或高
	 */
	public static void resize(String inputPath,String outputPath,int maxWidth){
		
		   File src = new File(inputPath) ;
	        try {
	        	
	            Image image = ImageIO.read(src);
	            float width = image.getWidth(null);
	            float height = image.getHeight(null);
	            
	            int fixWidth = maxWidth;
	            int fixHeight = maxWidth;
	            
	            //高图
	            if(width<height){
	            	fixHeight = (int)(fixWidth*(height/width));
	            }else{//长图
	            	fixWidth = (int)(fixHeight*(width/height));
	            }
	            
	            //比指定的小，则不做大小方面的压缩
	            if(width<fixWidth || height<fixHeight){
	            	fixWidth = (int)width;
	            	fixHeight = (int)height;
	            }
	            
	           
	    		ImageIcon ii = new ImageIcon(inputPath);
	    		Image i = ii.getImage();
	    		Image resizedImage = i.getScaledInstance(fixWidth, fixHeight, Image.SCALE_AREA_AVERAGING);

	    		// This code ensures that all the pixels in the image are loaded.
	    		Image temp = new ImageIcon(resizedImage).getImage();
	    		// Create the buffered image.
	    		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null),BufferedImage.TYPE_INT_RGB);
	    		// Copy image to buffered image.
	    		Graphics g = bufferedImage.createGraphics();
	    		// Clear background and paint the image.
	    		g.setColor(Color.white);
	    		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
	    		g.drawImage(temp, 0, 0, null);
	    		g.dispose();
	    		// Soften.
	    		float[] softenArray = { -0.125f, -0.125f, -0.125f, -0.125f, 2, -0.125f, -0.125f, -0.125f, -0.125f };
	    		Kernel kernel = new Kernel(3, 3, softenArray);
	    		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
	    		bufferedImage = cOp.filter(bufferedImage, null);
	    		
	    		try {
	    			ImageIO.write(bufferedImage, "jpg", new File(outputPath));
	    		} catch (Exception e1) {

	    		}
	        } catch (IOException e) {
	            e.printStackTrace();
	       }
	}
	
	
	/**
	 * 图片压缩，用于PNG类型，能够保留透明特性。
	 * reSizeImageTransparent:
	 * @author Bool
	 * @param inputPath
	 * @param outputPath
	 * @param width
	 * @param height
	 */
	public static void resizePNG(String inputPath, String outputPath, int width, int height) {

		File src = new File(inputPath);
		try {

			Image image = ImageIO.read(src);
			BufferedImage convertedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2D = (Graphics2D) convertedImage.createGraphics();
			g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2D.drawImage(image, 0, 0, width, height, null);
			g2D.dispose();
			ImageIO.write(convertedImage, "png", new File(outputPath));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * reSizeImage:指定宽高比来压缩，使用了第三方内核压缩，算法比较优秀；图形较为清晰，压缩jpg格式图片很好
	 * @author Bool
	 * @param inputPath
	 * @param outputPath
	 * @param width
	 * @param height
	 */
	public static void resizeJPG(String inputPath, String outputPath, int width, int height) {

		ImageIcon ii = new ImageIcon(inputPath);
		Image i = ii.getImage();
		Image resizedImage = i.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);

		// This code ensures that all the pixels in the image are loaded.
		Image temp = new ImageIcon(resizedImage).getImage();
		// Create the buffered image.
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		// Copy image to buffered image.
		Graphics g = bufferedImage.createGraphics();
		// Clear background and paint the image.
		g.setColor(Color.white);
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		// Soften.
		float[] softenArray = { -0.125f, -0.125f, -0.125f, -0.125f, 2, -0.125f, -0.125f, -0.125f, -0.125f };
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bufferedImage = cOp.filter(bufferedImage, null);
		
		try {
			ImageIO.write(bufferedImage, "jpg", new File(outputPath));
		} catch (Exception e1) {

		}
		
	}
	


}
