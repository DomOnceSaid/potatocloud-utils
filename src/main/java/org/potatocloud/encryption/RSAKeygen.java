package org.potatocloud.encryption;

import org.potatocloud.encryption.model.Key;

import java.security.*;
import java.util.Base64;

public class RSAKeygen {


    public static Key rsaKeygen() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            return new Key(encodeKey(privateKey), encodeKey(publicKey));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String encodeKey(java.security.Key key) {
        byte[] keyBytes = key.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

}
