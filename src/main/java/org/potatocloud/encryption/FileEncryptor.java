package org.potatocloud.encryption;

import org.potatocloud.console.Print;
import org.potatocloud.file.FileUtil;

import java.io.InputStream;

public class FileEncryptor {

    public static InputStream encryptFile(InputStream stream, String publicKey) {
        try {
            return null;
        } catch (Exception e) {
            Print.ln("Encryption error : " + e.getMessage());
        } return null;
    }

    public static InputStream decryptFile(InputStream stream, String privateKey) {
        try {
            return null;
        } catch (Exception e) {
            Print.ln("Decryption error : " + e.getMessage());
        } return null;
    }

}
