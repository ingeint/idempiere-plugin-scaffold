gradle=./gradlew -q clean
projectName=$(shell ./gradlew -q projectName)

test:
	@ ./gradlew clean test

install:
	@ ./gradlew -q clean installDist

run: install
	@ ./build/install/$(projectName)/bin/$(projectName)
