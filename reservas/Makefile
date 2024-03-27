build:
	mvn compile

unit-test:
	mvn test

integration-test:
	mvn test -P integration-test

system-test: start-app
	mvn test -P system-test

performance-test:
	mvn gatling:test -P performance-test

start-app:
	mvn spring-boot:start

docker-start:
	docker compose -f docker-compose.yml up -d

docker-stop:
	docker compose -f docker-compose.yml down