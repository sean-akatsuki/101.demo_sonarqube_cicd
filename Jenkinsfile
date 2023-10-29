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
                git branch: 'develop', url: 'https://github.com/sean-akatsuki/101.demo_sonarqube_cicd.git'
            }
        }
        
        stage('SonarQubeAnalysis'){
            steps{
                withSonarQubeEnv('sonarqube'){
    	            sh "mvn clean verify sonar:sonar -Dsonar.projectKey=demo_sonarqube_cicd -Dsonar.projectName='demo_sonarqube_cicd'"
                }
            }
        }
        
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
        
        stage('AppBuild') {
            steps{
                sh 'mvn -f pom.xml clean package'
            }
        }
    
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