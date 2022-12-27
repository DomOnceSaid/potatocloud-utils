package org.potatocloud.encryption;

public class RSAKey {
    private String publicKey;
    private String privateKey;

    public RSAKey() {}

    public RSAKey(String privateKey, String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }
    public String publicKey() { return this.publicKey; }
    public String privateKey() { return  this.privateKey; }

    @Override
    public String toString() {
        return "RSAKey{\n" +
                "publicKey='" + publicKey + '\'' +
                ",\nprivateKey='" + privateKey + '\'' +
                '}';
    }
}