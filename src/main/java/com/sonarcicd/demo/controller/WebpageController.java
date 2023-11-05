package com.sonarcicd.demo.controller;

import com.sonarcicd.demo.service.ServiceCheck;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *ページ遷移コントローラー 
 *  
*/ 

@Controller
public class WebpageController {

    @Value("${healthcheck.JENKINS_URL}")
    private String JENKINS_URL;
    @Value("${healthcheck.SONARQUBE_URL}")
    private String SONARQUBE_URL;
    @Value("${healthcheck.DATABASE_URL}")
    private String DATABASE_URL;

    @GetMapping("/index")
    public String helloWorld(Model model){
        model.addAttribute("message", "this is a index page via Thymleaf");
        return "index";
    }    
  
    @GetMapping("/status")
    public String healthCheck(Model model){
        ServiceCheck sc= new ServiceCheck();

        model.addAttribute("database_err", sc.checkDatabase(DATABASE_URL)? "0":"1");
        model.addAttribute("jenkins_err", sc.checkURL(JENKINS_URL)? "0":"1");
        model.addAttribute("sonarqube_err", sc.checkURL(SONARQUBE_URL)? "0":"1");
        return "status";
    }
    @GetMapping("/")
     public String rootCon(Model model){
        return "redirect:/status";
    }
}
