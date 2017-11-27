# Bidder

## About
A simple implementation of a bidder api. Receives bid requests, picks a campaign and responds back with a bid response.

## Requirements
- JDK 8
- Maven 3.0+ (if you wish not to install maven, use instead of mvn command below, ./mvnw on Linux or mvnw.cmd on Windows)

## Run the code:
In the root project folder run:

### Application

```
mvn spring-boot:run
```

### Tests

```
mvn clean test
```

## Important Note
The Campaigns API is mocked only in integration tests. So, if you run the app and post a bid request, an exception will be thrown.


