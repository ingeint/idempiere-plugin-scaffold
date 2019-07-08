projectName=$(shell ./gradlew -q projectName)

run: install
	@ ./build/install/$(projectName)/bin/$(projectName)

test:
	@ ./gradlew clean test

install:
	@ ./gradlew -q clean installDist
