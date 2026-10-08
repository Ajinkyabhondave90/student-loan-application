# Student Loan Desk

Full-stack student loan application manager.

**Stack:** Java 8 · Spring Boot 2.7 (WAR, Tomcat 9 compatible) · Spring Data JPA · PostgreSQL · React 18 (Vite) · HTML/CSS/JavaScript

## Features
- Submit, edit (while pending), approve/reject (with remarks) and delete loan applications
- Server-side validation with field-level error messages
- Automatic EMI calculation (fixed 9.5% a year, change `loan.interest-rate`)
- Dashboard counts and approved amount, plus status filter

## Prerequisites
- JDK 8, Maven 3.6+, Node.js 18+ (Tomcat 9 optional)
- PostgreSQL (local install **or** Docker)

## 1. Database
Option A – Docker (easiest):
```
docker compose up -d
```
Option B – existing PostgreSQL:
```
createdb -U postgres studentloan
```
Credentials default to `postgres` / `postgres`. Edit `backend/src/main/resources/application.properties` if yours differ. Tables are created automatically.

## 2. Backend (port 8080)
```
cd backend
mvn spring-boot:run
```

### Alternative: deploy the WAR on Tomcat 9
```
cd backend
mvn clean package
```
Copy `backend/target/studentloan.war` into `TOMCAT_HOME/webapps/`, start Tomcat, and the API is at
http://localhost:8080/studentloan/api/loans. Then point the frontend at it:
copy `frontend/.env.tomcat.example` to `frontend/.env` before running `npm run dev`.

## 3. Frontend (port 5173)
```
cd frontend
npm install
npm run dev
```
Open http://localhost:5173

In VS Code you can also use **Terminal → Run Task** (Start database, Run backend, Run frontend).

## REST API
| Method | Endpoint | Purpose |
|---|---|---|
| GET | /api/loans?status=PENDING | List (optional filter) |
| GET | /api/loans/stats | Dashboard numbers |
| GET | /api/loans/{id} | Single application |
| POST | /api/loans | Create |
| PUT | /api/loans/{id} | Update (pending only) |
| PATCH | /api/loans/{id}/status | `{ "status": "APPROVED", "remarks": "..." }` |
| DELETE | /api/loans/{id} | Delete |

## Structure
```
backend/   Spring Boot (controller → service → repository → entity, DTO validation, exception handler)
frontend/  React + Vite (App, LoanForm, LoanTable, Stats, api.js)
```
