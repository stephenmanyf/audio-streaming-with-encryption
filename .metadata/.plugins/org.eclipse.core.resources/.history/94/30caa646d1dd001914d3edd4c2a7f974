package com.example.demo;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

@SpringBootApplication
public class CipherOutputStreamApplication implements CommandLineRunner {
	
	public static String audioFilePath;

	public static void main(String[] args) {
		SpringApplication.run(CipherOutputStreamApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			String filePath = (args.length == 0 ? null: args[0]);
			SimpleAudioPlayer audioPlayer = new SimpleAudioPlayer();
			
//			if (filePath == null || filePath.equals("")) {
////				filePath = getClass().getResource("temple_of_love-sisters_of_mercy.wav").getPath();
////				filePath = new ClassPathResource("temple_of_love-sisters_of_mercy.wav").getFile().getAbsolutePath();
//				filePath = ResourceUtils.getFile("classpath:temple_of_love-sisters_of_mercy.wav").getPath();
//				
////				filePath = new ClassPathResource("temple_of_love-sisters_of_mercy.wav").getPath();
//			}
			
			if (filePath != null && !filePath.equals("")) {
				System.out.println("file path: " + filePath);
				audioFilePath = filePath;
				audioPlayer.load(new File(filePath));
			} else {
				ClassPathResource resource = new ClassPathResource("temple_of_love-sisters_of_mercy.wav");
//				audioPlayer.load(resource.getInputStream());
				audioPlayer.load(resource.getURL());
			}
//			audioPlayer.play();
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
//		try {
//			SimpleAudioPlayer audioPlayer = new SimpleAudioPlayer();
//			audioPlayer.play();
//			Scanner sc = new Scanner(System.in);
//			
//			while (true) {
//				System.out.println("1. pause");
//				System.out.println("2. resume");
//				System.out.println("3. restart");
//				System.out.println("4. stop");
//				System.out.println("5. Jump to specific time");
//				int c = sc.nextInt();
//				audioPlayer.gotoChoice(c);
//				if (c==4) break;
//			}
//		} catch (Exception ex) {
//			System.out.println("Error with playing sound.");
//			ex.printStackTrace();
//		}
	}

}
