# Spring boot emails

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

```shell
mvn spring-boot:run
```
## Sending emails

You can send emails as follows:

```shell
Post 0.0.0.0:8081/api/v1/send-email
Payload:
{
    "to": "",
    "subject": "Test email",
    "message": "This is a test email",
    "file":"file" // This is optional
    
}

```

