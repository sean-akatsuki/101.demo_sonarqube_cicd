package com.sonarcicd.demo.controller;

import com.sonarcicd.demo.service.ServiceCheckUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Restapi for AJAX access */
@RestController
public class RestapiController {

	@GetMapping("/checkjenkins")
	public String checkJenkins() {
		return ServiceCheckUtil.checkJenkins()? "0":"1";
	}
	
	@GetMapping("/checksonar")
	public String checkSonarQube() {
		return ServiceCheckUtil.checkSonarQube()? "0":"1";
	}	
	
	@GetMapping("/checkdb")
	public String checkDatabase() {
		return ServiceCheckUtil.checkDatabase()? "0":"1";
	}


}
