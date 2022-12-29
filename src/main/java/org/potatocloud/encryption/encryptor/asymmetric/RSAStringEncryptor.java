package org.potatocloud.encryption.encryptor.asymmetric;

import java.security.KeyFactory;
import java.security.PrivateKey;

import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSAStringEncryptor {

	public static String encrypt(String content, String publicKey) {
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
			RSAPublicKey key = (RSAPublicKey) kf.generatePublic(keySpecX509);
			byte[] contentBytes = content.getBytes();
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cipherContent = cipher.doFinal(contentBytes);
			return Base64.getEncoder().encodeToString(cipherContent);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String decrypt(String cipherContent, String privateKey) {
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
			PrivateKey key = kf.generatePrivate(keySpecPKCS8);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] cipherContentBytes = Base64.getDecoder().decode(cipherContent.getBytes());
			byte[] decryptedContent = cipher.doFinal(cipherContentBytes);
			return new String(decryptedContent);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
