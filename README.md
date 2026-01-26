# Mini Sample Management AI Agent

A full-stack application for managing samples with a Spring Boot backend and Angular frontend.

## Project Structure

```
mini_sample_management_AI_agent/
├── backend/          # Spring Boot REST API
│   └── src/
│       └── main/
│           └── java/com/miniproject/slims/
└── frontend/         # Angular application
    └── ui/
        └── src/
            └── app/
```

## Features

- Sample CRUD operations
- Sample filtering by type and status
- Pagination support
- RESTful API backend
- Modern Angular frontend

## Tech Stack

### Backend
- Spring Boot 3.4.2
- Java 17
- Spring Data JPA
- H2 Database (in-memory)

### Frontend
- Angular 21
- TypeScript
- RxJS

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- Node.js 18+
- npm 10+

### Backend Setup

```bash
cd backend
./mvnw spring-boot:run
```

The backend will run on `http://localhost:8080`

### Frontend Setup

```bash
cd frontend/ui
npm install
npm start
```

The frontend will run on `http://localhost:4200`

## API Endpoints

- `GET /api/samples` - List all samples (with pagination, filtering)
- `GET /api/samples/{id}` - Get sample by ID
- `POST /api/samples` - Create a new sample
- `PUT /api/samples/{id}` - Update a sample

## Deployment

This project is configured for deployment on:
- **Frontend**: GitHub Pages
- **Backend**: Railway/Render (via GitHub Actions)

See `.github/workflows/` for deployment configurations.

## License

MIT
