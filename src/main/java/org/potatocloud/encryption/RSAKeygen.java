package org.potatocloud.encryption;

import java.security.*;
import java.util.Base64;

public class RSAKeygen {


    public static RSAKey rsaKeygen() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();
        return new RSAKey(encodeKey(privateKey), encodeKey(publicKey));
    }

    public static String encodeKey(Key key) {
        byte[] keyBytes = key.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

}
