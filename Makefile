gradle=./gradlew -q clean

test:
	@ ./gradlew clean test

install:
	@ ./gradlew -q clean installDist

run: install
	@ ./build/install/$(shell ./gradlew -q projectName)/bin/$(shell ./gradlew -q projectName)
