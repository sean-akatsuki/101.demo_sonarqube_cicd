- create date: 2023-10-18
- update date:none

# 1. Base Env/環境準備 (OS:Ubuntu, memory >=4Gib, >=2vcpu, Disk >=20Gib)
## 1.1 JDK17, fontconfig
```
#####インストール
$sudo apt update
$sudo apt install fontconfig openjdk-17-jre openjdk-17-jdk
$java -version
結果例
openjdk version "17.0.8.1" 2023-08-24
OpenJDK Runtime Environment (build 17.0.8.1+1-Ubuntu-0ubuntu120.04)
OpenJDK 64-Bit Server VM (build 17.0.8.1+1-Ubuntu-0ubuntu120.04, mixed mode, sharing)
```
## 1.2 Maven
```
#####download binary file / 最新バージョンMavenの取得
$wget https://dlcdn.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.tar.gz -P /tmp

#####解凍
$sudo tar xf /tmp/apache-maven-*.tar.gz -C /opt

#####link
$sudo ln -s /opt/apache-maven-3.9.5 /opt/maven

#####setup
$sudo nano /etc/profile.d/maven.sh

#####add contents below to maven.sh
-----
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export M2_HOME=/opt/maven
export MAVEN_HOME=/opt/maven
export PATH=${M2_HOME}/bin:${PATH}
----

#####権限
$sudo chmod +x /etc/profile.d/maven.sh

#####設定を読み込み
$source /etc/profile.d/maven.sh

#####確認
$mvn -version
Apache Maven 3.9.5 (57804ffe001d7215b5e7bcb531cf83df38f93546)
Maven home: /opt/maven
Java version: 17.0.8.1, vendor: Private Build, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "5.15.0-1036-aws", arch: "amd64", family: "unix"
```
## 1.3 Jenkins
```
#####install jenkinsインストール
$sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
$echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
$sudo apt-get update
$sudo apt-get install jenkins

#####start Jenkins/起動
$sudo systemctl enable jenkins
$sudo systemctl start jenkins
$sudo systemctl status jenkins
ステータス例：
● jenkins.service - Jenkins Continuous Integration Server
     Loaded: loaded (/lib/systemd/system/jenkins.service; enabled; vendor preset: enabled)
     Active: active (running) since Thu 2023-10-19 00:26:33 UTC; 30s ago
   Main PID: 7369 (java)
      Tasks: 43 (limit: 1141)
     Memory: 291.8M
     CGroup: /system.slice/jenkins.service
             └─7369 /usr/bin/java -Djava.awt.headless=true -jar /usr/share/java/jenkins.war --webroot=/var/cache/jenkins/war --httpPort=8080

#####firewall configure(Optional)-必要に応じてファイアウォールを設定、クラウド環境では不要
$YOURPORT=8080
$PERM="--permanent"
$SERV="$PERM --service=jenkins"
$sudo firewall-cmd $PERM --new-service=jenkins
$sudo firewall-cmd $SERV --set-short="Jenkins ports"
$sudo firewall-cmd $SERV --set-description="Jenkins port exceptions"
$sudo firewall-cmd $SERV --add-port=$YOURPORT/tcp
$sudo firewall-cmd $PERM --add-service=jenkins
$sudo firewall-cmd --zone=public --add-service=http --permanent
$sudo firewall-cmd --reload

#####unlocking jenkins/初期化
#####obtain password/パスワードの確認
$sudo cat /var/lib/jenkins/secrets/initialAdminPassword
#####login/ログイン
access http://localhost:8080
#####Customizing Jenkins with plugins/プラグインをインストール  
ページ操作：Install suggested plugins or Select plugins to install
#####Creating the first administrator user/管理ユーザを作成 
ページ操作：[Create First Admin User] page appears --> input --> Save and Finish -->[Jenkins is ready ] page --> click [Start using Jenkins]
```

## 1.4 Database-PostGreSQL  
```
#####installation インストール
$ sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'
$ wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -
$ sudo apt-get update
$ sudo apt-get install postgresql-15

#####DBユーザsonarqube
$ sudo -i -u postgres
$psql
postgres=# create user sonarqube with password 'xxxxxxxx';
alter user sonarqube with SUPERUSER;
create database sonarqube owner sonarqube encoding utf8;
\q

#####データベースsonarqube
$psql -h localhost -u sonarqube -W
sonarqube=# 
SHOW SERVER_ENCODING;
\q

#####auto start on system starting(Optional)/通常不要、必要に応じて実施
sudo systemctl enable postgres
```
## 1.5 SonarQube
```
#####システムチェック、ご注意:SonarQubeの利用でMem (<2Gib起動できない、=2Gib稼働超遅い)>2Gib,より良いIOを使ってください ；
/***desc start***/
vm.max_map_count is greater than or equal to 524288
fs.file-max is greater than or equal to 131072
the user running SonarQube can open at least 131072 file descriptors
the user running SonarQube can open at least 8192 threads


update(with root user): 
sysctl -w vm.max_map_count=524288
sysctl -w fs.file-max=131072
ulimit -n 131072
ulimit -u 8192

add commands above to os init script 
/***desc end***/
$sudo sysctl vm.max_map_count
$sudo sysctl fs.file-max
$sudo ulimit -n
$sudo ulimit -u

$sudo grep SECCOMP /boot/config-$(uname -r)
結果例:
  CONFIG_HAVE_ARCH_SECCOMP_FILTER=y
  CONFIG_SECCOMP_FILTER=y
  CONFIG_SECCOMP=y

#####SonarQubeの取得(ユーザsonarqubeを作成して、ユーザsonarqubeで実施)
$wget https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-10.2.1.78527.zip
##### access link below via browser manually if you can't download it via command above
##### https://www.sonarsource.com/products/sonarqube/downloads/

#####設定
$unzip  file_name.zip #<SONARQUBE_HOME>
$vi <SONARQUBE_HOME>/conf/sonar.properties
---------
Example for PostgreSQL
sonar.jdbc.username=sonarqube
sonar.jdbc.password=xxxxxxx
sonar.jdbc.url=jdbc:postgresql://localhost/sonarqube
----------

#####設定(Optional)-higher IO device for elasticSearch
#####ご認識ください：/var/sonarqube/に紐づいているDeviceがhigh IOである前提
$vi <SONARQUBE_HOME>/conf/sonar.properties
----------
sonar.path.data=/var/sonarqube/data
sonar.path.temp=/var/sonarqube/temp
----------
#####設定(Optional)-port(default 9000) and context(default /) of web server
$vi <SONARQUBE_HOME>/conf/sonar.properties
----------
sonar.web.host=192.168.0.1
sonar.web.port=80
sonar.web.context=/sonarqube
----------

#####SonarQubeサービスを作成
$sudo ln -s /home/sonarqube/sonarqube-10.2.1.78527 /opt/sonarqube
$sudo vi /etc/systemd/system/sonarqube.service
-----------
[Unit]
Description=SonarQube service
After=syslog.target network.target

[Service]
Type=simple
User=sonarqube
Group=sonarqube
PermissionsStartOnly=true
ExecStart=/bin/nohup /usr/bin/java -Xms512m -Xmx3072m -Djava.net.preferIPv4Stack=true -jar /opt/sonarqube/lib/sonar-application-xxxxxx.jar
StandardOutput=syslog
LimitNOFILE=131072
LimitNPROC=8192
TimeoutStartSec=5
Restart=always
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
-----------
#####sonarqube起動
$sudo systemctl enable sonarqube.service
$sudo systemctl start sonarqube.service

#####login
-http://localhost:9000
-login: admin
-password: admin

```

# 2. CICD Integration(SonarQube, Jenkins, Github) 

## 2.1 基本準備 
### 2.1.1 prepare application/デモ用アプリの準備
本リポジトリのコードソースをご参考    
後ろのcicd Stepでアプリ更新にはサービスの停止・再起動が必要になるため、アプリをOS自動起動サービス(systemd)に登録完了までにしてください。    
※プロセスのkill方法でも実装できますが、分かりやすくように、OS(systemd)サービスとしてアプリを停止・起動・再起動する  
OSサービス登録例：  
```
[Unit]

Description=A Spring Boot application
After=syslog.target

[Service]
User=appuser
ExecStart=/bin/sh -c "/usr/bin/java -jar /home/appuser/application.jar  >> /home/appuser/application.log"
SuccessExitStatus=143 

[Install] 
WantedBy=multi-user.target
```

## 2.1.2 jenkinsでmaven基本準備  
操作ルート：install plugin [Maven Integration]-->manage jenkins --> Tool  --> maven追加 -->名前:M3 & MAVEN_HOME:インストールされたmavenのhomeを指定(```mvn -version```で確認できる) --> save/保存

## 2.2 Jenkins & Githubの連携設定
### 2.2.1 jenkinsでgithub用のsecretを作成  
操作ルート：jenkins-->jenkinsの管理/manage jenkins-->credentials/認証情報 --> Domains(Global) -->add credentials(認証情報を追加)　--> SecretText --> Secret文字列を入力, id=github-webhook-->save/保存  

jenkins-->jenkinsの管理/manage jenkins-->システム/system --> Github --> 高度な設定/Advanced　--> Shared secretsを追加 --> github-webhookを選択 --> save/保存  

### 2.2.2 作成したsecretを githubのwebhookに配置  
操作ルート：リポジトリのsettingsをクリック--> webhooksをクリック -->  payload url: ```http://JENKINSのURL:port/github-webhook/``` & Secret:上記１で作成したSecret文字列 & Trigger: Pullrequest,Pushes & Activeをチェック -->save/保存  

## 2.3 Jenkins & SonarQubeの連携設定
### 2.3.1 Sonarqubeでプロジェクトを作成  
token作成
### 2.3.2 SoanrqubeでJenkins用webhookを作成
### 2.3.3 JenkinsでSonarQubeとの連携の設定
プラグインのインストール    
Tokenの設定  

## 2.4 Jenkins pipelineジョブの作成  
### 2.4.1 Jenkinsfileスクリプトの準備
[スクリプト](Jenkinsfile)  

### 2.4.2 Jenkins Jobの作成  

# 3. 実施テスト 
脆弱性あるコード
```
import org.h2.security.SHA256;

		String inputString = "s3cr37";
		byte[] key= inputString.getBytes();
		byte[] message="message".getBytes(); 
		SHA256.getHMAC(key, message);  

```