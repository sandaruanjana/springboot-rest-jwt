# springboot-rest-jwt

Springboot 3 JWT Authentication and Authorization with Spring Security and MySQL Example.

## Run Locally

Clone the project

```bash
  git clone https://github.com/sandaruanjana/springboot-rest-jwt.git
``` 

Go to the project directory

```bash
  cd springboot-rest-jwt
```

Install dependencies

```bash
  mvn install
```

Start the server

```bash
  mvn spring-boot:run
```

## API Reference

#### Signup

```http
  POST /api/v1/auth/signup
```

JSON Body

```json
{
  "username": "string",
  "password": "string"
}
```

#### Login

```http
  POST /api/v1/auth/login
```

Json Body

```json
{
  "username": "string",
  "password": "string"
}
```

#### Refresh Token

```http
  POST /api/v1/auth/refresh
```

Header

```http
  Authorization: Bearer <token>
```

Form Data

```
  refreshToken: string
```
   


## Tech Stack

**Server:** Spring Boot, Spring Security, Spring Data JPA, MySQL, Maven
