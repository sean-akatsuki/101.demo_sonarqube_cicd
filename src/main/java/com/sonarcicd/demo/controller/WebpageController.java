package com.sonarcicd.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebpageController {

    @GetMapping("/index")
    public String helloWorld(Model model){
        model.addAttribute("message", "this is a index page via Thymleaf");
        return "index";
    }    
  
    @GetMapping("/status")
    public String helloWorld(Model model){
        model.addAttribute("status_list", "status_list");
        return "status";
    }
}
