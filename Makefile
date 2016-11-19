.PHONY: dist

all:
	./bin/activator run
debug:
	sbt -jvm-debug 9999 run
cleandb:
	rm -fr db/*
dist:
	./bin/activator clean dist
