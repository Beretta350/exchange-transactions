# exchange-transactions
A Rates of Exchange Transaction REST API for WEX assessment

# Exchange Transactions

## Overview

This is a rates of exchange transaction REST API for WEX assessment developed using Java with Spring Boot. 
The application facilitates the storage and retrieval of exchange transactions, adhering to specific requirements. 
The technology stack includes Spring Boot, Flyway, Mockito, MockMvc, Lombok, PostgreSQL, Jacoco, and H2 database for tests.

## Technologies Used

- **Spring Boot:** The core framework for building the application, providing a streamlined and efficient development environment.

- **Gradle:** The build tool used for managing dependencies, building the project, and simplifying the development workflow.

- **Flyway:** Ensures seamless database migration, allowing for smooth updates to the database schema as the application evolves.

- **Mockito:** A powerful mocking framework for testing, facilitating the creation of mock objects to simulate behavior.

- **MockMvc:** A testing framework for Spring MVC applications, enabling the simulation of HTTP requests to test controllers.

- **Lombok:** Enhances code readability and reduces boilerplate code in Java applications.

- **PostgreSQL:** A robust relational database used for storing and retrieving exchange transactions.

- **Jacoco:** A Java code coverage tool that ensures comprehensive test coverage, promoting code quality.

- **H2 Database:** Utilized for testing purposes, providing an in-memory database for running tests without external database setup.

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

Happy coding!
