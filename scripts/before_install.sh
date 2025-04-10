#!/bin/bash
if [ -f /home/ec2-user/app/pid ]; then
  kill -9 $(cat /home/ec2-user/app/pid) || true
  rm -f /home/ec2-user/app/pid
fi
rm -f /home/ec2-user/app/aws-demo-app-1.0-SNAPSHOT.jar