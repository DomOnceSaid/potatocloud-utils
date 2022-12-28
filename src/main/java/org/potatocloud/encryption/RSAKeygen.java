package org.potatocloud.encryption;

import org.potatocloud.encryption.model.RSAKey;

import java.security.*;
import java.util.Base64;

public class RSAKeygen {


    public static RSAKey rsaKeygen() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            return new RSAKey(encodeKey(privateKey), encodeKey(publicKey));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String encodeKey(Key key) {
        byte[] keyBytes = key.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

}
