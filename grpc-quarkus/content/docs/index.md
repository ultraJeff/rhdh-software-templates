# ${{ values.name }}

${{ values.description }}

## Architecture

This is a Quarkus gRPC microservice that exposes the **${{ values.grpcServiceName }}** gRPC service.

### Ports

| Port | Protocol | Description |
|------|----------|-------------|
| 8080 | HTTP     | Quarkus HTTP server (health checks, metrics) |
| 9000 | gRPC     | gRPC service endpoint |

## Development

### Prerequisites

- JDK 21+
- Maven 3.9+

### Running locally

```bash
mvn quarkus:dev
```

### Building the container image

```bash
mvn package -Dquarkus.container-image.build=true
```

## Deployment

This service is deployed on OpenShift via ArgoCD GitOps. The deployment manifests
are managed in the [`${{ values.name }}-gitops`](https://${{ values.ghHost }}/${{ values.ghOwner }}/${{ values.repoName }}-gitops) repository.
