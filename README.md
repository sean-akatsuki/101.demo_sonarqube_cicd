# 101.demo_sonarqube_cicd  

## memo 
```
■Ubuntu/OpenJDK 17/fontconfig   

■SonarQube  
	1.SonarQube Community Edition:  
	https://www.sonarsource.com/products/sonarqube/downloads/?gads_campaign=SQ-Mroi-PMax&gads_ad_group=Global&gads_keyword=&gad_source=1&gclid=EAIaIQobChMIg9u8wPn7gQMVz62WCh1_XQp2EAAYASACEgL1CfD_BwE  
	2.Preparation:  
	https://docs.sonarsource.com/sonarqube/latest/requirements/prerequisites-and-overview/  
		1. install jdk   
		The SonarQube server requires Java version 17  
		2.install a db  
		PostgreSQL 15  
		3.Linux - see doc link  

	3.Installation & Setup:  
	https://docs.sonarsource.com/sonarqube/latest/setup-and-upgrade/install-the-server/  
	https://docs.sonarsource.com/sonarqube/latest/setup-and-upgrade/configure-and-operate-a-server/operating-the-server/  
	https://docs.sonarsource.com/sonarqube/latest/setup-and-upgrade/install-the-server/  

■Jenkins  
	1.Preparation  
	https://www.jenkins.io/doc/book/installing/linux/  
		Java 17-OpenJDK, Eclipse Temurin, and Amazon Corretto  
		LTS 2.361.1 (September 2022)  
	2. installation & setup  
	https://www.jenkins.io/doc/book/installing/linux/#debianubuntu  
	
■SprintBoot with web content
https://spring.io/guides/gs/serving-web-content/
https://www.baeldung.com/spring-thymeleaf-css-js  

■CICD flow  
	pull request --> sonarqube --> report to reviewer --> yes/no  「yes」--> source build/compile/release --> cicd finished(successful)
                                                                     「no」--> cicd finished(rejected by reviewer)  

■予定実施step
1. 環境準備-os,db,jdk,jenkins,sonarqube等々
2. 開発コード準備(springboot-java,html,javascript,css)
3. Jenkins pipelineの作成
4. SonarQube使い関連の実施
5. Jenkins & SonarQube & Github のIntegration



jenkins --> circle ci / github actions
jenkins & slack: https://plugins.jenkins.io/slack/
jenkins & aws sns: https://plugins.jenkins.io/snsnotify/
jenkins & sonarqube:
https://plugins.jenkins.io/sonar/
https://docs.sonarsource.com/sonarqube/9.8/analyzing-source-code/scanners/jenkins-extension-sonarqube/
https://stackoverflow.com/questions/65880598/how-to-obtain-the-sonarqube-taskid-report-url-in-jenkins-pipeline

cicd trigger decision:
https://softwareengineering.stackexchange.com/questions/238146/continuous-integration-deployment-test-on-commit-pull-request-or-what

```                  

◇参考資料/reference  
https://docs.sonarsource.com/sonarqube/latest/  
https://www.sonarsource.com/products/sonarqube/downloads/  
https://www.jenkins.io/doc/book/installing/linux/  
https://maven.apache.org/install.html  
https://phoenixnap.com/kb/install-maven-on-ubuntu

[環境構築手順](EnvSetup.md)
