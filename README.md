# Dropwizard Dependency Injection Example

## Requirements

You need :
- JDK 17
- Docker
- Docker-compose
- Make

## Usage

### Start external services

```shell script
make up
```

### Build application


```shell script
./gradlew clean build
```


### Run


Run spring boot application
```shell script
./gradlew bootRun
```

Run dropwizard application

```shell script
java -jar dropwizard/build/libs/dropwizard-example.jar db migrate dropwizard/config.yml
```


```shell script
java -jar dropwizard/build/libs/dropwizard-example.jar server dropwizard/config.yml
```

### Health Check

To see your application enter url `http://localhost:8080`


### Stop external services

```shell script
make down
```

