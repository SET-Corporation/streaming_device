#!/bin/bash

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

#Create directory
sudo mkdir /usr/streaming

#Maven
cd /usr/streaming
sudo wget https://dlcdn.apache.org/maven/maven-3/3.8.2/binaries/apache-maven-3.8.2-bin.zip
sudo unzip apache-maven-3.8.2-bin.zip
sudo rm -Rf apache-maven-3.8.2-bin.zip
cd apache-maven-3.8.2
export PATH=$PATH:$(pwd)

#JDK Amazon Corretto 11 (ARM)
cd /usr/streaming
sudo wget https://corretto.aws/downloads/resources/11.0.12.7.1/amazon-corretto-11.0.12.7.1-linux-armv7.tar.gz
sudo tar -xvf amazon-corretto-11.0.12.7.1-linux-armv7.tar.gz
sudo rm -Rf amazon-corretto-11.0.12.7.1-linux-armv7.tar.gz
cd amazon-corretto-11.0.12.7.1-linux-armv7
export PATH=$PATH:$(pwd)

#Persist path to java and maven
echo "PATH=\"$PATH:/usr/streaming/amazon-corretto-11.0.12.7.1-linux-armv7/bin:/usr/streaming/apache-maven-3.8.2/bin\"" | sudo tee -a /etc/profile

#Build and run the project
cd /usr/streaming
sudo git clone https://github.com/SET-Corporation/streaming_device.git
cd streaming_device
echo "mqtt.user=user
mqtt.password=user
mqtt.hostname=setvideo
mqtt.port=1883
mqtt.clientId=$mqttid
mqtt.topic=set/$mqttid

http.hostname=http://setvideo:\n
" | sudo tee -a /usr/streaming/streaming_device/src/main/resources/application.properties
mvn package -DskipTest
java -jar ./target/tg.jar
