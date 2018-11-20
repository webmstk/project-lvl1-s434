.DEFAULT_GOAL := build-run

clean:
	./mvnw clean

build: clean lint
	./mvnw package

run:
	java -jar ./target/casino-1.0-SNAPSHOT-jar-with-dependencies.jar

build-run: clean build run

update: update-properties update-plugins

update-properties:
	./mvnw versions:update-properties

update-plugins:
	./mvnw versions:display-plugin-updates

lint:
	./mvnw spotbugs:check
	./mvnw pmd:check
#	./mvnw verify

checkstyle:
	./mvnw checkstyle:checkstyle


# "чистая" сборка

#.DEFAULT_GOAL := build-run
#
#build: compile
#	jar cfe ./target/casino.jar games.Slot -C ./target/classes .
#
#compile: clean
#	mkdir -p ./target/classes
#	javac -d ./target/classes ./src/main/java/games/Slot.java
#
#run:
#	java -jar ./target/casino.jar
#
#clean:
#	rm -rf ./target
#
#build-run: build run
