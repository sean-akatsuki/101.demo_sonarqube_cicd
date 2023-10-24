package com.sonarcicd.demo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.web.client.RestTemplate;
/**
 * ヘルスチェックサービス
 * 
 */
public class ServiceCheckUtil {

    //static final String JENKINS_URL="http://localhost:8080";
    //static final String SONARQUBE_URL="http://localhost:9000";
    //String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";

    @Value("${healthcheck.jenkins.url}")
    private static final String JENKINS_URL;

    @Value("${healthcheck.sonarqube.url}")
    private static final  String SONARQUBE_URL;

    @Value("${healthcheck.database.url}")
    private static final String DATABASE_URL;

    RestTemplate restTemplate = new RestTemplate();
    
    public static boolean checkJenkins(){
        try {} exception{}
        if(restTemplate.getForEntity(JENKINS_URL, String.class).getStatusCode().isError()){
            return false;
        }else{
            return true;
        }
    }

    public static boolean checkDatabase(){
        try{
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            
            return true;
        }catch (SQLException ex){
            return false;
        }
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