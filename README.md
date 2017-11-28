# Bidder

## About
A simple implementation of a bidder api. Receives bid requests, picks a campaign and responds back with a bid response.

## Requirements
- JDK 8
- Maven 3.0+ (if you wish not to install maven, instead of the mvn command below, use ./mvnw on Linux or mvnw.cmd on Windows)

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

## Notes
- For the 2nd task, to keep the solution simple i used an in-memory data structure to keep track of the bids per campaign. In a real-world scenario, we should use a persistent data store.
- The Campaigns API is mocked only in integration tests. So, if you run the application and post a bid request, it responds back with 500 Internal Server Error as a ConnectionException will be thrown.


