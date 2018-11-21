.DEFAULT_GOAL := build-run

clean:
	./mvnw clean

build: clean lint
	./mvnw package

run:
	java -jar ./target/casino-1.0-SNAPSHOT-jar-with-dependencies.jar

build-run: clean build run

update:
	./mvnw versions:update-properties versions:display-plugin-updates

lint:
	./mvnw spotbugs:check pmd:check

checkstyle:
	./mvnw checkstyle:checkstyle
