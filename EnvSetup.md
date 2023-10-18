- create date: 2023-10-18
- update date:none

# 1. Base Env/環境準備 (OS:Ubuntu)
## 1.1 JDK17, fontconfig
```
#####インストール
$sudo apt update
$sudo apt install fontconfig openjdk-17-jre
$java -version
結果例
openjdk version "17.0.8" 2023-07-18
OpenJDK Runtime Environment (build 17.0.8+7-Debian-1deb12u1)
OpenJDK 64-Bit Server VM (build 17.0.8+7-Debian-1deb12u1, mixed mode, sharing)
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
export JAVA_HOME=/usr/lib/jvm/java
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
```
## 1.3 Jenkins
```
#####install jenkinsインストール
$sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
$sudo apt-get update
$sudo apt-get install jenkins

#####start Jenkins/起動
$sudo systemctl enable jenkins
$sudo systemctl start jenkins
$sudo systemctl status jenkins
ステータス例：
Loaded: loaded (/lib/systemd/system/jenkins.service; enabled; vendor preset: enabled)
Active: active (running) since Tue 2018-11-13 16:19:01 +03; 4min 57s ago

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
postgres=# create user sonarqube SUPERUSER with login password 'xxxxxxxx';
\q

#####データベースsonarqube
$psql -h localhost -u sonarqube -W
sonarqube=# create database sonarqube encoding utf8;
SHOW SERVER_ENCODING;
\q

#####auto start on system starting(Optional)/必要に応じて実施
sudo systemctl enable postgres
```
## 1.5 SonarQube
```
#####システムチェック
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
ExecStart=/bin/nohup /opt/java/bin/java -Xms32m -Xmx32m -Djava.net.preferIPv4Stack=true -jar /opt/sonarqube/lib/sonar-application-9.9.1.69595.jar
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












