package com.sonarcicd.demo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * ヘルスチェックサービス
 * 
 */
@Component
public class ServiceCheckUtil {

    static Logger logger = LoggerFactory.getLogger(ServiceCheckUtil.class);

    @Value("${healthcheck.jenkins.url}") //jenkins login url
    private static  String JENKINS_URL;
    @Value("${healthcheck.sonarqube.url}") //sonarqube login url
    private static   String SONARQUBE_URL;
    @Value("${healthcheck.database.url}") //databse endpoint
    private static  String DATABASE_URL;

    static RestTemplate restTemplate = new RestTemplate();
   
    //return false if restapi calling failed
    public static boolean checkJenkins(){
        logger.info("Service:checkJenkins URI:"+JENKINS_URL);
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
     @Autowired
    public static boolean checkDatabase(){
        logger.info("Service:checkDatabase URI:"+DATABASE_URL);
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
        logger.info("Service:checkSonarQube URI:"+SONARQUBE_URL);
        try{
            if(restTemplate.getForEntity(SONARQUBE_URL, String.class).getStatusCode().isError()){
                return false;
            }else{
                return true;
            }
        }catch(RestClientException ex){return false;}
    }
}