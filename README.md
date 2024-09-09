# Index
- [Vintage](#collaborator-hours-gap)
- [Prerequisites](#prerequisites)
- [Usage](#usage)

## Vintage
> Application designed for online wine stores. Manage sales, products, and customers.

## Prerequisites
- Java `17`
- Windows `11`
- Docker `27.1.1`

## Usage
At the root of the project, run the following sequence of commands:
- ```./mvnw clean package```
- ```java -jar target/vintage-0.0.1-SNAPSHOT.jar```
- ```docker build --tag=vintage:latest .```
- ```docker run -p 8081:8080 vintage:latest```

For testing, access the API documentation through the URL http://localhost:8080/doc.html






