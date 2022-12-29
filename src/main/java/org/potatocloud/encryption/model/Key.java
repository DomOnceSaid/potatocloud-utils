package org.potatocloud.encryption.model;

public class Key {

	private String publicKey;

	private String privateKey;

	public Key() {
	}

	public Key(String privateKey, String publicKey) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public String publicKey() {
		return this.publicKey;
	}

	public String privateKey() {
		return this.privateKey;
	}

	@Override
	public String toString() {
		return "RSAKey{\n" + "publicKey='" + publicKey + '\'' + ",\nprivateKey='" + privateKey + '\'' + '}';
	}

}