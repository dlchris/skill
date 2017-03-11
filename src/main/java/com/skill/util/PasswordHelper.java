package com.skill.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

public class PasswordHelper {
	private static String algorithmName = "md5";
	private static int hashIterations = 1;

	public static String encryptPassword(String password, String salt) {
		String newPassword = new SimpleHash(algorithmName, password, salt, hashIterations).toHex();
		return newPassword;
	}

	public static String generateSalt() {
		SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
		String hex = secureRandom.nextBytes(4).toHex(); // һ��Byteռ�����ֽڣ��˴����ɵ�3�ֽڣ��ַ�������Ϊ6
		return hex;
	}

}