package com.maven.jiami;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.maven.oracle.Test1;
import com.maven.util.Byte2HexStr;

/**
 * @author admin
 *
 */
public class AESDemo1 {
	private KeyGenerator keyGenerator;//密钥生成器
	private SecretKey secretKey;//密钥,真正使用中可以直接指定或者存入文件中
	private Cipher cipher;//加密算法
	
	@SuppressWarnings("restriction")
	public AESDemo1(){
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			secretKey = keyGenerator.generateKey();
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * @author admin
	 * 加密
	 * 
	 */
	public String jiaMi(String context) throws InvalidKeyException, 
			IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		
		byte[] contextByte = context.getBytes("UTF8");///////////是否加转换编码"UTF8"
		byte[] resultByte = cipher.doFinal(contextByte);//加密
		return Byte2HexStr.parseByte2HexStr(resultByte);//转换成16进制字符串
	}
	/**
	 * @author admin
	 * 解密
	 */
	public String jieMi(String context) throws InvalidKeyException, 
			IllegalBlockSizeException, BadPaddingException{
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		
		byte[] contextByte = Byte2HexStr.parseHexStr2Byte(context);//把16进制字符串转换成2进制byte数组
		
		byte[] resultByte = cipher.doFinal(contextByte);//解密
		
		return new String(resultByte);
	}
	
	/**
	 * @param args
	 * 初步测试加密解密是否可用
	 */
	/*public static void main(String[] args){
		AESDemo1 aesDemo1 = new AESDemo1();
		
		String context = "俄wed大三的地方23980w阿嫂";
		System.out.println("原字符串：" + context);
		try {
			String jiaMiString = aesDemo1.jiaMi(context);
			System.out.println("加密后字符串：" + jiaMiString);
		
			String jieMiString = aesDemo1.jieMi(jiaMiString);
			System.out.println("解密后字符串：" + jieMiString);		
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}*/
	
	/**
	 * @param args
	 * 测试加密后存储到数据库
	 * 再从数据库取出，解密
	 * 
	 */
	public static void main(String[] args){
		AESDemo1 aesDemo1 = new AESDemo1();
		
		String context = "89fd844度假s村ndc";
		System.out.println("原字符串：" + context);
		try {
			String jiaMiString = aesDemo1.jiaMi(context);
			System.out.println("加密后字符串：" + jiaMiString);
		
			Test1.connect(jiaMiString);//把加密后内容放入数据库
			String quChu = Test1.connect();//把加密后内容从数据库取出
			
			String jieMiString = aesDemo1.jieMi(quChu);
			System.out.println("解密后字符串：" + jieMiString);		
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
