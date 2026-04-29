# LangFlow

LangFlow is a production-ready web application for language practice through adaptive flashcard quizzes.

## Live Access

- Frontend (Netlify): https://langflow.netlify.app/
- Backend API (Render): https://languageplatformbackend.onrender.com
- API Health Check: https://languageplatformbackend.onrender.com/actuator/health
- Swagger UI: https://languageplatformbackend.onrender.com/swagger-ui/index.html

## Product Scope

- Secure user registration and login (JWT)
- Practice sessions with adaptive flashcard flow
- Quiz submission and score/history tracking
- Role-protected API endpoints

## Technical Architecture

- Frontend: static web app (`FRONTEND/`)
- Backend: Spring Boot REST API (`BACKEND/`)
- Database: MySQL (Aiven)
- Deployment:
	- Frontend on Netlify
	- Backend on Render (Docker)

## Tech Stack

- Java 21
- Spring Boot
- Maven
- MySQL
- JWT Authentication
- Swagger/OpenAPI

## Security & Configuration

The backend expects these environment variables in production:

- `DB_URL` (JDBC URL)
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `APP_CORS_ALLOWED_ORIGINS`

Frontend must target the backend over HTTPS in production.

## Local Development (Developer Handover)

1) Import schema:

```bash
/usr/local/mysql/bin/mysql -u root -p < BACKEND/schema_local.sql
```

2) Configure `BACKEND/.env` (copy from `BACKEND/.env.example`):

```dotenv
DB_URL=jdbc:mysql://localhost:3306/languagelearning?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=your_mysql_password
JWT_SECRET=replace_with_a_long_random_secret_at_least_32_chars
APP_CORS_ALLOWED_ORIGINS=http://localhost:5500,http://127.0.0.1:5500
```

3) Run backend:

```bash
cd BACKEND
./run-local.sh
```

4) Run frontend:

```bash
cd FRONTEND
python3 -m http.server 5500
```

5) Open: http://localhost:5500

## Operational Notes

- On first deployment, import `BACKEND/schema_local.sql` before starting the API.
- If browser auth behaves unexpectedly during local testing, clear local storage keys (`token`, `user`, `userID`, `API_BASE_URL`) and login again.

## Contact

Guilherme Silveira  
gws.silveira@gmail.com
