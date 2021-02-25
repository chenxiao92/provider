package com.busi.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.*;


/** 
 * @Description：RSA 加解密工具类 
 * <p>创建日期：2018年5月14日 </p>
 * @version V1.0  
 * @author junjie.cao
 * @see
 */
public class RSAUtil {  
	private static Log log = LogFactory.getLog(RSAUtil.class); 
    /** 
     * 定义加密方式 
     */  
    private final static String KEY_RSA = "RSA";  
    /** 
     * 定义填充方式 
     */
    private final static String PKCS1PADDING = "RSA/ECB/PKCS1Padding";  
    
    /** 
     * 定义签名算法 
     */  
    private final static String KEY_RSA_SIGNATURE = "MD5withRSA";  
  
    
    
    /**
     * @Description：装载私钥 
     * <p>创建人：junjie.cao ,  2018年5月14日  下午4:38:25</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午4:38:25</p>
     *
     * @param modulus 模数
     * @param exponent 向量
     * @param radix 10进制 或者16进制
     * @return
     * @throws Exception
     * RSAPrivateKey 
     */
    public static PrivateKey loadPrivateKey(String modulus, String exponent, int radix)  throws Exception{
		BigInteger mod = new BigInteger(modulus, radix);
        BigInteger exp = new BigInteger(exponent, radix);
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(mod, exp);
        KeyFactory keyFac = KeyFactory.getInstance(KEY_RSA);
        return keyFac.generatePrivate(priKeySpec);
	}
    
    /**
     * @Description：从文件获取公钥
     * <p>创建人：junjie.cao ,  2018年5月14日  下午4:20:29</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午4:20:29</p>
     *
     * @param file 文件
     * @return
     * PrivateKey 
     */
    public static PrivateKey loadPrivateKey(File file) { 
    	
    	if(file == null || !file.exists()){
    		log.error("PrivateKey file not exist");
    		return null;
    	}
        PrivateKey privateKey = null;  
        InputStream in = null;  
        try {  
            byte[] key = new byte[2048];  
            in = new FileInputStream(file);  
            in.read(key);  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {  
            log.error("Private key certificate file format error",ex);  
        } catch (IOException ex) {  
            log.error(ex.getMessage(),ex);  
        } finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					log.error("IO close exception",e);
					in = null;
				}
			}
		} 
        return privateKey;  
    }  

    /**
     * @Description：用字符串装载私钥 
     * <p>创建人：junjie.cao ,  2018年5月14日  下午5:01:48</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午5:01:48</p>
     *
     * @param privateKey
     * @return
     * PrivateKey 
     */
    public static PrivateKey loadPrivateKey(String privateKey) { 
    	if(privateKey == null || "".equals(privateKey.trim())){
    		log.error("PrivateKey string illegality");
    		return null;
    	}
    	PrivateKey key = null;
		try {  
			// 解密由base64编码的私钥  
    		byte[] bytes = Base64Util.decode(privateKey);  
    		// 构造PKCS8EncodedKeySpec对象  
    		PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);  
    		// 指定的加密算法  
    		KeyFactory factory = KeyFactory.getInstance(KEY_RSA);  
    		// 取私钥对象  
    		key = factory.generatePrivate(pkcs);   
		} catch (Exception e) {  
		    log.error("Load PrivateKey exception",e);
		}
		return key;
    }
     
    
    
    
    /**
     * @Description：装载公钥
     * <p>创建人：junjie.cao ,  2018年5月14日  下午4:35:24</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午4:35:24</p>
     *
     * @param modulus 模数
     * @param exponent 向量
     * @param radix 10进制 或者16进制
     * @return
     * @throws Exception
     * PublicKey 
     */
    public static PublicKey loadPublicKey(String modulus, String exponent, int radix)  throws Exception{
		BigInteger mod = new BigInteger(modulus, radix);
        BigInteger exp = new BigInteger(exponent, radix);
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(mod, exp);
        KeyFactory keyFac = KeyFactory.getInstance(KEY_RSA);
        return keyFac.generatePublic(pubKeySpec);
	}
    
    /**
     * @Description：从文件获取私钥
     * <p>创建人：junjie.cao ,  2018年5月14日  下午4:20:29</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午4:20:29</p>
     *
     * @param file 文件
     * @return
     * PrivateKey 
     */
    public static PublicKey loadPublicKey(File file) { 
    	if(file == null || !file.exists()){
    		log.error("PublicKey file not exist");
    		return null;
    	}
        try {  
            CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");  
            FileInputStream bais = new FileInputStream(file);  
            X509Certificate Cert = (X509Certificate) certificatefactory.generateCertificate(bais);  
            return Cert.getPublicKey();  
        } catch (CertificateException | FileNotFoundException ex) {  
            log.error("LoadPublicKey failure", ex);  
        }  
        return null;  
    }
    
    /**
     * @Description：从字符串获取私钥对象
     * <p>创建人：junjie.cao ,  2018年5月14日  下午4:20:29</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午4:20:29</p>
     *
     * @param publicKey 公钥字符串
     * @return
     * PublicKey 
     */
    public static PublicKey loadPublicKey(String publicKey) {
    	if(publicKey == null || "".equals(publicKey.trim())){
    		log.error("PrivateKey string illegality");
    		return null;
    	}
    	PublicKey key = null;
    	 try {  
             // 解密由base64编码的公钥  
			byte[] bytes = Base64Util.decode(publicKey);  
			// 构造X509EncodedKeySpec对象  
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);  
			// 指定的加密算法  
			KeyFactory factory = KeyFactory.getInstance(KEY_RSA);  
			// 取公钥对象  
			key = factory.generatePublic(keySpec);  
         } catch (Exception e) {  
             log.error("load PublicKey exception", e);
         }
    	 return key;
    }
    
    
    /**
     * @Description：用私钥对信息生成数字签名
     * <p>创建人：junjie.cao ,  2018年5月14日  下午4:07:30</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午4:07:30</p>
     *
     * @param data 加密数据 
     * @param privateKey 私钥 
     * @return
     * String 
     */
    public static String sign(byte[] data, PrivateKey privateKey) {  
        String str = null;  
        try {  
            // 用私钥对信息生成数字签名  
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);  
            signature.initSign(privateKey);  
            signature.update(data);  
            str = HexUtils.encodeHexStr(signature.sign());  
        } catch (Exception e) {  
        	 log.error("sign Exception",e);  
        }  
        return str;  
    }
    
    /**
     * @Description：用字符串私钥对信息生成数字签名
     * <p>创建人：junjie.cao ,  2018年5月14日  下午4:07:30</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午4:07:30</p>
     *
     * @param data 加密数据 
     * @param privateKey 私钥 
     * @return
     * String 
     */
    public static String sign(byte[] data, String privateKey) {  
    	String str = null;  
    	try {  
    		//装载私钥
    		PrivateKey key = loadPrivateKey(privateKey);
    		//签名
    		str = sign(data,key);
    	} catch (Exception e) {  
    		log.error("sign Exception",e);   
    	}  
    	return str;  
    }  

    /**
     * @Description：用字符串文件私钥对信息生成数字签名
     * <p>创建人：junjie.cao ,  2018年5月14日  下午5:07:36</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午5:07:36</p>
     *
     * @param data
     * @param file
     * @return
     * String 
     */
    public static String sign(byte[] data, File file) {  
    	String str = null;  
    	try {  
    		//装载私钥
    		PrivateKey key = loadPrivateKey(file);
    		//签名
    		str = sign(data,key);
    	} catch (Exception e) {  
    		log.error("sign Exception",e);  
    	}  
    	return str;  
    }
    
    /**
     * @Description：用模数和向量对信息生成数字签名
     * <p>创建人：junjie.cao ,  2018年5月14日  下午5:18:21</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午5:18:21</p>
     *
     * @param data
     * @param modulus
     * @param exponent
     * @param radix
     * @return
     * String 
     */
    public static String sign(byte[] data,String modulus, String exponent, int radix) {  
    	String str = null;  
    	try {  
    		//装载私钥
    		PrivateKey key = loadPrivateKey(modulus,exponent,radix);
    		//签名
    		str = sign(data,key);
    	} catch (Exception e) {  
    		log.error("sign Exception",e);  
    	}  
    	return str;  
    }  
  
    

    /**
     * @Description：用公钥验证签名
     * <p>创建人：junjie.cao ,  2018年5月14日  下午5:23:03</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午5:23:03</p>
     *
     * @param data 数据明文
     * @param key  公钥
     * @param sign 签名密文
     * @return
     * boolean 
     */
    public static boolean verify(byte[] data, PublicKey key, String sign) {  
    	boolean flag = false;  
    	try {  
    		// 用公钥验证数字签名  
    		Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);  
    		signature.initVerify(key);  
    		signature.update(data);  
    		flag = signature.verify(HexUtils.decodeHex(sign));  
    	} catch (Exception e) {  
    		log.error("verify Exception",e);    
    	}  
    	return flag;  
    }  
    
    /**
     * @Description：校验数字签名 
     * <p>创建人：junjie.cao ,  2018年5月14日  下午4:07:57</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午4:07:57</p>
     *
     * @param data 数据 
     * @param publicKey 公钥 
     * @param sign 数字签名 
     * @return
     * boolean 校验成功返回true，失败返回false 
     */
    public static boolean verify(byte[] data, String publicKey, String sign) {  
        boolean flag = false;  
        try {  
            // 取公钥对象  
            PublicKey key = loadPublicKey(publicKey);  
            // 用公钥验证数字签名  
            return verify(data, key, sign);
        } catch (Exception e) {  
        	log.error("verify Exception",e);  
        }  
        return flag;  
    }  

    /**
     * @Description：用文件公钥校验数字签名 
     * <p>创建人：junjie.cao ,  2018年5月14日  下午5:25:27</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午5:25:27</p>
     *
     * @param data 数据明文
     * @param file 秘钥文件
     * @param sign 签名
     * @return
     * boolean 
     */
    public static boolean verify(byte[] data, File file, String sign) {  
    	boolean flag = false;  
    	try { 
    		PublicKey publicKey = loadPublicKey(file);
    		// 用公钥验证数字签名  
    		return verify(data, publicKey,sign);
    	} catch (Exception e) {  
    		log.error("verify Exception",e);  
    	}  
    	return flag;  
    }   

    /**
     * @Description：用模数和向量公钥校验数字签名 
     * <p>创建人：junjie.cao ,  2018年5月14日  下午5:28:09</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午5:28:09</p>
     *
     * @param data
     * @param modulus
     * @param exponent
     * @param radix
     * @param sign
     * @return
     * boolean 
     */
    public static boolean verify(byte[] data,String modulus, String exponent, int radix, String sign) {  
    	boolean flag = false;  
    	try { 
    		PublicKey publicKey = loadPublicKey(modulus,exponent,radix);
    		// 用公钥验证数字签名  
    		return verify(data, publicKey,sign);
    	} catch (Exception e) {  
    		log.error("verify Exception",e); 
    	}  
    	return flag;  
    }   
    

    /**
     * @Description：用秘钥解密数据
     * <p>创建人：junjie.cao ,  2018年5月16日  上午11:40:19</p>
     * <p>修改人：junjie.cao ,  2018年5月16日  上午11:40:19</p>
     *
     * @param data 明文
     * @param key 秘钥
     * @return
     * @throws Exception
     * byte[] 
     */
    public static byte[] decryptByKey(byte[] data,Key key) throws Exception{  
    	// 对数据加密  
    	Cipher cipher = Cipher.getInstance(PKCS1PADDING);  
    	cipher.init(Cipher.DECRYPT_MODE, key);  
    	return  cipher.doFinal(data);  
    }
    
    /**
     * @Description：用私钥解密
     * <p>创建人：junjie.cao ,  2018年5月16日  上午11:41:26</p>
     * <p>修改人：junjie.cao ,  2018年5月16日  上午11:41:26</p>
     *
     * @param data  明文数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     * byte[] 
     */
    public static byte[] decryptByPrivateKey(byte[] data,PrivateKey privateKey) throws Exception{  
    	return decryptByKey(data,privateKey);  
    }
    
    /**
     * @Description：私钥解密
     * <p>创建人：junjie.cao ,  2018年5月14日  下午4:09:02</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午4:09:02</p>
     *
     * @param data 加密数据
     * @param key 私钥 
     * @return
     * @throws Exception
     * byte[] 
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception{ 
       
    	// 取得私钥  
        PrivateKey privateKey = loadPrivateKey(key);  
        // 对数据解密  
        return decryptByPrivateKey(data,privateKey);   
    }  
  
    /**
     * @Description：公钥解密 
     * <p>创建人：junjie.cao ,  2018年5月16日  上午11:44:52</p>
     * <p>修改人：junjie.cao ,  2018年5月16日  上午11:44:52</p>
     * 
     * @param data 加密数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     * byte[] 
     */
    public static byte[] decryptByPublicKey(byte[] data,PublicKey publicKey) throws Exception{  
    	return decryptByKey(data,publicKey);  
    }

    /**
     * @Description：公钥解密 
     * <p>创建人：junjie.cao ,  2018年5月14日  下午4:09:22</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午4:09:22</p>
     *
     * @param data 加密数据 
     * @param key 公钥
     * @return
     * @throws Exception
     * byte[] 
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception{   

        // 取得公钥  
        PublicKey publicKey = loadPublicKey(key);  
        // 对数据解密  
        return decryptByPublicKey(data,publicKey);   
    }  
  
    /**
     * @Description：公钥加密 
     * <p>创建人：junjie.cao ,  2018年5月16日  上午9:55:06</p>
     * <p>修改人：junjie.cao ,  2018年5月16日  上午9:55:06</p>
     *
     * @param data 待加密明文
     * @param publicKey 公钥
     * @return
     * @throws Exception
     * byte[] 
     */
    public static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception{  
        return encryptByKey(data,publicKey); 
    }  

    /**
     * @Description：公钥加密
     * <p>创建人：junjie.cao ,  2018年5月14日  下午4:09:50</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午4:09:50</p>
     *
     * @param data 公钥加密
     * @param key 公钥
     * @return
     * @throws Exception
     * byte[] 
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception{  
        // 取得公钥  
        PublicKey publicKey = loadPublicKey(key);    
        return encryptByPublicKey(data,publicKey); 
    }  
  
    /**
     * @Description：私钥加密  
     * <p>创建人：junjie.cao ,  2018年5月14日  下午4:10:13</p>
     * <p>修改人：junjie.cao ,  2018年5月14日  下午4:10:13</p>
     *
     * @param data 数据明文
     * @param key  私钥
     * @return
     * @throws Exception
     * byte[] 
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception{  
        // 取得私钥  
        PrivateKey privateKey = loadPrivateKey(key);  
        // 对数据加密  
        return  encryptByPrivateKey(data,privateKey);  
    }  

    /**
     * @Description：私钥加密   
     * <p>创建人：junjie.cao ,  2018年5月16日  上午10:03:24</p>
     * <p>修改人：junjie.cao ,  2018年5月16日  上午10:03:24</p>
     *
     * @param data 明文
     * @param privateKey 私钥
     * @return
     * @throws Exception
     * byte[] 
     */
    public static byte[] encryptByPrivateKey(byte[] data,PrivateKey privateKey) throws Exception{  
    	return encryptByKey(data,privateKey);  
    }  

    
    /**
     * @Description：用秘钥加密数据 
     * <p>创建人：junjie.cao ,  2018年5月16日  上午10:01:04</p>
     * <p>修改人：junjie.cao ,  2018年5月16日  上午10:01:04</p>
     *
     * @param data 数据明文
     * @param key 秘钥 公钥或私钥
     * @return
     * @throws Exception
     * byte[] 
     */
    public static byte[] encryptByKey(byte[] data,Key key) throws Exception{  
    	// 对数据加密  
    	Cipher cipher = Cipher.getInstance(PKCS1PADDING);  
    	cipher.init(Cipher.ENCRYPT_MODE, key);  
    	return  cipher.doFinal(data);  
    }  
}