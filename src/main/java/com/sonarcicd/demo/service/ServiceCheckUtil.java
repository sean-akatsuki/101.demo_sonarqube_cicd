package com.sonarcicd.demo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
/**
 * ヘルスチェックサービス
 * 
 */
public class ServiceCheckUtil {

    @Value("${healthcheck.jenkins.url}") //jenkins login url
    private static  String JENKINS_URL;
    @Value("${healthcheck.sonarqube.url}") //sonarqube login url
    private static   String SONARQUBE_URL;
    @Value("${healthcheck.database.url}") //databse endpoint
    private static  String DATABASE_URL;

    static RestTemplate restTemplate = new RestTemplate();
   
    //return false if restapi calling failed
    public static boolean checkJenkins(){
        try{
            if(restTemplate.getForEntity(JENKINS_URL, String.class).getStatusCode().isError()){
                return false;
            }else{
                return true;
            }
        }catch(RestClientException ex){
            return false;
        }
    }

    //return false if restapi calling failed
    public static boolean checkDatabase(){
        try{
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            if (conn != null) {
                try {conn.close();} catch (SQLException e) { /* Ignored */}
            }
            return true;
        }catch (SQLException ex){return false;}
    }

    //return false if restapi calling failed
    public static boolean checkSonarQube(){
        try{
            if(restTemplate.getForEntity(SONARQUBE_URL, String.class).getStatusCode().isError()){
                return false;
            }else{
                return true;
            }
        }catch(RestClientException ex){return false;}
    }
}