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
	

■CICD flow  
	pull request --> sonarqube --> report to reviewer --> yes/no  「yes」--> source build/compile/release --> cicd finished(successful)
                                                                     「no」--> cicd finished(rejected by reviewer)  

■予定実施step
1. 環境準備
2. 開発コード準備(java springboot)
3. Jenkins pipelineの作成
4. SonarQube使い関連の実施
5. Jenkins & SonarQubeのIntegration
```                  

◇参考資料/reference  
https://docs.sonarsource.com/sonarqube/latest/  
https://www.sonarsource.com/products/sonarqube/downloads/  
https://www.jenkins.io/doc/book/installing/linux/  
https://maven.apache.org/install.html  
https://phoenixnap.com/kb/install-maven-on-ubuntu

[環境構築手順](EnvSetup.md)
