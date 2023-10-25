package com.sonarcicd.demo.controller;

import com.sonarcicd.demo.service.ServiceCheck;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Restapi for AJAX access */
@RestController
public class RestapiController {

	@Value("${healthcheck.JENKINS_URL}")
    private String JENKINS_URL;
    @Value("${healthcheck.SONARQUBE_URL}")
    private String SONARQUBE_URL;
    @Value("${healthcheck.DATABASE_URL}")
    private String DATABASE_URL;

	ServiceCheck sc=new ServiceCheck();
	@GetMapping("/checkjenkins")
	public String checkJenkins() {
		return sc.checkURL(JENKINS_URL)? "0":"1";
	}
	
	@GetMapping("/checksonar")
	public String checkSonarQube() {
		return sc.checkURL(SONARQUBE_URL)? "0":"1";
	}	
	
	@GetMapping("/checkdb")
	public String checkDatabase() {
		return sc.checkDatabase(DATABASE_URL)? "0":"1";
	}


}
