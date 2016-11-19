#!/usr/bin/env bash
PRJNAME=gentelella-play
echo "Launching $PRJNAME with nohup and saving his pin in APP.PID"
nohup ./bin/${PRJNAME} -Dpidfile.path=APP.PID &

