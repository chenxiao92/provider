package com.busi.util;

import java.util.Random;
import java.util.UUID;

public class RandomUtils {

	public static String getUuidStr(){
		String key = UUID.randomUUID().toString();
		key = key.replace("-", "");
		return key;
	}

	/**
	 * @Description：生成用户图像Id
	 * <p>创建人：junjie.cao,  2018/10/30  15:28</p>
	 * <p>修改人：junjie.cao,  2018/10/30  15:28</p>
	 *
	  * @Param: calling
	 *
	 */
	public static String createImageId(String calling){
		String randomStr = getCharAndNumber(16);
		return calling.substring(7) + randomStr;
	}

	/**
	 * @Description：生产token
	 * <p>创建人：junjie.cao,  2018/10/30  12:16</p>
	 * <p>修改人：junjie.cao,  2018/10/30  12:16</p>
	 *  
	 */
	public static String createToken(){
		return getCharAndNumber(20);
	}

	/**
	 * @Description：生成随机长度的字符串
	 * <p>创建人：junjie.cao,  2018/10/30  12:16</p>
	 * <p>修改人：junjie.cao,  2018/10/30  12:16</p>
	 *
	 * @Param: null
	 *
	 */
	public static String getCharAndNumber(int lenght){
		StringBuilder sb = new StringBuilder(20);
		Random random = new Random();
		for (int i = 0; i < lenght; i++) {
			boolean isChar = (random.nextInt(2)%2==0);
			if(isChar){
				int choice = (random.nextInt(2)%2==0)?65:97;
				sb.append((char)(choice + random.nextInt(26)));
			}else{
				sb.append(String.valueOf(random.nextInt(10)));
			}
		}
		return sb.toString();
	}
}
