#!/usr/bin/env groovy
pipeline {
    agent any
    tools {
        maven 'M3'
    }
    //def scannerHome=tool 'sonarqube'
    
    stages {
        //pull code source from github
        stage('Preparation') { 
            steps{
                git branch: 'develop', url: 'https://github.com/sean-akatsuki/101.demo_sonarqube_cicd.git'
            }
        }
        
        //calling SonarQube code analysis 
        stage('SonarQubeAnalysis'){
            steps{
                withSonarQubeEnv('sonarqube'){
    	            sh "mvn clean verify sonar:sonar -Dsonar.projectKey=demo_sonarqube_cicd -Dsonar.projectName='demo_sonarqube_cicd'"
                }
            }
        }
        
        //check code analysis result
        stage('SonarQubeResultCheck'){
            steps{
                script{
                    timeout(5){
    	                def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                        }
                    }
                }
            }
        }
        
        //build code source
        stage('AppBuild') {
            steps{
                sh 'mvn -f pom.xml clean package'
            }
        }

        //deploy application to server
        stage('AppDeploy'){
            steps{
                sh 'sudo systemctl stop demoapp.service'
                sh 'sudo cp target/application.release.jar /home/appuser/application.jar'
                sh 'sudo chown appuser:appuser /home/appuser/application.jar'
                sh 'sudo systemctl start demoapp.service'
            }
        }
    }
}