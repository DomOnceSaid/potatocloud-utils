package org.potatocloud.encryption.encryptor.asymmetric;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.common.base.Splitter;

public class RSAFileEncryptor {

	private static final Integer CHUNK_SIZE = 200;

	public static String[] encrypt(String publicKey, File inputFile) {
		byte[] encoded = new byte[0];
		try {
			encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(inputFile));
			String base64 = new String(encoded, StandardCharsets.US_ASCII);
			String[] split = base64.split("(?<=\\G.{" + CHUNK_SIZE + "})");
			String[] encrypted = new String[split.length];
			int index = 0;
			for (String s : split) {
				encrypted[index] = RSAStringEncryptor.encrypt(split[index], publicKey);
				index++;
			}
			return encrypted;
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String decrypt(String privateKey, String[] inputFile) {
		int index = 0;
		StringBuilder decrypted = new StringBuilder();
		for (String s : inputFile) {
			decrypted.append(RSAStringEncryptor.decrypt(inputFile[index], privateKey));
			index++;
		}
		return decrypted.toString();
	}

}
