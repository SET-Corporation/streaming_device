#!/bin/bash

#Gather current directory
workd=($pwd)

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
mkdir ./app

#Maven
cd $workd/app
wget https://dlcdn.apache.org/maven/maven-3/3.8.2/binaries/apache-maven-3.8.2-bin.zip
unzip apache-maven-3.8.2-bin.zip
rm -Rf apache-maven-3.8.2-bin.zip
mvndir=$(pwd)/apache-maven-3.8.2/bin
export PATH=$PATH:$mvndir

#JDK Amazon Corretto 11 (ARM)
cd $workd/app
wget https://corretto.aws/downloads/resources/11.0.12.7.1/amazon-corretto-11.0.12.7.1-linux-armv7.tar.gz
tar -xvf amazon-corretto-11.0.12.7.1-linux-armv7.tar.gz
rm -Rf amazon-corretto-11.0.12.7.1-linux-armv7.tar.gz
jdkdir=$(pwd)/amazon-corretto-11.0.12.7.1-linux-armv7/bin
export PATH=$PATH:$jdkdir

#Configure app properties
echo "mqtt.user=user
mqtt.password=user
mqtt.hostname=setvideo
mqtt.port=1883
mqtt.clientId=$mqttid
mqtt.topic=set/$mqttid

http.hostname=http://setvideo:
" | tee -a $workd/src/main/resources/application.properties

#Persist path to java and maven
echo "PATH=\"$PATH:$jdkdir/bin:$mvndir/bin\"" | sudo tee -a /etc/profile

#Build and run the project
cd $workd
mvn package -DskipTest
cp $workd/target/tg.jar $workd/app
java -jar $workd/app/tg.jar
