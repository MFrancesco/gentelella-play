.PHONY: dist

all:
	activator run
debug:
	sbt -jvm-debug 9999 run
cleandb:
	rm -fr db/*
deploy:
	@echo "Compiling and generating the distribuible code"
	activator clean dist
	@echo "Now will execute the hot_deploy script"
	sh hot_deploy.sh
prepare:
	@echo "Creating a folder scoring/db in home where the db will be saved"
	mkdir -p ~/scoring/db
dist:
	activator clean dist
