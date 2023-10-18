
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
#download binary file / 最新バージョンMavenの取得
wget https://dlcdn.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.tar.gz -P /tmp
#解凍
sudo tar xf /tmp/apache-maven-*.tar.gz -C /opt
#link
sudo ln -s /opt/apache-maven-3.9.5 /opt/maven
#setup
sudo nano /etc/profile.d/maven.sh
#add contents below to maven.sh
-----
export JAVA_HOME=/usr/lib/jvm/java
export M2_HOME=/opt/maven
export MAVEN_HOME=/opt/maven
export PATH=${M2_HOME}/bin:${PATH}
----
#権限
sudo chmod +x /etc/profile.d/maven.sh
#設定を読み込み
source /etc/profile.d/maven.sh
#確認
mvn -version
```

