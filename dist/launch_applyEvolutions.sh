#!/usr/bin/env bash
PRJNAME=gentelella-play
echo "Launching $PRJNAME with nohup and saving his pin in APP.PID"
nohup ./bin/${PRJNAME} -Dplay.evolutions.autoApply=true -Dplay.evolutions.autoApplyDowns=true -Dpidfile.path=APP.PID &

