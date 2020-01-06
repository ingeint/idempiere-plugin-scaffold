projectName=$(shell ./gradlew -q projectName)

plugin: install
	@ ./build/install/$(projectName)/bin/$(projectName)

test:
	@ ./gradlew clean test
	@ rm -f settings.properties

install:
	@ ./gradlew -q clean installDist

name:
	@ ./gradlew -q projectName
