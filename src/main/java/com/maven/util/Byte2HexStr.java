package com.maven.util;


/**
 * @author admin
 * 二进制和16进制的相互转换
 */
public class Byte2HexStr {
	/**
     * 将二进制转换成16进值制 ，防止byte[]数字转换成string类型时造成的数据损失
     * @param buf 
     * @return 返回16进制转换成的string
     */ 
	public static String parseByte2HexStr(byte[] buf){
		StringBuffer sb = new StringBuffer();
		
		int length = buf.length;
		for(int i=0;i<length;i++){
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if(hex.length() == 1)
				hex = '0' + hex;
			
			sb.append(hex.toUpperCase());
		}
		
		return sb.toString();
	}
	/**
     * 将16进制转换为二进制
     * @param hexStr 16进制的数组转换成String类型再传过来的参数
     * @return 转换回来的二进制数组
     */ 
	public static byte[] parseHexStr2Byte(String hexStr){
		if(hexStr.length() < 1)
			return null;
		
		int length = hexStr.length()/2;
		byte[] result = new byte[length];
		for(int i=0;i<length;i++){
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1),16);
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2),16);
			
			result[i] = (byte) (high*16+low);
		}
		
		return result;		
	}
	
}
