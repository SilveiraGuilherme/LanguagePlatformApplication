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

- **Java 21 + Spring Boot**
- **MySQL (hosted on Azure)**
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
- Deployment practices using Azure

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

> Make sure `FRONTEND/assets/js/api-config.js` uses `http://localhost:8080` as `BASE_URL`.

---

## 📫 Contact

**Guilherme Silveira**  
📍 Dublin, Ireland  
📧 gws.silveira@gmail.com  
🔗 [GitHub](https://github.com/SilveiraGuilherme) | [LinkedIn](https://linkedin.com/in/jsguilherme)

---

> 🚧 Frontend version coming soon!
