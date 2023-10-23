package com.sonarcicd.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestapiController {

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

}