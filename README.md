# 🌍 LangFlow

**LangFlow** is a full-stack language learning platform designed to help students practice sentence-based flashcards through adaptive quizzes. The backend supports user registration, secure sessions, flashcard management, and quiz result tracking using spaced repetition principles.

This project was developed as the final project of my **Higher Diploma in Computing** at **National College of Ireland (NCI)**. It applies multiple concepts and technologies I’ve learned throughout the course, with an emphasis on real-world backend development and RESTful API design.

---

## 🚀 Features

- ✅ Student registration and login (JWT-secured)
- 📚 Practice sessions using flashcards grouped by difficulty
- 🔁 Spaced repetition logic using ratings (EASY, MEDIUM, HARD, DONT_KNOW)
- 🧠 Quiz submission with score tracking and result history
- 🔍 API documentation via Swagger
- 🔒 Secure endpoints with role-based access (JWT)
- ✅ Tested using Postman

---

## 🛠️ Tech Stack

- **Java 25 + Spring Boot**
- **MySQL (hosted on Aiven)**
- **Maven**
- **JWT Security**
- **Swagger UI**
- **Postman** (for manual testing)

---

## 🎓 Educational Objectives

This project consolidates the following topics covered in the **Higher Diploma in Computing**:

- Object-Oriented Design
- Spring Boot + REST API development
- Database Design & Normalization (SQL & MySQL)
- Authentication and Security using JWT
- Agile methodology and task planning (Trello)
- Manual testing and debugging with Postman
- Deployment practices using Render, Netlify, and Aiven

---

## 🧪 How to Run Locally

### 1) Clone and open the project

```bash
git clone <your-repo-url>
cd LanguagePlatformApplication
```

### 2) Set up MySQL schema (first time only)

Run:

```bash
/usr/local/mysql/bin/mysql -u root -p < BACKEND/schema_local.sql
```

This creates the `languagelearning` database and all required tables.

### 3) Configure local environment variables

Create a local env file in `BACKEND/.env` (this file is gitignored):

```dotenv
DB_URL=jdbc:mysql://localhost:3306/languagelearning?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=your_mysql_password
JWT_SECRET=replace_with_a_long_random_secret_at_least_32_chars
APP_CORS_ALLOWED_ORIGINS=http://localhost:5500,http://127.0.0.1:5500
```

You can copy from `BACKEND/.env.example`.

### 4) Run backend (Spring Boot)

```bash
cd BACKEND
./run-local.sh
```

Backend will run on:

`http://localhost:8080`

Swagger UI:

`http://localhost:8080/swagger-ui/index.html`

### 5) Run frontend

In a second terminal:

```bash
cd FRONTEND
python3 -m http.server 5500
```

Open:

`http://localhost:5500`

> Make sure `FRONTEND/assets/js/api-config.js` points to your backend URL when you deploy. Keep `http://localhost:8080` for local development.

---

## ☁️ Free Deployment Checklist

### Recommended stack

- **Frontend**: Netlify
- **Backend**: Render Web Service
- **Database**: Aiven MySQL

### Teacher-style deployment flow

1. Deploy the backend first so the frontend has a real API URL.
2. Set backend environment variables on Render.
3. Deploy the frontend on Netlify.
4. Point the frontend to the backend URL.
5. Test login, quiz flow, and CORS from the live site.

### 1) Prepare backend environment variables

Set these on your hosting platform:

- `DB_URL` = your hosted MySQL JDBC URL
- `DB_USERNAME` = your database username
- `DB_PASSWORD` = your database password
- `JWT_SECRET` = a long random secret string
- `APP_CORS_ALLOWED_ORIGINS` = your Netlify URL(s), comma-separated

Example:

```text
APP_CORS_ALLOWED_ORIGINS=https://your-site.netlify.app,https://www.your-site.netlify.app
```

For JWT, generate a strong secret locally, for example:

```bash
openssl rand -base64 48
```

### 2) Deploy the backend on Render

Render settings:

- **Option A**: Import the repo and let Render read [render.yaml](render.yaml)
- **Option B**: Create a Web Service manually

If you configure it manually, use:

- **Root directory**: `BACKEND`
- **Runtime**: Docker
- **Dockerfile**: `BACKEND/Dockerfile`
- **Port**: Render provides `PORT` automatically, and the container already reads it

After the first deploy, copy the Render service URL. You will use it in the frontend.

Suggested Render env values:

- `DB_URL` = your Aiven JDBC URL
- `DB_USERNAME` = `avnadmin` or your custom user
- `DB_PASSWORD` = your Aiven password
- `JWT_SECRET` = a long random secret
- `APP_CORS_ALLOWED_ORIGINS` = your Netlify URL(s) plus any local testing origin

### 3) Deploy the frontend on Netlify

The frontend is already configured to read the backend URL from an environment variable.

**Steps:**
1. Connect your GitHub repo to Netlify.
2. Netlify will auto-read [netlify.toml](netlify.toml).
3. Set publish directory to `FRONTEND` (if not auto-detected).
4. Go to **Settings → Build & deploy → Environment → Environment variables**.
5. Add: `API_BASE_URL` = `https://languageplatformbackend.onrender.com` (your Render backend URL)
6. Redeploy.

**How it works:**
- Locally: `FRONTEND/assets/js/main.js` defaults to `http://localhost:8080`
- On Netlify: It reads `API_BASE_URL` from the environment variable and uses that instead

No code changes needed — the frontend automatically picks the right backend based on where it's running.

### 4) Import the database schema

Import [BACKEND/schema_local.sql](BACKEND/schema_local.sql) into your hosted database before the first backend start.

### 5) Final checks

- Backend health endpoint responds with `UP`
- Frontend can log in and call backend APIs
- No CORS errors in the browser console

### 6) Common gotchas

- If login fails from Netlify, check `APP_CORS_ALLOWED_ORIGINS` on Render.
- If the frontend still hits localhost, update the base URL in `FRONTEND/assets/js/api-config.js`.
- If the backend fails to connect, re-check the Aiven JDBC URL, username, password, and SSL settings.

- If registration or login requests return `403` in the browser during local testing, you may have a stale token or API URL saved in the browser `localStorage`. Clear those keys and retry. In the browser console run:

```javascript
localStorage.removeItem('token');
localStorage.removeItem('API_BASE_URL');
localStorage.removeItem('user');
localStorage.removeItem('userID');
```

	The frontend was updated to avoid sending an `Authorization` header to public auth routes (e.g. `/api/auth/register` and `/api/auth/login`), but older stored tokens can still cause the browser to send them — clearing `localStorage` fixes this.

---

## 📫 Contact

**Guilherme Silveira**  
📍 Dublin, Ireland  
📧 gws.silveira@gmail.com  
🔗 [GitHub](https://github.com/SilveiraGuilherme) | [LinkedIn](https://linkedin.com/in/jsguilherme)

---

> 🚧 Frontend version coming soon!
