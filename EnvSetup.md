- create date: 2023-10-18
- update date:none

# 1. Base Env/環境準備 (OS:Ubuntu)
## 1.1 JDK17, fontconfig/ jdk17のインストール
```
$sudo apt update
$sudo apt install fontconfig openjdk-17-jre
$java -version
結果例
openjdk version "17.0.8" 2023-07-18
OpenJDK Runtime Environment (build 17.0.8+7-Debian-1deb12u1)
OpenJDK 64-Bit Server VM (build 17.0.8+7-Debian-1deb12u1, mixed mode, sharing)
```
## 1.2 Maven/ Mavenのインストール
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

#####auto start on system starting/必要に応じて実施
sudo systemctl enable postgres
```
## 1.5 SonarQube














