# RHDH Software Templates

Custom software templates for Red Hat Developer Hub. These are registered in the RHDH catalog via the `all.yaml` Location entity.

## Working Templates

### Astro Application (`astro-app/`)

Scaffolds an Astro JS static site with full CI/CD:
- Source repo + GitOps repo created on GitHub
- PaC (Pipelines-as-Code) triggers Tekton PipelineRuns on push
- ArgoCD Application + AppProject created via Roadie scaffolder plugin
- Image pushed to Quay.io

### Quarkus gRPC Service (`grpc-quarkus/`)

Scaffolds a Quarkus gRPC microservice with full CI/CD:
- Source repo + GitOps repo created on GitHub
- PaC triggers pipeline: git-clone → maven build → buildah → push to Nexus registry → update gitops
- ArgoCD Application + AppProject created via Roadie scaffolder plugin
- gRPC reflection enabled for `grpcurl` testing without proto files
- Image pushed to `registry.ultra.lab` (Nexus Docker hosted)

## ArgoCD Integration Notes

The templates use the Roadie `argocd:create-resources` scaffolder action. Key discoveries:

- **`namespace` parameter** controls `spec.destination.namespace` on the Application AND the AppProject's allowed destinations. Must be set to the app's target namespace (e.g., `${{ parameters.name }}`), NOT `openshift-gitops`.
- **AppProject `clusterResourceWhitelist`** is not set by the Roadie plugin by default. Configure `argocd.projectSettings.clusterResourceWhitelist` in the RHDH app-config to allow cluster-scoped resources like Namespaces.
- **AppProject `destinations`** defaults to only the specified namespace. Configure `argocd.projectSettings.destinations` in the RHDH app-config to allow `namespace: '*'`.
- **`projectName`** must be unique per app. Using `default` fails because the plugin tries to create a project named `default` which already exists.
- **`CreateNamespace=false`** is hardcoded by the plugin and cannot be changed. Include a Namespace manifest in the gitops repo.
- **Cluster-scoped resources** (ClusterRoleBinding) should be converted to namespace-scoped RoleBindings where possible to avoid AppProject permission issues.

## Placeholder Templates

The remaining templates use `debug:log` placeholder steps with TODO comments indicating where real provisioning actions would be wired in.

## Templates

### Join OpenShift Service Mesh

Onboards an existing application into an OpenShift Service Mesh control plane. You specify the app namespace, which mesh control plane to join (defaults to `istio-system`), the mTLS mode (strict or permissive), whether sidecar injection is automatic or manual, and optionally expose the service via an Ingress Gateway with a VirtualService. It also offers circuit breaker and rate limiting options. The real implementation would create `ServiceMeshMember` CRs, `PeerAuthentication` policies, and optionally `Gateway`/`VirtualService` resources.

### Camel Integration Adapter

Scaffolds a Camel Quarkus integration project that bridges two systems. You pick the source protocol (REST, SOAP, Kafka, AMQP, FTP, etc.) and target protocol, the data format (JSON, XML, CSV, Avro), and an error handling strategy (dead-letter-queue, retry-with-backoff, circuit-breaker, or log-and-continue). The real implementation would generate a Camel Quarkus project skeleton with the appropriate route, publish it to a Git repo, register it in the Backstage catalog, and create ArgoCD deployment resources.

### Multi-Cloud Application Launch

Deploys a containerized application across multiple cloud providers simultaneously. You select which clouds (AWS, Azure, GCP via checkboxes), a primary region, the container image, replicas per cloud, CPU/memory requests, and a load balancing strategy (geo-proximity, round-robin, weighted, or failover). It can also set up global DNS. The real implementation would use Crossplane or Terraform to provision infrastructure, generate Kubernetes manifests per cloud, and create ArgoCD ApplicationSets.

### Spin Up EC2 Instance

Provisions an AWS EC2 instance. You configure the instance type (from t3.micro up to r5.large), the OS (Amazon Linux 2023, RHEL 9, Ubuntu 24.04, or Windows Server 2022), AWS region, VPC/subnet, public IP assignment, EBS storage size and type (gp3, gp2, io1, etc.), SSH key pair, IAM role, and environment/cost-center tags. The real implementation would apply Terraform or a Crossplane Claim and register the instance as a Backstage Resource entity.

### Spin Up S3 Bucket

Provisions an AWS S3 bucket with full configuration options: access control (private, public-read), block public access toggle, predefined bucket policies (cross-account read-only, CloudFront origin, VPC endpoint only), versioning, encryption (AES-256 or KMS with optional custom key), cross-region replication, lifecycle rules (Glacier transition and expiration), and access logging. The real implementation would use Terraform or Crossplane and register the bucket as a Backstage Resource entity.
