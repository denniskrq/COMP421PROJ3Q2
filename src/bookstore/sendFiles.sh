#!/bin/bash

javac -cp ./postgresql-42.0.0.jre7.jar frontend.java &&
scp frontend.class cs421g02@comp421.cs.mcgill.ca:
