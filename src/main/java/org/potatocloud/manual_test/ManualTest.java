package org.potatocloud.manual_test;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.potatocloud.console.Print;
import org.potatocloud.encryption.encryptor.asymmetric.RSAFileEncryptor;
import org.potatocloud.encryption.encryptor.symmetric.AESFileEncryptor;
import org.potatocloud.encryption.keygen.RSAKeygen;
import org.potatocloud.encryption.model.Key;
import org.potatocloud.storage.MinioBucket;
import org.potatocloud.authentication.TOTPHelper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings({ "SpellCheckingInspection", "unused" })
public class ManualTest {

	private static void testUpload() {
		InputStream file;
		try {
			file = new FileInputStream("src/main/resources/targetFile.png");
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		}
		String MINIO_SECRET = System.getenv("MINIO_SECRET");
		String MINIO_ENDPOINT = System.getenv("MINIO_ENDPOINT");

		MinioBucket minioBucket = new MinioBucket().endpoint("http://192.168.75.128:9000").accessKey("admin")
				.secretKey(MINIO_SECRET).bucket("testbucket1");

		minioBucket.upload(file, "test/", "test1.png", "image/png");
	}

	private static void TestGenerateQR() {
		byte[] buffer = TOTPHelper.generateQRasByte("dom", "dom@mai.com", "hasdbasihbdiasbdiasbd", 6, 30);
		File targetFile = new File("src/main/resources/targetFile.tmp");
		try {
			Files.write(Path.of("src/main/resources/targetFile.png"), buffer);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void TestGeneratorRSAKey() {
		Key keys = RSAKeygen.rsaKeygen();
		System.out.println(keys);
	}

	public static void TestEncryptionDecryption() {
		TestGenerateQR();
		String key = "YmMFsmgAAczRmWRqhF8a18SWAtuAAXWAZoJkdthlYlU=";// AESKeygen.generateKey();
		Print.ln(key);
		String enc = AESFileEncryptor.encryptFile(key, new File("src/main/resources/targetFile.png"));
		String dec = AESFileEncryptor.decryptFile(key, new File(enc), "png");
	}

	public static void TestAsymetricFileEncryption() {
		try {
			TestGenerateQR();
			Key keys = RSAKeygen.rsaKeygen();
			byte[] encoded = Base64
					.encodeBase64(FileUtils.readFileToByteArray(new File("src/main/resources/targetFile.png")));
			String base64 = new String(encoded, StandardCharsets.US_ASCII);

			String[] encrypted = RSAFileEncryptor.encrypt(keys.publicKey(),
					new File("src/main/resources/targetFile.png"));
			String decrypted = RSAFileEncryptor.decrypt(keys.privateKey(), encrypted);

			if (base64.equals(decrypted)) {
				Print.ln("passed");
			}
			else {
				Print.ln("failed");
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
