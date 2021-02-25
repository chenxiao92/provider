package com.busi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @Description: 图像工具
 * <p>创建日期：2019年5月27日-上午10:31:36</p>
 * @version V1.0
 * @author lichao
 * @see
 */
public class ImageUtil {

	protected static Logger logger = LoggerFactory.getLogger("ImageUtil");
	
	/**
	 * @Description: 重命名图片名称
	 * <p>创建人：lichao,  2019年5月24日  下午6:17:14</p>
	 * <p>修改人：lichao,  2019年5月24日  下午6:17:14</p>
	 * @param imagePath	原图片路径
	 * @param newName	新名称
	 */
	public static String renameImageName(String imagePath, String newName) {
		logger.info("重命名图片imagePath="+imagePath+",newName="+newName);
		File oldImage = new File(imagePath);
		if(!oldImage.exists()){
			logger.info("图片不存在imagePath="+imagePath);
			return null;
		}
		String fix = imagePath.substring(imagePath.lastIndexOf("."));
		String rootPath = oldImage.getParent();
		File newFile = new File(rootPath + File.separator + newName + fix);
		oldImage.renameTo(newFile);
		return newFile.getAbsolutePath();
	}
	
}
