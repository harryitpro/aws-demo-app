#!/bin/bash
if [ -f /home/ec2-user/app/pid ]; then
  kill -9 $(cat /home/ec2-user/app/pid) || true
  rm -f /home/ec2-user/app/pid
fi