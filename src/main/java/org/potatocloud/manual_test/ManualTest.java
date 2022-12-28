package org.potatocloud.manual_test;

import org.potatocloud.encryption.RSAKeygen;
import org.potatocloud.encryption.model.RSAKey;
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
}
