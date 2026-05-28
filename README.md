# ☕ Java Learning Web App

A simple task manager built with **Spring Boot**, containerized with **Docker**, and deployed on **AWS EC2**. Perfect for learning the full Java → Docker → Cloud pipeline.

## Tech Stack

| Layer     | Technology                    |
|-----------|-------------------------------|
| Backend   | Java 17, Spring Boot 3.2      |
| Templates | Thymeleaf (server-side HTML)  |
| Styling   | Pure CSS (no frameworks)      |
| Build     | Maven                         |
| Container | Docker (multi-stage build)    |
| Cloud     | AWS EC2 (Amazon Linux 2)      |

## Features

- Add, complete, and delete tasks
- Priority levels (High / Medium / Low)
- Stats dashboard (total, pending, done)
- REST API endpoint at `/api/stats`
- Fully containerized — runs the same everywhere

---

## Running Locally (without Docker)

```bash
# Prerequisites: Java 17+, Maven 3.9+
mvn spring-boot:run
# Open: http://localhost:8080
```

## Running with Docker (locally)

```bash
# Build the image
docker build -t java-webapp .

# Run the container
docker run -d -p 8080:8080 --name java-webapp java-webapp

# Or use Docker Compose:
docker compose up -d

# Open: http://localhost:8080
```

---

## Deploying to AWS EC2

See the step-by-step guide in the README below, or follow the instructions provided with the project.

---

## Project Structure

```
java-webapp/
├── src/
│   └── main/
│       ├── java/com/example/webapp/
│       │   ├── WebAppApplication.java   # Entry point
│       │   ├── controller/
│       │   │   └── TaskController.java  # Routes & logic
│       │   └── model/
│       │       └── Task.java            # Task data model
│       └── resources/
│           ├── templates/
│           │   └── index.html           # Thymeleaf HTML
│           └── static/
│               ├── css/style.css
│               └── js/app.js
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```
