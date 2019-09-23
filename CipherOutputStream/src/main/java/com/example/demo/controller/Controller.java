package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
}
