package com.wankun.hash;

import java.io.UnsupportedEncodingException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * 一致性Hash 方法测试
 * 
 * @author wankun
 */
public class TestMd5Hashing {

	Md5Hashing hash = new Md5Hashing();

	private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	@Test
	public void testComputeMd5() throws UnsupportedEncodingException {
		byte[] md5bytes = hash.computeMd5("md5");
		System.out.println(md5bytes.length);
		// 把密文转换成十六进制的字符串形式
		char str[] = new char[md5bytes.length * 2];
		int k = 0;
		for (int i = 0; i < md5bytes.length; i++) {
			byte byte0 = md5bytes[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		System.out.println("16进制加密2：" + String.valueOf(str));
		String res = String.valueOf(str);
		// String res = byteToString(md5bytes);
		Assert.assertEquals(res, "1BC29B36F623BA82AAF6724FD3B16718");
	}

	@Test
	public void testHash() {
		System.out.println(hash.hash(hash.computeMd5("md5"), 3));
	}

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			int iRet = bByte[i];
			if (iRet < 0) {
				iRet += 256;
			}
			int iD1 = iRet / 16;
			int iD2 = iRet % 16;
			sBuffer.append(hexDigits[iD1]);
			sBuffer.append(hexDigits[iD2]);
		}
		return sBuffer.toString();
	}
}
