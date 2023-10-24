package com.sonarcicd.demo.service;

import org.springframework.web.client.RestTemplate;


public class ServiceCheckUtil {

    static final String JENKINS_URL="http://localhost:8080";
    static final String SONARQUBE_URL="http://localhost:9000";

    RestTemplate restTemplate = new RestTemplate();
    
    public static boolean checkJenkins(){
        if(restTemplate.getForEntity(JENKINS_URL, String.class).getStatusCode().isError()){
            return false;
        }else{
            return true;
        }
    }

    public static boolean checkDatabase(){
        return true;
    }

    public static boolean checkSonarQube(){
        if(restTemplate.getForEntity(SONARQUBE_URL, String.class).getStatusCode().isError()){
            return false;
        }else{
            return true;
        }
    }
    }

}