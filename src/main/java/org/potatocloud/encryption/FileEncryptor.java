package org.potatocloud.encryption;

import org.potatocloud.file.FileUtil;

import java.io.InputStream;

public class FileEncryptor {

    public static InputStream encryptFile(InputStream stream, String publicKey) {
        String base64 = FileUtil.fileToBase64(stream);
        String encrypted = StringEncryptor.encrypt(base64, publicKey);
        return FileUtil.byteArrayToFile(FileUtil.stringToByteArray(encrypted));
    }

    public static InputStream decryptFile(InputStream stream, String privateKey) {
        String base64 = FileUtil.fileToBase64(stream);
        String encrypted = StringEncryptor.decrypt(base64, privateKey);
        return FileUtil.byteArrayToFile(FileUtil.stringToByteArray(encrypted));
    }

}
