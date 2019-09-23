package com.example.demo;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CipherDemoApplication implements CommandLineRunner {

	/* File to write encrypted data */
	private static File file;
	private static String filePath = "a.txt";
	
	/* variables used to encrypt the data */
	private static int keySize = 128;
	private static String transformation = "AES/CBC/PKCS5Padding";
	private static String algorithm = "AES";
	private static Key key = null;
	private static Cipher cipher = null;
	
	/* Encrypt the data and write to the file a.txt. */
	private static void encryptFile() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		keyGenerator.init(keySize);
		key = keyGenerator.generateKey();
		cipher = cipher.getInstance(transformation);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		try (OutputStream outputStream = new BufferedOutputStream(
				new CipherOutputStream(new FileOutputStream(file), cipher))) {
			for (int i = 0; i < 10; i++) {
				outputStream.write(new String("Hello World\n").getBytes());
			}
		}
	}
	
	/* Print the contents of file a.txt */
	private static void printFile() throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			String line = null;
			
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
	}
	
	/* Decrypt and print the contents of file a.txt. */
	private static void decryptAndPrintFile() throws Exception {
		Cipher cipherToDecrypt = Cipher.getInstance(transformation);
		cipherToDecrypt.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(cipher.getIV()));
		
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new CipherInputStream(new FileInputStream(file), cipherToDecrypt)))) {
			
			String line = null;
			
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CipherDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		file = new File(filePath);
		
		encryptFile();
		
		System.out.println("Data before Decrypting");
		System.out.println("************************************");
		printFile();
		
		System.out.println("\nData After Decrypting");
		System.out.println("************************************");
		decryptAndPrintFile();
	}

}
