package com.busi.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description: 水印工具类
 * <p>创建日期：2019年4月18日-上午9:56:50</p>
 * @version V1.0
 * @author lichao
 * @see
 */
public class WaterMarkUtil {
	
	protected static Logger logger = LoggerFactory.getLogger(WaterMarkUtil.class);
	
	/**
	 * @Description: 创建水印图片
	 * <p>创建人：lichao,  2019年6月5日  上午11:18:20</p>
	 * <p>修改人：lichao,  2019年6月5日  上午11:18:20</p>
	 * @param filePath
	 * @throws Exception
	 */
	public static void creatWaterImg(String filePath) throws Exception {
		if (StringUtils.isEmpty(filePath)) {
			logger.debug("图片不存在,无须创建对应水印图片");
			return ;
		}
		String path = filePath.substring(0, filePath.lastIndexOf("/"));
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
		String suffix = filePath.substring(filePath.lastIndexOf("."));
		File file = new File(filePath);
		createWaterImg(file, path, fileName, suffix);
	}
	
	/**
	 * @Description: 创建带水印的图片
	 * <p>创建人：lichao,  2019年4月30日  上午9:42:52</p>
	 * <p>修改人：lichao,  2019年4月30日  上午9:42:52</p>
	 * @param is 图片输入流
	 * @param path 原图片路径
	 * @param fileName 原图片名称
	 * @param fileType 原图片类型
	 */
	public static void createWaterImg(File srcFile, String path, String fileName, String fileType) throws Exception {
		String encFullPath = path + "/water_" + encrypt(fileName) + fileType;
		FileUtils.copyFile(srcFile, new File(encFullPath));
		words(encFullPath);
		
		// 加密水印图片
		FileEncAndDec.encFile(encFullPath);
	}
	
	/**
	 * @Description: 获取水印图片名称对应的真实名称
	 * <p>创建人：lichao,  2019年6月5日  上午9:59:21</p>
	 * <p>修改人：lichao,  2019年6月5日  上午9:59:21</p>
	 * @param waterName
	 * @return
	 */
	public static String getRealImgName(String waterName){
		if(waterName.startsWith("water_")){
			String suf = waterName.substring(waterName.lastIndexOf("."));
			String name = waterName.substring(waterName.indexOf("_")+1, waterName.lastIndexOf("."));
			name = decrypt(name);
			return name.concat(suf);
    	}
		return waterName;
	}
	
	/**
	 * @Description: 获取文件名为fileName对应的水印图片名称
	 * <p>创建人：lichao,  2019年4月30日  上午10:09:52</p>
	 * <p>修改人：lichao,  2019年4月30日  上午10:09:52</p>
	 * @param fileName
	 * @return
	 */
	public static String getWaterImgName(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return "";
		}
		String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		String encName = "";
		try {
			encName = "water_" + encrypt(fileName) + fileType;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encName;
	}
	
	public static void words(String imgPath) throws IOException{
		words(imgPath, 0.6f, Color.RED, "GD ChinaMobile        GD ChinaMobile        GD ChinaMobile", "jpg", imgPath);
	}
	
	/**
	 * 在源图片上设置水印文字
	 */
	public static void words(String srcImagePath, float alpha, Color color,
			String inputWords, String imageFormat, String toPath) throws IOException {
		FileOutputStream fos = null;
		try {
			// 读取图片
			BufferedImage image = ImageIO.read(new File(srcImagePath));

			// 创建java2D对象
			Graphics2D g2d = image.createGraphics();
			// 用源图像填充背景
			g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null, null);
			// 设置水印透明度
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			// 为 Graphics2D 上下文设置 Composite。 Composite 用于所有绘制方法中，如 drawImage、
			// drawString、draw 和 fill。 它指定新的像素如何在呈现过程中与图形设备上的现有像素组合。
			g2d.setComposite(ac);

			// 计算文字尺寸
			int fontSize = 30;
			if (image.getWidth() < 400) {
				fontSize = 10;
			} else if (image.getWidth() < 700) {
				fontSize = 30;
			} else {
				fontSize = 60;
			}

			// 设置文字字体名称、样式、大小
			AffineTransform transform = new AffineTransform();
			transform.rotate(Math.toRadians(-45), 0, 0);
			Font font = new Font("Arial", Font.PLAIN, fontSize);
			g2d.setFont(font.deriveFont(transform));// 倾斜40度
			// g2d.setFont(font);// 设置字体样式
			g2d.setColor(color);// 设置字体颜色

			int lineNumb = image.getHeight() / 4;
			// 开始绘制水印
			for (int i = 0; i < 8; i++) {
				g2d.drawString(inputWords, 10, 180 + i * lineNumb); // 输入水印文字及其起始x、y坐标
			}
			g2d.dispose();
			// 将水印后的图片写入toPath路径中
			fos = new FileOutputStream(toPath);
			ImageIO.write(image, imageFormat, fos);
		}
		// 文件操作错误抛出
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}
	
	/**
	 * 在源图像上设置图片水印
	 */
	public static void image(String srcImagePath, String appendImagePath, float alpha, int x, int y, int width,
			int height, String imageFormat, String toPath) throws IOException {
		FileOutputStream fos = null;
		try {
			// 读图
			BufferedImage image = ImageIO.read(new File(srcImagePath));
			// 创建java2D对象
			Graphics2D g2d = image.createGraphics();
			// 用源图像填充背景
			g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null, null);

			// 关键地方
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			g2d.setComposite(ac);

			BufferedImage appendImage = ImageIO.read(new File(appendImagePath));
			g2d.drawImage(appendImage, x, y, width, height, null, null);
			g2d.dispose();
			fos = new FileOutputStream(toPath);
			ImageIO.write(image, imageFormat, fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}
	
	/**
	 * @Description: 加密
	 * <p>创建人：lichao,  2019年6月5日  上午11:05:10</p>
	 * <p>修改人：lichao,  2019年6月5日  上午11:05:10</p>
	 * @param name
	 * @return
	 */
	private static String encrypt(String name){
		if (StringUtils.isEmpty(name) || name.length() <= 10) {
			return name;
		}
		String pre = name.substring(0, 10);
		String suf = name.substring(10);
		return suf.concat(pre);
	}
	
	/**
	 * @Description: 解密
	 * <p>创建人：lichao,  2019年6月5日  上午11:05:16</p>
	 * <p>修改人：lichao,  2019年6月5日  上午11:05:16</p>
	 * @param name
	 * @return
	 */
	private static String decrypt(String name){
		if (StringUtils.isEmpty(name) || name.length() <= 10) {
			return name;
		}
		String suf = name.substring(name.length()-10);
		String pre = name.substring(0, name.length()-10);
		return suf.concat(pre);
	}
	
}
