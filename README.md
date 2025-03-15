# Movie Search API

![Java](https://img.shields.io/badge/Java-17-blue)
![GKE](https://img.shields.io/badge/GKE-Kubernetes-green)
![Terraform](https://img.shields.io/badge/Terraform-1.10.4-purple)
![Helm](https://img.shields.io/badge/Helm-3-orange)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-CI/CD-brightgreen)

A web-based application for searching movies by title or actor with relevance-based results, built using Java and deployed to Google Kubernetes Engine (GKE). This project features a RESTful API, infrastructure automation with Terraform, Helm-based deployments, and a CI/CD pipeline via GitHub Actions.

## Features
- **Relevance-based Search**: Search movies by title or actor with optimized results.
- **RESTful API**: Well-designed endpoints for efficient data retrieval from external APIs (e.g., OMDB).
- **Infrastructure as Code**: Automated GKE cluster provisioning using Terraform.
- **Kubernetes Deployment**: Helm charts manage application deployment to GKE.
- **CI/CD Pipeline**: GitHub Actions automates Terraform, Docker builds, and Helm deployments.
- **Security**: Sensitive API keys stored securely using Kubernetes and GitHub secrets.

## Prerequisites
- Java 17 (or later)
- Docker
- A valid [OMDB API key](https://www.omdbapi.com/apikey.aspx)
- (Optional) Google Cloud SDK (`gcloud`) and Terraform for full infrastructure setup

## Local Setup
Since the live deployment is hosted on a private GCP account, you can test the application locally using Docker.

1. **Clone the Repository**
   ```bash
   git clone https://github.com/JoshAtre/movie-search-api.git
   cd movie-search-api
   ```

2. **Build the Application**
   ```bash
   ./gradlew build
   ```

3. **Build the Docker Image**
   ```bash
   docker build -t movies .
   ```
   
5. **Run the Docker Container**, and replace `<your-api-key>` with your OMDB API key
   ```bash
   docker run -e SPRING_PROFILES_ACTIVE=dev -e OMDB_API_KEY=<your-api-key> -p 8080:8080 movies
   ```
   
6. **Test the API** in a separate terminal, query the search endpoint:
   ```bash
   curl -v "http://localhost:8080/search?movieTitle=disciple&maxResults=1"
   ```

## Project Structure
- `src/`: Java source code for the RESTful API.
- `terraform/`: Terraform configuration for GKE cluster setup.
- `movies-chart/`: Helm chart for Kubernetes deployment.
- `.github/workflows/terraform.yml`: GitHub Actions workflow for CI/CD.

## Notes
- The live deployment requires a GCP account and credentials for GKE and Google Artifact Registry (GAR). See [docs/CLOUD_SETUP.md](docs/CLOUD_SETUP.md) for cloud setup details.
- For production deployment, update the movies-chart/values.yaml with your GAR image details and secrets.
- For the full, detailed steps on how I configured Terraform and Helm for this application, see [this Notion page](https://evergreen-lantana-f52.notion.site/Configuring-Terraform-and-Helm-for-Movies-Application-17bcda7cd8a8800abb05f3ad0a92c271?pvs=4).
