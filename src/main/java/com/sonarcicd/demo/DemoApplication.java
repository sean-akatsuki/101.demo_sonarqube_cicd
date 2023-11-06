package com.sonarcicd.demo;

//import java.io.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.h2.security.SHA256;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		//String username = "steve";
		//String password = "blue";
		//System.out.println(username+password);
		/** Noncompliant block*/
		String inputString = "s3cr37";
		byte[] key         = inputString.getBytes();
		SHA256.getHMAC(key, message);  // Noncompliant

		SpringApplication.run(DemoApplication.class, args);
	}

}
