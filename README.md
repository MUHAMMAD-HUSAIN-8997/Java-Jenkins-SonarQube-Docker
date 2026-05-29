# DevOps CI/CD Pipeline — Java Web Application

A fully automated end-to-end CI/CD pipeline integrating GitHub, Jenkins, SonarQube, and Docker across AWS Ubuntu instances.

---

## Architecture Overview

```
Developer → GitHub → Jenkins (CI) → SonarQube (Analysis) → Docker Server (CD)
```

This pipeline automates the entire software delivery lifecycle — from a developer's `git push` to a live containerised deployment — with built-in code quality enforcement via SonarQube quality gates.

---

## Infrastructure

| Component | Platform | Role |
|---|---|---|
| GitHub | Cloud | Source code, Dockerfile, Jenkinsfile, docker-compose.yml |
| Jenkins | Ubuntu EC2 (AWS) | CI/CD orchestration, pipeline execution |
| SonarQube | Ubuntu EC2 (AWS) | Static code analysis, quality gate enforcement |
| Docker Server | Ubuntu EC2 (AWS) | Container build and deployment host |

---

## Pipeline Flow

### 1. Code Push
Developer pushes Java web application code to the GitHub repository. The repository also contains the `Jenkinsfile` (declarative pipeline), `Dockerfile`, and `docker-compose.yml`.

### 2. SCM Webhook → Jenkins
GitHub fires a webhook to the Jenkins server on every push to the configured branch. Jenkins detects the change via SCM polling or webhook trigger.

### 3. Jenkins Pipeline Execution
Jenkins fetches the Jenkinsfile directly from GitHub and executes the defined pipeline stages:

- **Checkout** — clones the repository
- **Build** — compiles the application using Apache Maven (`mvn clean package`)
- **Test** — runs unit tests as part of the Maven lifecycle

### 4. SonarQube Code Analysis
On successful build, Jenkins dispatches the compiled code to the SonarQube server for static analysis. SonarQube evaluates:

- Code quality and maintainability
- Bugs and code smells
- Security vulnerabilities
- Test coverage thresholds

SonarQube sends results back to Jenkins via its configured webhook.

### 5. Quality Gate Check
Jenkins evaluates the SonarQube quality gate result:

- **Passed** → pipeline proceeds to deployment
- **Failed** → pipeline is aborted; developer is notified

### 6. Docker Deployment (SSH)
On a passing quality gate, Jenkins SSHs into the Docker server and executes the deployment sequence:

```bash
git pull                        # fetch latest Dockerfile and compose.yml
docker compose down             # stop running containers
docker compose build --no-cache # rebuild images from updated source
docker compose up -d            # launch containers in detached mode
```

The application is now live in the updated containerised environment.

---

## Repository Structure

```
├── src/                        # Java web application source
├── Jenkinsfile                 # Declarative pipeline definition
├── Dockerfile                  # Container image build instructions
├── docker-compose.yml          # Multi-container orchestration config
└── pom.xml                     # Maven project and dependency config
```

---

## Prerequisites

- Jenkins with Maven, Git, and SSH plugins configured
- SonarQube server with a project token and webhook pointed at Jenkins
- Jenkins credentials configured for GitHub (SCM) and Docker server (SSH)
- Docker and Docker Compose installed on the deployment server

---

## Key Highlights

- Zero-touch deployment on every successful push
- Quality gate acts as an automated rollback mechanism — broken or low-quality code never reaches production
- All pipeline-as-code artifacts (`Jenkinsfile`, `Dockerfile`, `docker-compose.yml`) are version-controlled in the same repository
- Infrastructure is cloud-native on AWS, keeping each concern on a dedicated server

---

## License

MIT
