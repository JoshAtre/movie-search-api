# Cloud Setup for Movie Search API

## Prerequisites
- Google Cloud Platform (GCP) account
- Google Cloud SDK (`gcloud`)
- Terraform 1.10.4+
- Helm 3+
- Docker

## Steps
1. **Set Up GCP**
   - Create a project and enable GKE and Artifact Registry APIs.
   - Configure Workload Identity Federation (see Terraform section below).

2. **Terraform GKE Cluster**
   ```hcl
   provider "google" {
     project = "<your-project-id>"
     region  = "us-west2"
   }
   resource "google_container_cluster" "primary" {
     name     = "cluster1"
     location = "us-west2-b"
     initial_node_count = 2
     # Add Workload Identity config...
   }
   ```
   Run `terraform init` and `terraform apply`.

3. **Push Docker Image to GAR**
   ```bash
   docker build -t us-west2-docker.pkg.dev/<project-id>/my-repository/movies:latest .
   docker push us-west2-docker.pkg.dev/<project-id>/my-repository/movies:latest
   ```

4. **Deploy with Helm**
   ```bash
   helm upgrade --install movies-chart ./movies-chart --set secrets.omdb_api_key=<your-api-key>
   ```

5. **CI/CD with GitHub Actions** See `.github/workflows/terraform.yml` for the full pipeline setup.
