package com.sonarcicd.demo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
/**
 * ヘルスチェックサービス
 * 
 */

public class ServiceCheck {
     Logger logger = LoggerFactory.getLogger(ServiceCheck.class);
     RestTemplate restTemplate = new RestTemplate();
   
    //return false if restapi calling failed
    public  boolean checkURL(String URL){
        logger.info("Service:checkURL URI:"+URL);
        try{
            if(restTemplate.getForEntity(URL, String.class).getStatusCode().isError()){
                return false;
            }else{
                return true;
            }
        }catch(RestClientException ex){
            return false;
        }catch(Exception e){
            return false;
        }
    }

    //return false if restapi calling failed
    public  boolean checkDatabase(String endpoint){
        logger.info("Service:checkDatabase URI:"+endpoint);
        try{
            Connection conn = DriverManager.getConnection(endpoint);
            if (conn != null) {
                try {conn.close();} catch (SQLException e) { /* Ignored */}
            }
            return true;
        }catch (SQLException ex){return false;}
    }

    
}