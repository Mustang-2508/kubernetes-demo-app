# Kubernetes Demo — Spring Boot CRUD App

A simple Spring Boot CRUD application backed by PostgreSQL. Built as a sandbox for experimenting with new concepts.

## Tech Stack

- Java 17
- Spring Boot 3.5.14
- Spring Data JPA + Hibernate
- PostgreSQL
- Lombok

## Project Structure

```
src/main/java/com/myapp/spring_demo/
├── SpringDemoApplication.java
├── controller/
│   └── ProductController.java
├── service/
│   └── ProductService.java
├── repository/
│   └── ProductRepository.java
└── entity/
    └── Product.java
```

## Running on Kubernetes

> Requires: Docker Desktop with Kubernetes enabled (or minikube)

### 1. Build the Docker image

```bash
docker build -t kubernetes-demo-app:latest .
```

> If using **minikube**, load the image into its registry instead:
> ```bash
> minikube image load kubernetes-demo-app:latest
> ```

### 2. Apply all manifests

```bash
kubectl apply -f k8s/
```

This creates (in dependency order — Kubernetes handles it):
- `postgres-secret` — DB credentials
- `postgres-pvc` — persistent storage for Postgres
- `postgres` Deployment + `postgres-service` — Postgres pod reachable internally at `postgres-service:5432`
- `spring-demo` Deployment + `spring-demo-service` — app exposed on NodePort `30080`

### 3. Check everything is running

```bash
kubectl get pods
kubectl get services
```

Wait until both pods show `Running`.

### 4. Access the app

- **Docker Desktop K8s:** `http://localhost:30080`
- **Minikube:** `minikube service spring-demo-service --url`

### Tear down

```bash
kubectl delete -f k8s/
```

---

## Running Locally (Docker Compose)

### 1. Start PostgreSQL via Docker

```bash
docker-compose up -d
```

### 2. Start the Spring Boot app

```bash
./mvnw spring-boot:run
```

App starts on **http://localhost:8080**

### Stop / Tear down

```bash
# Stop the container (keeps data)
docker-compose down

# Stop and delete all data (volume)
docker-compose down -v
```

---

## API Endpoints & cURL Commands

> Base URL: `http://localhost:8080` (local) or `http://localhost:30080` (Kubernetes)

### Get All Products

```bash
curl -X GET http://localhost:8080/api/products
```

---

### Get Product by ID

```bash
curl -X GET http://localhost:8080/api/products/1
```

---

### Create a Product

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "description": "A powerful laptop",
    "price": 999.99
  }'
```

---

### Update a Product

```bash
curl -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Gaming Laptop",
    "description": "An even more powerful laptop",
    "price": 1299.99
  }'
```

---

### Delete a Product

```bash
curl -X DELETE http://localhost:8080/api/products/1
```

---

## Product JSON Schema

```json
{
  "id":          1,
  "name":        "Laptop",
  "description": "A powerful laptop",
  "price":       999.99
}
```

`id` is auto-generated. `name` and `price` are required. `description` is optional.

## HTTP Response Codes

| Operation | Success Code |
|-----------|-------------|
| GET all   | `200 OK` |
| GET by id | `200 OK` |
| POST      | `201 Created` |
| PUT       | `200 OK` |
| DELETE    | `204 No Content` |
