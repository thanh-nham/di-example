up:
	cd docker && docker-compose up -d

down:
	cd docker && docker-compose down -v

start-spring:
	./gradlew bootRun

migrate:
	java -jar dropwizard/build/libs/dropwizard-example.jar db migrate dropwizard/config.yml

start-dropwizard:
	java -jar dropwizard/build/libs/dropwizard-example.jar server dropwizard/config.yml