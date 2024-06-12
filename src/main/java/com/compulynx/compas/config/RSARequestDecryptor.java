package com.compulynx.compas.config;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//import sun.misc.BASE64Decoder;

import org.apache.commons.codec.binary.Base64;

public class RSARequestDecryptor {
	private final String PUBLIC_KEY_FILE = "C:\\apache-tomcat-8.5.20\\conf\\iprint\\rsa_1024_pub.pem";

	private final String PLAIN_TEXT_ENCODING = "UTF-8";

	public String encryptMessage(String msg) throws NoSuchAlgorithmException, NoSuchPaddingException,
			UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		try {
			PublicKey key = getPemPublicKey();
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return org.apache.commons.codec.binary.Base64.encodeBase64String(cipher.doFinal(msg.getBytes("UTF-8")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public PublicKey getPemPublicKey() throws Exception {
		File f = new File(PUBLIC_KEY_FILE);
		System.out.println(PUBLIC_KEY_FILE);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();

		String temp = new String(keyBytes);
		String publicKeyPEM = temp.replace("-----BEGIN PUBLIC KEY-----\n", "");
		publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");

		Base64 b64 = new Base64();
		byte[] decoded = b64.decodeBase64(publicKeyPEM);

		X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}
}
