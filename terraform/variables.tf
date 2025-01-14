variable "project" {
  type = string
  default = "service-project-dev-1a"
}

variable "region" {
  type = string
  default = "us-west2"
}

variable "zone" {
  type = string
  default = "us-west2-b"
}

variable "network" {
  type = string
  default = "josh-vpc"
}

variable "subnetwork" {
  type = string
  default = "us-west2-subnet"
}
