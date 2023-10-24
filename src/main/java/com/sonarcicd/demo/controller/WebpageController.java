package com.sonarcicd.demo.controller;

import com.sonarcicd.demo.service.ServiceCheckUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *ページ遷移コントローラー 
 *  
*/ 

@Controller
public class WebpageController {

    @GetMapping("/index")
    public String helloWorld(Model model){
        model.addAttribute("message", "this is a index page via Thymleaf");
        return "index";
    }    
  
    @GetMapping("/status")
    public String healthCheck(Model model){
        model.addAttribute("database_err", ServiceCheckUtil.checkDatabase()? "0":"1");
        model.addAttribute("jenkins_err", ServiceCheckUtil.checkJenkins()? "0":"1");
        model.addAttribute("sonarqube_err", ServiceCheckUtil.checkSonarQube()? "0":"1");
        return "status";
    }
}
