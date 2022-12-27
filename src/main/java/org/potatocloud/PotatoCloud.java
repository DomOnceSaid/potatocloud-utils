package org.potatocloud;

import dev.potato.totp.exceptions.QrGenerationException;
import org.potatocloud.encryption.RSAKeygen;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.potatocloud.encryption.RSAKey;

public class PotatoCloud {

    public static void main(String[] args) throws QrGenerationException, IOException, NoSuchAlgorithmException {
        RSAKey key = RSAKeygen.rsaKeygen();
        System.out.print(key);
    }
}
