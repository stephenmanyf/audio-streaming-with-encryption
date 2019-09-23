package com.example.demo.controller;

import java.io.IOException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.component.SimpleAudioPlayer;
import com.fasterxml.jackson.annotation.JacksonInject.Value;

import sun.audio.AudioPlayer;

@RestController
@RequestMapping("/")
public class Controller {

	@GetMapping("/download")
	public void download() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//		SimpleAudioPlayer player = new SimpleAudioPlayer();
		URL url = new URL("http://localhost:8080/music");
//		player.load(url);
//		player.play();
		
		AudioPlayer.player.start(url.openStream());
	}
	
	private Key generateKey() throws Exception 
    {
			String keyValue = "1234567890123456"; 
            Key key = new SecretKeySpec(keyValue.getBytes(), algorithm);
            return key;
    }
	
	
	private int keySize = 128;
	private final String transformation = "AES/CBC/PKCS5Padding";
	private final String algorithm = "AES";
	private Key key = null;
	private Cipher cipher = null;
	
//	@GetMapping("/download/decrypted")
//	public void downloadDecrypted() throws UnsupportedAudioFileException, IOException, LineUnavailableException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
////	    // 8-byte initialization vector
////	    byte[] iv = {
////	    	(byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,
////	    	(byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3,
////	    	(byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,
////	    	(byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3
////	    };
////	    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv); 
//	    
////		AudioPlayer player = new AudioPlayer();
//		URL url = new URL("http://localhost:8080/music/encrypted");
//		
//		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
//		keyGenerator.init(keySize);
//		key = keyGenerator.generateKey();
//		cipher = Cipher.getInstance(transformation);
//		cipher.init(Cipher.ENCRYPT_MODE, key);
////		cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
////		AlgorithmParameterSpec paramSpec = new IvParameterSpec(cipher.getIV());
//		
////System.out.println("cipher.getIV(): " + cipher.getIV().hashCode());
//		System.out.println("key: " + key.hashCode());
//		
//		Cipher cipherToDecrypt = Cipher.getInstance(transformation);
////		cipherToDecrypt.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(cipher.getIV()));
//		cipherToDecrypt.init(Cipher.DECRYPT_MODE, key);
////		cipherToDecrypt.init(Cipher.DECRYPT_MODE, key, paramSpec);
//		CipherInputStream cipherInputStream = new CipherInputStream(url.openStream(), cipherToDecrypt);
//		
//		AudioPlayer.player.start(cipherInputStream);
////		AudioPlayer.player.start(url.openStream());
////		cipherInputStream.close();
////		url.openStream().close();
//	}
	
	@GetMapping("/download/decrypted")
	public void downloadDecrypted() throws UnsupportedAudioFileException, IOException, LineUnavailableException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
//	    // 8-byte initialization vector
//	    byte[] iv = {
//	    	(byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,
//	    	(byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3,
//	    	(byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,
//	    	(byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3
//	    };
//	    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv); 
	    
//		AudioPlayer player = new AudioPlayer();
		URL url = new URL("http://localhost:8080/music/encrypted");
		
//		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
//		keyGenerator.init(keySize);
//		key = keyGenerator.generateKey();
//		cipher = Cipher.getInstance(transformation);
//		cipher.init(Cipher.ENCRYPT_MODE, key);
////		cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
////		AlgorithmParameterSpec paramSpec = new IvParameterSpec(cipher.getIV());
//		
////System.out.println("cipher.getIV(): " + cipher.getIV().hashCode());
//		System.out.println("key: " + key.hashCode());
//		
//		Cipher cipherToDecrypt = Cipher.getInstance(transformation);
////		cipherToDecrypt.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(cipher.getIV()));
//		cipherToDecrypt.init(Cipher.DECRYPT_MODE, key);
		byte[] raw = "1234567890123456".getBytes();
		Key keySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[cipher.getBlockSize()];
        
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
		
		
//		cipherToDecrypt.init(Cipher.DECRYPT_MODE, key, paramSpec);
//		CipherInputStream cipherInputStream = new CipherInputStream(url.openStream(), cipherToDecrypt);
        CipherInputStream cipherInputStream = new CipherInputStream(url.openStream(), cipher);
		
		AudioPlayer.player.start(cipherInputStream);
//		AudioPlayer.player.start(url.openStream());
//		cipherInputStream.close();
//		url.openStream().close();
	}
	
	public static void main(String[] args) {
		byte[] encrypted = encrypt("ABCDEFG");
		byte[] orgtext = decrypt(encrypted);
		System.out.println(new String(orgtext));
	}
	
	private static byte[] encrypt(String orgText) {
		byte[] encrypted = null;
		
		try {
			byte[] raw = "1234567890123456".getBytes();
			Key keySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = new byte[cipher.getBlockSize()];
            
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
            encrypted = cipher.doFinal(orgText.getBytes());
            System.out.println("encrypted string: " + encrypted.length);
            System.out.println("ivPS: " + ivParamSpec.hashCode());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return encrypted;
	}
	
	private static byte[] decrypt(byte[] encrypted) {
		byte[] orgText = null;
		
		try {
			byte[] raw = "1234567890123456".getBytes();
			Key keySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = new byte[cipher.getBlockSize()];
            
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
            orgText = cipher.doFinal(encrypted);
            System.out.println("decrypted string: " + encrypted.length);
            System.out.println("ivPS: " + ivParamSpec.hashCode());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return orgText;
	}
}
