# ${{ values.name }}

${{ values.description }}

## Overview

This is an Astro JS static site deployed on OpenShift using Tekton CI and ArgoCD GitOps.

## Development

1. Open in Dev Spaces for a pre-configured development environment
2. Run `npm install` to install dependencies
3. Run `npm run dev` to start the local development server
4. Push changes to trigger the Tekton pipeline

## Deployment

- **Source Repository**: Contains the Astro application code
- **GitOps Repository**: Contains Kubernetes manifests managed by ArgoCD
- **CI Pipeline**: Tekton pipeline builds and pushes the container image
- **CD**: ArgoCD watches the gitops repo and deploys changes automatically
