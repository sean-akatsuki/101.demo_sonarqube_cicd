#!/usr/bin/env groovy
pipeline {
    agent any
    tools {
        maven 'M3'
    }
    //def scannerHome=tool 'sonarqube'
    stages {
        stage('Preparation') { 
            steps{
                //load latest resource from repo
                git branch: 'develop', url: 'https://github.com/sean-akatsuki/101.demo_sonarqube_cicd.git'
            }
        }
        
        stage('SonarQubeAnalysis'){
            steps{
                //read Sonar Server info & execute static analysis
                withSonarQubeEnv('sonarqube'){
    	            sh "mvn clean verify sonar:sonar -Dsonar.projectKey=demo_sonarqube_cicd -Dsonar.projectName='demo_sonarqube_cicd'"
                }
            }
        }
        
        stage('SonarQubeResultCheck'){
            steps{
                script{
                    timeout(5){
                        //wait & read static analysis result from webhook
    	                def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                        }
                    }
                }
            }
        }
        
        stage('AppBuild') {
            steps{
                //build java application
                sh 'mvn -f pom.xml clean package'
            }
        }
    
        stage('AppDeploy'){
            steps{
                //deploy application jar
                sh 'sudo systemctl stop demoapp.service'
                sh 'sudo cp target/application.release.jar /home/appuser/application.jar'
                sh 'sudo chown appuser:appuser /home/appuser/application.jar'
                sh 'sudo systemctl start demoapp.service'
            }
        }
        stage('PostDeploy'){
            steps{
                //clear local workspace after job execution
                cleanWs()
            }
        }
    }
}
