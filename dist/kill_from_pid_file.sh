#!/usr/bin/env bash
if [ -f APP.PID ];
then
    kill -9 `cat APP.PID`
    rm APP.PID
else
    echo "Cannot find pidfile, no project is running"
fi