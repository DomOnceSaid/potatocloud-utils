package org.potatocloud.encryption.keygen;

import org.springframework.util.Base64Utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class AESKeygen {

	private static final Integer KEY_SIZE = 256;

	public static String generateKey(Integer n) {
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		keyGenerator.init(n);
		SecretKey key = keyGenerator.generateKey();
		return Base64Utils.encodeToString(key.getEncoded());
	}

	public static String generateKey() {
		return generateKey(KEY_SIZE);
	}

}
