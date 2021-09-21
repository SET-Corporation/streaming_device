#!/bin/bash

#Gather current directory
workd=$(pwd)

#Check if internet (ping google.com)
printf "checking internet connection ...\n\n"
ping -c 1 google.com
if [ $? = 0 ]
then
  printf "\nconnected to internet\n"
else
  printf "\nplease check your internet connection\n"
  exit 1
fi

#Gather device information
printf "\nplease provide mqtt client ID : "
read mqttid

#Deps
sudo apt update -y
sudo apt upgrade -y
sudo apt autoremove -y
sudo apt install -y --no-install-recommends vlc git java-common

#Create directory for the app
sudo mkdir /app
sudo chmod -R 777 /app

#Maven
cd /app
wget https://dlcdn.apache.org/maven/maven-3/3.8.2/binaries/apache-maven-3.8.2-bin.zip
unzip apache-maven-3.8.2-bin.zip
rm -Rf apache-maven-3.8.2-bin.zip
cd apache-maven-3.8.2/bin
mvndir=$(pwd)
export PATH=$PATH:$mvndir

#JDK Amazon Corretto 11 (ARM)
cd /app
wget https://corretto.aws/downloads/resources/11.0.12.7.1/amazon-corretto-11.0.12.7.1-linux-armv7.tar.gz
tar -xvf amazon-corretto-11.0.12.7.1-linux-armv7.tar.gz
rm -Rf amazon-corretto-11.0.12.7.1-linux-armv7.tar.gz
cd amazon-corretto-11.0.12.7.1-linux-armv7/bin
jdkdir=$(pwd)
export PATH=$PATH:$jdkdir

#Persist path to java and maven
echo "PATH=\"$PATH:$jdkdir/bin:$mvndir/bin\"" | sudo tee -a /etc/profile

#Build and run the project
cd $workd
cd streaming_device
echo "mqtt.user=user
mqtt.password=user
mqtt.hostname=setvideo
mqtt.port=1883
mqtt.clientId=$mqttid
mqtt.topic=set/$mqttid

http.hostname=http://setvideo:\n
" | sudo tee -a ./src/main/resources/application.properties
mvn package -DskipTest
cp ./target/tg.jar /app
java -jar ./target/tg.jar
