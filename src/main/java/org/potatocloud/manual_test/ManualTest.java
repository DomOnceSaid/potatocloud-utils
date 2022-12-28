package org.potatocloud.manual_test;

import org.potatocloud.console.Print;
import org.potatocloud.encryption.FileEncryptor;
import org.potatocloud.encryption.RSAKeygen;
import org.potatocloud.encryption.model.RSAKey;
import org.potatocloud.file.FileUtil;
import org.potatocloud.storage.MinioBucket;
import org.potatocloud.authentication.TOTPHelper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class ManualTest {

    private static void testUpload() {
        InputStream file;
        try {
            file = new FileInputStream("src/main/resources/targetFile.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        String MINIO_SECRET = System.getenv("MINIO_SECRET");
        String MINIO_ENDPOINT = System.getenv("MINIO_ENDPOINT");

        MinioBucket minioBucket = new MinioBucket()
                .endpoint("http://192.168.75.128:9000")
                .accessKey("admin")
                .secretKey(MINIO_SECRET)
                .bucket("testbucket1");

        minioBucket.upload(file ,"test/", "test1.png", "image/png");
    }

    private static void TestGenerateQR() {
        byte[] buffer = TOTPHelper.generateQRasByte(
                "dom", "dom@mai.com", "hasdbasihbdiasbdiasbd", 6, 30
        );
        File targetFile = new File("src/main/resources/targetFile.tmp");
        try {
            Files.write(Path.of("src/main/resources/targetFile.png"), buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void TestGeneratorRSAKey() {
        RSAKey keys = RSAKeygen.rsaKeygen();
        System.out.println(keys);
    }

    private static void TestEncryptionDecryption() {
        {
            RSAKey key = RSAKeygen.rsaKeygen();
            TestGenerateQR();
            try {
                InputStream file = FileUtil.openFile("src/main/resources/targetFile.png");
                InputStream encrypted = FileEncryptor.encryptFile(file, key.publicKey());
                Files.write(Path.of("src/main/resources/targetFileEncrypted"), FileUtil.fileToByteArray(encrypted));
                InputStream fileEncrypted = FileUtil.openFile("src/main/resources/targetFileEncrypted");
                InputStream decrypted = FileEncryptor.decryptFile(fileEncrypted, key.privateKey());
                Files.write(Path.of("src/main/resources/targetFileDecrypted.png"), FileUtil.fileToByteArray(decrypted));
            } catch (IOException e) {
                Print.ln(e.getMessage());
            }
        }
    }
}
