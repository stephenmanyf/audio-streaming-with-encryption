package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.CipherOutputStreamApplication;
import com.example.demo.SimpleAudioPlayer;

@RestController
@RequestMapping("/")
public class Controller {

//	@Autowired
//	private SimpleAudioPlayer wavPlayer;
	
	@GetMapping("/music")
	public ResponseEntity<Resource> getAudio() throws IOException {
//		File file = new File("src/main/resources/temple_of_love-sisters_of_mercy.wav");
//		File file = new File(CipherOutputStreamApplication.audioFilePath);
		ClassPathResource resource = new ClassPathResource("temple_of_love-sisters_of_mercy.wav");
//		File file = resource.getFile();
		resource.getInputStream();
		
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "filename=music.wav");
		header.add("Cache-Control",  "no-cache, no-store, must-revalidate");
		header.add("Pragma",  "no-cache");
		header.add("Expires", "0");
		
//		Path path = Paths.get(file.getAbsolutePath());
//		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		
		return ResponseEntity.ok()
				.headers(header)
//				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}
	
	
	/* variables used to encrypt the data */
	private int keySize = 128;
	private final String transformation = "AES/CBC/PKCS5Padding";
	private final String algorithm = "AES";
	private Key key = null;
	private Cipher cipher = null;
	
	/* encrypt the data */
//	private void encryptFile(InputStream inputStream) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException {
//		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
//		keyGenerator.init(keySize);
//		cipher = Cipher.getInstance(transformation);
//		cipher.init(Cipher.ENCRYPT_MODE, key);
//		
//		
//	}
	
//	private Key generateKey() throws Exception 
//    {
//			String keyValue = "1234567890123456"; 
//            Key key = new SecretKeySpec(keyValue.getBytes(), algorithm);
//            return key;
//    }
	
//	private static byte[] encrypt(String orgText) {
//		byte[] encrypted = null;
//		
//		try {
//			byte[] raw = "1234567890123456".getBytes();
//			Key keySpec = new SecretKeySpec(raw, "AES");
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            byte[] iv = new byte[cipher.getBlockSize()];
//            
//            IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
//            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
//            encrypted = cipher.doFinal(orgText.getBytes());
//            System.out.println("encrypted string: " + encrypted.length);
//            System.out.println("ivPS: " + ivParamSpec.hashCode());
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return encrypted;
//	}
	
	@GetMapping("/music/encrypted")
	public void getEncryptedAudio(final HttpServletResponse response) throws Exception {
		ClassPathResource resource = new ClassPathResource("temple_of_love-sisters_of_mercy.wav");
		
//	    // 8-byte initialization vector
//	    byte[] iv = {
//	    	(byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,
//	    	(byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3,
//	    	(byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,
//	    	(byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3
//	    };
//	    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);

//		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
//		keyGenerator.init(keySize);
//		key = keyGenerator.generateKey();
//	    key = generateKey();
		
//		cipher = Cipher.getInstance(transformation); **
//		cipher.init(Cipher.ENCRYPT_MODE, key); **

		byte[] raw = "1234567890123456".getBytes();
		Key keySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[cipher.getBlockSize()];
        
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
		
		
//		AlgorithmParameterSpec paramSpec = new IvParameterSpec(cipher.getIV());
//		cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		
//		System.out.println("cipher.getIV(): " + cipher.getIV().hashCode());
//		System.out.println("key: " + key.hashCode());
		
//		try (OutputStream outputStream = new CipherOutputStream(new File))
		OutputStream outputStream = new CipherOutputStream(response.getOutputStream(), cipher);
		
//		HttpHeaders header = new HttpHeaders();
//		header.add(HttpHeaders.CONTENT_DISPOSITION, "filename=music.wav");
//		header.add("Cache-Control",  "no-cache, no-store, must-revalidate");
//		header.add("Pragma",  "no-cache");
//		header.add("Expires", "0");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=music.wav");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma",  "no-cache");
		response.setHeader("Expires", "0");
//		StreamUtils.copy(in, response.getOutputStream());
		StreamUtils.copy(resource.getInputStream(), outputStream);
		
//		return ResponseEntity.ok()
//				.headers(header)
////				.contentLength(file.length())
//				.contentType(MediaType.parseMediaType("application/octet-stream"))
//				.body(resource);
	}
}
