projectName=$(shell ./gradlew -q projectName)

plugin: install
	@ ./build/install/$(projectName)/bin/$(projectName)

test:
	@ ./gradlew clean test

install:
	@ ./gradlew -q clean installDist
