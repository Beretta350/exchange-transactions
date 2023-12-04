# Exchange Transactions

![Golang](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200.svg?style=for-the-badge&logo=Flyway&logoColor=white)
![Jacoco](https://img.shields.io/badge/Jacoco-F01F7A.svg?style=for-the-badge&logo=Codecov&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)
![Postgres](https://img.shields.io/badge/Postgres-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2%20Database-07405E?style=for-the-badge&logo=Databricks&logoColor=white)

## Overview

This is a rates of exchange transaction REST API for WEX assessment developed using Java with Spring Boot. 
The application facilitates the storage and retrieval of exchange transactions. 
All of it consuming the Treasury Reporting Rates of Exchange API and returning the amount of dollar in the transaction converted in a desired currency.

### Treasury Reporting Rates of Exchange API documentation

```bash
   https://fiscaldata.treasury.gov/datasets/treasury-reporting-rates-exchange/treasury-reporting-rates-of-exchange
```

## Technologies Used

- **Spring Boot:** The core framework for building the application, providing a streamlined and efficient development environment.

- **Gradle:** The build tool used for managing dependencies, building the project, and simplifying the development workflow.

- **Flyway:** Ensures seamless database migration, allowing for smooth updates to the database schema as the application evolves.

- **Mockito:** A powerful mocking framework for testing, facilitating the creation of mock objects to simulate behavior.

- **MockMvc:** A testing framework for Spring MVC applications, enabling the simulation of HTTP requests to test controllers.

- **Lombok:** Enhances code readability and reduces boilerplate code in Java applications.

- **PostgreSQL:** A robust relational database used for storing and retrieving exchange transactions.

- **Jacoco:** A Java code coverage tool that ensures comprehensive test coverage, promoting code quality.

- **H2 Database:** Utilized for testing purposes, providing an in-memory database for running integration tests without external database setup.

## Getting Started

To run the project, follow these steps:

1. Clone the repository.

2. Navigate to the project directory.

3. Build the project using Gradle:

    ```bash
    ./gradlew build
    ```

4. Start the application using Docker Compose:

    ```bash
    docker-compose up -d
    ```

5. The application will be accessible on:

    ```bash
    http://localhost:8080/transaction
    ```
6. Example of purchase:
    - body:
      ```json
      {"amount":23.34, "description":"valid desc","transactionDate": "2023-10-10"}
      ```
    - request:
        ```bash 
          http://localhost:8080/transaction/purchase
       ```
7. Example of retrieve:
   - request:
       ```bash 
         http://localhost:8080/transaction/retrieve?id=2fa8a225-401c-4fd6-9e95-65ed2a05551c&currency=Real
      ```
    
## Testing

The application incorporates thorough testing using Mockito, MockMvc, and H2 database for unit and integration testing. Jacoco is utilized to measure code coverage, ensuring the reliability of the codebase.

Feel free to explore and modify the application to meet your specific needs. If you have any questions or encounter issues, don't hesitate to reach out.

To run all the tests:

 ```bash
  ./gradlew test
 ```
