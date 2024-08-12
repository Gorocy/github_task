# GitHub Repositories API

## Overview

This is a simple Spring Boot application that exposes an API for listing non-forked repositories of a specified GitHub user. The application provides a RESTful endpoint that returns the list of repositories in JSON format.

## Prerequisites

- Java 11 or higher
- Maven 3.x or higher
- A GitHub account (to test the API with valid GitHub usernames)

## Setup and Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/yourusername/github-repositories-api.git
    cd github-repositories-api
    ```

2. **Build the project:**

    ```bash
    mvn clean install
    ```

3. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

## API Endpoints

### List User Repositories

- **URL:** `/api/github/{username}`
- **Method:** `GET`
- **Headers:**
  - `Accept: application/json` (required)
- **Path Parameters:**
  - `username` (required): The GitHub username for which to list repositories.
- **Response:**
  - **200 OK**: Returns a list of non-fork repositories in JSON format.
  - **406 Not Acceptable**: Returned if the `Accept` header is missing or not set to `application/json`.
- **Example Request:**

    ```bash
    curl -H "Accept: application/json" http://localhost:8080/api/github/gorocy
    ```

- **Example Response:**

    ```json
    [
    {"name":"AiSD","owner":"Gorocy","branches":[{"name":"main","commitSha":"3a39a6dd23f143e73a561e55d6bba99a99a93521"}]},
    {"name":"ASD","owner":"Gorocy","branches":[{"name":"main","commitSha":"f7e955ba52b55e66266bcb5a7f68efa385c4792c"}]},
    {"name":"bank_management","owner":"Gorocy","branches":[]}
    ]
    ```

## Project Structure

- **`controller/GitHubController.java`**: The REST controller that handles the `/api/github/{username}` endpoint.
- **`service/GitHubService.java`**: Service class responsible for interacting with GitHub API and filtering non-forked repositories.
- **`response/RepositoryResponse.java`**: Data transfer object (DTO) representing the repository information returned by the API.

## Testing

You can test the API using tools like `curl`, Postman, or any other REST client.


