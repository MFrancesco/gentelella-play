#!/usr/bin/env bash
PRJNAME=gentelella-play
echo "Launching $PRJNAME with nohup and saving his pin in APP.PID"
nohup ./bin/${PRJNAME} -Dconfig.file=./conf/inmemory.conf -Dpidfile.path=APP.PID &

