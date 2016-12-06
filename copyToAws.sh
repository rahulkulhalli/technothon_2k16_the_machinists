#!/bin/bash
scp -v -i sentiment_key.pem images/* ubuntu@ec2-54-214-132-138.us-west-2.compute.amazonaws.com:/home/ubuntu/trial_img/
if [ \( $? -eq 0 \) ];
then
    echo "OK "+$?
else
    echo "NOK"+$?
fi
