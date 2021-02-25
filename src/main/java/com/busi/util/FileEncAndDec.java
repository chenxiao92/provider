package com.busi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @Description: 文件加解密
 * <p>创建日期：2019年6月24日-下午1:31:57</p>
 * @version V1.0
 * @author lichao
 * @see
 */
public class FileEncAndDec {
	protected static Logger logger = LoggerFactory.getLogger(FileEncAndDec.class);
	private static final int numOfEncAndDec = 0x2019; // 加密解密秘钥
	private static int dataOfFile = 0; // 文件字节内容

	/**
	 * @Description: 加密图片
	 * <p>创建人：lichao,  2019年6月25日  下午3:17:15</p>
	 * <p>修改人：lichao,  2019年6月25日  下午3:17:15</p>
	 * @param imagePath
	 */
	public static void encFile(String imagePath) {
		String path = imagePath.substring(0, imagePath.lastIndexOf(File.separator)+1);
		String name = imagePath.substring(imagePath.lastIndexOf(File.separator)+1, imagePath.lastIndexOf("."));
		String fix = imagePath.substring(imagePath.lastIndexOf("."));
		String temp = path + "tp" + fix;
		File srcFile = new File(imagePath);// 原文件
		File encFile = new File(temp);// 加密后图像
		if (!srcFile.exists()) {
			logger.info("原图片不存在");
			return;
		}
		InputStream fis = null;
		OutputStream fos = null;
		try {
			if (!encFile.exists()) {
				logger.info("创建加密图片");
				encFile.createNewFile();
			}
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(encFile);
			while ((dataOfFile = fis.read()) > -1) {
				fos.write(dataOfFile ^ numOfEncAndDec);
			}
			ImageUtil.renameImageName(temp, name);
		} catch (Exception e) {
			logger.error("加密文件失败");
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (fos != null){
					fos.flush();
					fos.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
	}
	
	/**
	 * @Description: 解密文件
	 * <p>创建人：lichao,  2019年6月26日  上午10:59:46</p>
	 * <p>修改人：lichao,  2019年6月26日  上午10:59:46</p>
	 * @param imgPath
	 */
	public static String decFile(String imgPath){
		String path = imgPath.substring(0, imgPath.lastIndexOf(File.separator)+1);// 目录
		String name = imgPath.substring(imgPath.lastIndexOf(File.separator)+1, imgPath.lastIndexOf("."));// 名字
		String fix = imgPath.substring(imgPath.lastIndexOf("."));// 后缀
		String temp = path + name + "_tmp" + fix;// 临时文件
		File encFile = new File(imgPath);
		File decFile = new File(temp);
		// 解密图片
		decFile(encFile, decFile);
		return temp;
	}
	
	/**
	 * @Description: 删除文件
	 * <p>创建人：lichao,  2019年6月26日  上午11:00:09</p>
	 * <p>修改人：lichao,  2019年6月26日  上午11:00:09</p>
	 * @param path
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		if (file != null && file.exists()) {
			file.delete();
			logger.info("删除文件：" + path);
		}
	}
	
	/**
	 * @Description: 获取加密图片的base64串
	 * <p>创建人：lichao,  2019年6月25日  下午3:15:14</p>
	 * <p>修改人：lichao,  2019年6月25日  下午3:15:14</p>
	 * @param imgPath
	 * @return
	 */
	public static String getImageStr(String imgPath){
		logger.info("获取图片[{}]的base64串", imgPath);
		String path = imgPath.substring(0, imgPath.lastIndexOf(File.separator)+1);
		String temp = path + "tmp.jpg";
		File encFile = new File(imgPath);
		File decFile = new File(temp);
		// 解密图片
		if(decFile(encFile, decFile)){
			// 图片转base64
			return toBase64(temp);
		}else{
			return null;
		}
//		decFile.delete();
	}
	
	private static boolean decFile(File encFile, File decFile) {
		if (!encFile.exists()) {
			logger.info("encrypt file not exixt");
			return false;
		}
		InputStream fis = null;
		OutputStream fos = null;
		try {
			if (!decFile.exists()) {
				logger.info("decrypt file created");
				decFile.createNewFile();
			}
			fis = new FileInputStream(encFile);
			fos = new FileOutputStream(decFile);
			while ((dataOfFile = fis.read()) > -1) {
				fos.write(dataOfFile ^ numOfEncAndDec);
			}
			return true;
		} catch (Exception e) {
			logger.error("解密密文件失败");
			return false;
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (fos != null){
					fos.flush();
					fos.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	private static String toBase64(String imgPath) {
		String imgFile = imgPath;// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		String encode = null; // 返回Base64编码过的字节数组字符串
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			encode = encoder.encode(data);
		} catch (Exception e) {
			logger.info("获取图片[{}]的base64串错误");
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return encode;
	}
	
	public static void main(String[] args) {
//		String temp = "C:\\Users\\admin\\Desktop\\000000000000000218.jpg";
//		encFile(temp);
		
		String temp = "C:\\Users\\admin\\Desktop\\000000000000000258.jpg";
		System.out.println(getImageStr(temp));
	}
	
}
