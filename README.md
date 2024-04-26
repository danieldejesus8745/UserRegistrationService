# User Registration Service
Service for user registration

# Stack
  - Java
  - Spring Boot
  - PostgreSQL
  - Docker

# How to run
  - Run Postgres database with docker compose
  ``` docker compose up -d ```

  - The API endpoint
    ```http://localhost:8080/api/v1/users```

  - To post a user make a POST request passing user object with name, email and password
  - To get a user make a GET request passing email and password in ```Authentication``` header
