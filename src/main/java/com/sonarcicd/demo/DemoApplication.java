package com.sonarcicd.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.h2.security.SHA256;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		String inputString = "s3cr37";
		byte[] key= inputString.getBytes();
		byte[] message="message".getBytes(); 
		SHA256.getHMAC(key, message);  
		SpringApplication.run(DemoApplication.class, args);
	}

}
