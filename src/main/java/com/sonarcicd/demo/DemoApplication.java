package com.sonarcicd.demo;

//import java.io.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		//String username = "steve";
		//String password = "blue";
		//System.out.println(username+password);
		SpringApplication.run(DemoApplication.class, args);
	}

}
