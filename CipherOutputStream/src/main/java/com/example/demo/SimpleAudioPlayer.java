package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.springframework.stereotype.Component;

@Component
public class SimpleAudioPlayer {

	//to store current position
	Long currentFrame;
	Clip clip;
	
	//current status of clip
	String status;
	
	AudioInputStream audioInputStream;
	
//	String filePath = "src/main/resources/temple_of_love-sisters_of_mercy.wav";
	
	//Constructor to initialize streams and clip
	public SimpleAudioPlayer() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//		File audioInputStreamArg = new File(filePath);
//		
//		//create AudioInputStream object
//		audioInputStream = AudioSystem.getAudioInputStream(audioInputStreamArg.getAbsoluteFile());
//
//		
//		// create clip reference
//		clip = AudioSystem.getClip();
//		
//		// open audioInputStream to the clip
////		clip.open(audioInputStream);
////		
////		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void load(URL url) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioInputStream = AudioSystem.getAudioInputStream(url);
		
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
	}
	
	public void load(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
		
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
	}
	
	public void load(InputStream stream) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioInputStream = AudioSystem.getAudioInputStream(stream);
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);		
	}
	
	// work as the user enters his choice
	public void gotoChoice(int c) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		switch (c) {
			case 1:
				pause();
				break;
			case 2:
				resumeAudio();
				break;
			case 3:
				restart();
				break;
			case 4:
				stop();
				break;
			case 5:
				System.out.println("Enter time (" + 0 + ", " + clip.getMicrosecondLength() + ")");
				Scanner sc = new Scanner(System.in);
				long c1 = sc.nextLong();
				jump(c1);
				break;
		}
	}
	
	//Method to play the audio
	public void play() {
		//start the clip
		clip.start();
		
		status = "play";
	}
	
	//Method to pause the audio
	public void pause() {
		if (status.equals("pause")) {
			System.out.println("audio is already paused");
			return;
		}
		this.currentFrame = this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}
	
	//Method to resume the audio
	public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (status.equals("play")) {
			System.out.println("Audio is already " + "being played");
			return;
		}
		clip.close();
		resetAudioStream();
		clip.setMicrosecondPosition(currentFrame);
		this.play();
	}
	
	//Method to restart the audio
	public void restart() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		clip.stop();
		clip.close();
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
	}
	
	//Method to stop the audio
	public void stop() {
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}
	
	//Method to jump over a specific part
	public void jump(long c) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (c > 0 && c < clip.getMicrosecondLength()) {
			clip.stop();
			clip.close();
			resetAudioStream();
			currentFrame = c;
			clip.setMicrosecondPosition(c);
			this.play();
		}
	}
	
	//Method to reset audio stream
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
