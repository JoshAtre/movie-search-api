terraform {
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

  network = var.network
  subnetwork = var.subnetwork

  cluster_autoscaling {
    enabled = true

    resource_limits {
      resource_type = "cpu"
      minimum = 0
      maximum = 2
    }
  }
}

resource "google_service_account" "workload_identity_user_sa" {
  account_id = "terraform-service-account"
  display_name = "Service account for Workload Identity Federation"
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
