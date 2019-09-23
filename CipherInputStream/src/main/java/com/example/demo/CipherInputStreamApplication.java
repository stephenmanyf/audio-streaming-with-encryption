package com.example.demo;

import java.net.URL;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.component.SimpleAudioPlayer;

@SpringBootApplication
public class CipherInputStreamApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CipherInputStreamApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		SimpleAudioPlayer player = new SimpleAudioPlayer();
//		URL url = new URL("http://localhost:8080/music");
//		player.load(url);
//		player.play();
	}

}
