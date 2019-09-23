package com.example.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreamingApplication implements CommandLineRunner {

	private final static String SOURCE = "Touchables\n" + 
			"If the basic button doesn't look right for your app, you can build your own button using any of the \"Touchable\" components provided by React Native. The \"Touchable\" components provide the capability to capture tapping gestures, and can display feedback when a gesture is recognized. These components do not provide any default styling, however, so you will need to do a bit of work to get them looking nicely in your app.\n" + 
			"Which \"Touchable\" component you use will depend on what kind of feedback you want to provide:\n" +  
			"Generally, you can use TouchableHighlight anywhere you would use a button or link on web. The view's background will be darkened when the user presses down on the button.\n" + 
			"You may consider using TouchableNativeFeedback on Android to display ink surface reaction ripples that respond to the user's touch.\n" +  
			"TouchableOpacity can be used to provide feedback by reducing the opacity of the button, allowing the background to be seen through while the user is pressing down.\n" + 
			"If you need to handle a tap gesture but you don't want any feedback to be displayed, use TouchableWithoutFeedback.\n" + 
			"In some cases, you may want to detect when a user presses and holds a view for a set amount of time. These long presses can be handled by passing a function to the onLongPress props of any of the \"Touchable\" components.";
			
	public static void main(String[] args) {
		SpringApplication.run(StreamingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		
		outStream.write(SOURCE.getBytes());
		
		ByteArrayInputStream inStream = new ByteArrayInputStream();
		
		
//		String finalString = new String(stream.toByteArray());
//		
//		System.out.println(finalString);
	}

}
