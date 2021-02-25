package com.busi.util;

import java.io.UnsupportedEncodingException;

public class ClientMessageProcessUtils {

    private final static String ENCODE = "UTF-8";
	
	private static  String pubKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNxlcdn/+JV3oSQs3/HcKGeAxjmsxL3khI3ElXBMu6YYYdEOvDJ8Tgm61jUJOC4u1X8n3E2FABQJ+WIeK/j6vlOz0z8S/9upf/jDfLzfCsxYKlmQLDxjWY3LhtUZ+fAWOzPRPbJNauLy0GAqVfXFFenj55jeIbBZjvVo2VqRPKfQIDAQAB";
    
    
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
	public static String encrypt(String msg) throws UnsupportedEncodingException, Exception{
		//生成des随机秘钥
		String desKey = RandomUtils.getUuidStr();
		//用rsa公钥加密des秘钥
		String keyStr =HexUtils.encodeHexStr(RSAUtil.encryptByPublicKey(desKey.getBytes(ENCODE), pubKey));
		//用des秘钥加密报文
		String msgStr = DES.encrypt(msg, desKey);
		//返回密文
		return keyStr + msgStr;
	}
	
	
	public static String decrypt(String msg) throws UnsupportedEncodingException, Exception{
		//前256位为加密的des秘钥
		String keyStr = msg.substring(0, 256);
		//后面是加密的报文
		String msgStr = msg.substring(256);
		//解密des秘钥
		String desKey =new String(RSAUtil.decryptByPublicKey(HexUtils.decodeHex(keyStr),pubKey),ENCODE);
		//用des秘钥解密报文
		msg = DES.decrypt(msgStr, desKey);
		//返回解密后的报文
		return msg;
	}
	public static void main(String[] args) throws UnsupportedEncodingException, Exception {
		String msg= "{\"funCode\":\"YYCSH\",\"reqDate\":\"20171130\",\"reqTime\":\"121033\"," +
				"\"calling\":\"13652167878\",\"merId\":\"000001\",\"clientVer\":\"1\"}";
//		System.out.println(encrypt(msg));
//		System.out.println(ServerMessageProcessUtils.decrypt(encrypt(msg)));
		System.out.println(encrypt(msg));
//		System.out.println(ServerMessageProcessUtils.decrypt(encrypt(msg)));
	}
	
}
