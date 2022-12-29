package org.potatocloud.encryption.encryptor.symmetric;

import org.potatocloud.console.Print;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import javax.crypto.spec.SecretKeySpec;
import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

@SuppressWarnings({ "SpellCheckingInspection", "unused" })
public class AESFileEncryptor {

	// public static File encryptFile(String aesKey, File inputFile) {
	// try {
	// String timeStamp = new
	// SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	// byte[] decodedKey = Base64.getDecoder().decode(aesKey);
	// SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	// Cipher cipher = Cipher.getInstance("AES");
	// cipher.init(Cipher.ENCRYPT_MODE, originalKey );
	// FileInputStream inputStream = new FileInputStream(inputFile);
	// FileOutputStream outputStream = new FileOutputStream("src/main/resources/"+
	// timeStamp + ".tmp");
	// byte[] buffer = new byte[64];
	// int bytesRead;
	// while ((bytesRead = inputStream.read(buffer)) != -1) {
	// byte[] output = cipher.update(buffer, 0, bytesRead);
	// if (output != null) {
	// outputStream.write(output);
	// }
	// }
	// byte[] outputBytes = cipher.doFinal();
	// if (outputBytes != null) {
	// outputStream.write(outputBytes);
	// }
	// inputStream.close();
	// File output = new File("src/main/resources/"+ timeStamp + ".encrypted.tmp");
	// outputStream.close();
	// return output;
	// } catch (Exception e) {
	// Print.ln("Encryption error : " + e.getMessage());
	// }
	// return null;
	// }
	//
	// public static File decryptFile(String aesKey, File inputFile) {
	// try {
	// String timeStamp = new
	// SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	// byte[] decodedKey = Base64.getDecoder().decode(aesKey);
	// SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	// Cipher cipher = Cipher.getInstance("AES");
	// cipher.init(Cipher.DECRYPT_MODE, originalKey );
	// FileInputStream inputStream = new FileInputStream(inputFile);
	// FileOutputStream outputStream = new FileOutputStream("src/main/resources/"+
	// timeStamp + ".tmp");
	// byte[] buffer = new byte[64];
	// int bytesRead;
	// while ((bytesRead = inputStream.read(buffer)) != -1) {
	// byte[] output = cipher.update(buffer, 0, bytesRead);
	// if (output != null) {
	// outputStream.write(output);
	// }
	// }
	// byte[] outputBytes = cipher.doFinal();
	// if (outputBytes != null) {
	// outputStream.write(outputBytes);
	// }
	// inputStream.close();
	// File output = new File("src/main/resources/"+ timeStamp + "decrypted.tmp");
	// outputStream.close();
	// return output;
	// } catch (Exception e) {
	// Print.ln("Decryption error : " + e.getMessage());
	// }
	// return null;
	// }

	public static String encryptFile(String aesKey, File inputFile) {
		try {
			createTempDirectory();
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			byte[] decodedKey = Base64.getDecoder().decode(aesKey);
			SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, originalKey);
			FileInputStream inputStream = new FileInputStream(inputFile);
			FileOutputStream outputStream = new FileOutputStream(
					new File("src/main/resources/temp/" + timeStamp + ".encrypted"));
			byte[] buffer = new byte[64];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				byte[] output = cipher.update(buffer, 0, bytesRead);
				if (output != null) {
					outputStream.write(output);
				}
			}
			byte[] outputBytes = cipher.doFinal();
			if (outputBytes != null) {
				outputStream.write(outputBytes);
			}
			inputStream.close();
			outputStream.close();
			return "src/main/resources/temp/" + timeStamp + ".encrypted";
		}
		catch (Exception e) {
			Print.ln("Encryption error : " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String decryptFile(String aesKey, File inputFile, String fileType) {
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			byte[] decodedKey = Base64.getDecoder().decode(aesKey);
			SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, originalKey);
			FileInputStream inputStream = new FileInputStream(inputFile);
			FileOutputStream outputStream = new FileOutputStream(
					new File("src/main/resources/temp/" + timeStamp + ".decrypted." + fileType));
			byte[] buffer = new byte[64];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				byte[] output = cipher.update(buffer, 0, bytesRead);
				if (output != null) {
					outputStream.write(output);
				}
			}
			byte[] outputBytes = cipher.doFinal();
			if (outputBytes != null) {
				outputStream.write(outputBytes);
			}
			inputStream.close();
			outputStream.close();
			return "src/main/resources/temp/" + timeStamp + ".decrypted." + fileType;
		}
		catch (Exception e) {
			Print.ln("Decryption error : " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	private static void createTempDirectory() {
		try {
			Files.createDirectories(Paths.get("src/main/resources/temp"));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
