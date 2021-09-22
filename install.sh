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
mkdir -p $workd/app/bin

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

#Persist path to java and maven
echo "PATH=\"$PATH:$jdkdir/bin:$mvndir/bin\"" | sudo tee -a /etc/profile

#Configure app properties
echo "mqtt.user=user
mqtt.password=user
mqtt.hostname=setvideo
mqtt.port=1883
mqtt.clientId=$mqttid
mqtt.topic=set/$mqttid

http.hostname=http://setvideo:
" | tee -a $workd/src/main/resources/application.properties

#Create launcher script
echo "java -jar $workd/app/bin/tg.jar" | tee -a $workd/app/streaming.sh
chmod +x $workd/app/streaming.sh

#Configuring the service
echo "[Unit]
      Description=Streaming client
      After=network.target
      After=graphical.target

      [Service]
      Type=simple
      Environment=DISPLAY=:0
      Environment=XAUTHORITY=/home/$USER/.Xauthority
      Restart=always
      RestartSec=5
      ExecStart=/bin/bash $workd/app/streaming.sh

      [Install]
      WantedBy=graphical.target" | sudo tee -a /etc/systemd/system/streaming.service

sudo systemctl daemon-reload
sudo systemctl enable streaming.service

#Erase temp files
find $workd -mindepth 1 ! -regex '^$workd/app\(/.*\)?' -delete

#Build and run the project
cd $workd
mvn package -DskipTest
cp $workd/target/tg.jar $workd/app/bin/
java -jar $workd/app/bin/tg.jar