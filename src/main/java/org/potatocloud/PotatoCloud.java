package org.potatocloud;

import org.potatocloud.console.Print;
import org.potatocloud.encryption.FileEncryptor;
import org.potatocloud.encryption.RSAKeygen;
import org.potatocloud.encryption.model.RSAKey;
import org.potatocloud.file.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class PotatoCloud {
    public static void  main (String[] args) {
        RSAKey key = RSAKeygen.rsaKeygen();

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
