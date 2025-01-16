terraform {
  backend "gcs" {
    bucket  = "terraform-state-movies"
    prefix  = "prod"
  }

  required_providers {
    google = {
      source = "hashicorp/google"
      version = "6.16.0"
    }
  }
}

provider "google" {
  project = var.project
  region = var.region
}

provider "kubernetes" {
  host = google_container_cluster.primary.endpoint
  token = data.google_client_config.current.access_token
}

data "google_client_config" "current" {}

resource "google_container_cluster" "primary" {
  name     = "cluster1"
  location = var.zone
  remove_default_node_pool = true

  workload_identity_config {
    workload_pool = "${data.google_client_config.current.project}.svc.id.goog"
  }

  initial_node_count = 2

  node_config {
    machine_type = "e2-small"
    disk_size_gb = 10 # Boot size = 10 GB

    workload_metadata_config {
      mode = "GKE_METADATA"
    }
  }

  cluster_autoscaling {
    enabled = false
  }

  network = var.network
  subnetwork = var.subnetwork
}

resource "google_container_node_pool" "default" {
  name       = "default-pool"
  cluster    = google_container_cluster.primary.name
  location   = var.zone
  initial_node_count = 2

  node_config {
    machine_type = "e2-small"
    disk_size_gb = 10
    preemptible  = false
  }

  autoscaling {
    min_node_count = 1
    max_node_count = 2
  }
}

resource "google_service_account" "workload_identity_user_sa" {
  account_id = "terraform-service-account"
  display_name = "Service account for Workload Identity Federation for GKE"
}

resource "google_project_iam_member" "monitoring-role" {
  member  = "serviceAccount:${google_service_account.workload_identity_user_sa.email}"
  project = data.google_client_config.current.project
  role    = "roles/monitoring.viewer"
}

resource "kubernetes_service_account" "ksa" {
  metadata {
    name = "ksa-workload"
    namespace = "default"
    annotations = {
      "iam.gke.io/gcp-service-account" = google_service_account.workload_identity_user_sa.email
    }
  }
}

resource "google_service_account_iam_member" "workload_identity_role" {
  member = "serviceAccount:${data.google_client_config.current.project}.svc.id.goog[default/ksa-workload]"
  role = "roles/iam.workloadIdentityUser"
  service_account_id = google_service_account.workload_identity_user_sa.name
}
