package com.busi.util;

import java.io.UnsupportedEncodingException;

public class ServerMessageProcessUtils {

    private final static String ENCODE = "UTF-8";
	
   	/**
   	 * @Description：将消息先用des加密，然后将des秘钥用rsa私钥加密，最终返回：rsa加密的des秘钥+des加密的报文
   	 * <p>创建人：junjie.cao ,  2017-11-24  上午07:14:34</p>
   	 * <p>修改人：junjie.cao ,  2017-11-24  上午07:14:34</p>
   	 *
   	 * @param msg
   	 * @return
   	 * @throws UnsupportedEncodingException
   	 * @throws Exception
   	 * String 
   	 */
   	public static String encrypt(String msg,String privateKey) throws UnsupportedEncodingException, Exception{
   		//生成des随机秘钥
   		String desKey = RandomUtils.getUuidStr();
   		//用rsa公钥加密des秘钥
   		String keyStr =HexUtils.encodeHexStr(RSAUtil.encryptByPrivateKey(desKey.getBytes(ENCODE),privateKey));
   		//用des秘钥加密报文
   		String msgStr = DES.encrypt(msg, desKey);
   		//返回密文
   		return keyStr + msgStr;
   	}
   	
   	
   	/**
   	 * @Description：用私匙解密 
   	 * <p>创建人：junjie.cao ,  2017-11-30  上午09:27:52</p>
   	 * <p>修改人：junjie.cao ,  2017-11-30  上午09:27:52</p>
   	 *
   	 * @param msg
   	 * @return
   	 * @throws UnsupportedEncodingException
   	 * @throws Exception
   	 * String 
   	 */
   	public static String decrypt(String msg,String privateKey) throws UnsupportedEncodingException, Exception{
   		//前256位为加密的des秘钥
   		String keyStr = msg.substring(0, 256);
   		//后面是加密的报文
   		String msgStr = msg.substring(256);
   		//解密des秘钥
   		String desKey =new String(RSAUtil.decryptByPrivateKey(HexUtils.decodeHex(keyStr),privateKey),ENCODE);
   		//用des秘钥解密报文
   		msg = DES.decrypt(msgStr, desKey);
   		//返回解密后的报文
   		return msg;
   	}
	
}
