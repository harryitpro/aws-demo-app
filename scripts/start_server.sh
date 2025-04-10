#!/bin/bash
cd /home/ec2-user/app
nohup java -jar aws-demo-app-1.0-SNAPSHOT.jar > app.log 2>&1 &
echo $! > pid