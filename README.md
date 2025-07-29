# Capsule Monorepo

## Structure

- `backend/` — Kotlin (Ktor or Spring Boot) API
- `frontend/` — React + TypeScript app
- `shared/` — Shared types (TypeScript), docs, scripts

## Getting Started

### Backend
- Navigate to `backend/` and set up a Kotlin project (Ktor recommended for APIs)
- Run the server: `mvn clean install && mvn exec:java`

### Frontend
- Navigate to `frontend/` and run: `npm install && npm start`

### Shared
- Place shared TypeScript types and JSON Schemas here

---

**Next Steps:**
- Scaffold backend and frontend projects
- Define Capsule schema in `shared/`