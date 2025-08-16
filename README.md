# Notes Management Backend

A Spring Boot application for managing notes with authentication, labels, and user management features.

## Deployed version
The application is deployed on Railway and can be accessed at the following URL:
[https://app-production-886a.up.railway.app/swagger-ui/index.html](https://app-production-886a.up.railway.app/swagger-ui/index.html)

## Installation Guide

### Prerequisites

Before you begin, ensure you have the following installed on your system:

- **Java 17** or higher
- **Maven** 3.6+
- **Docker** and **Docker Compose**
- **Git**


### 1. Clone the Repository

```bash
git clone https://github.com/MircoPilcoPeralta/notes-management-backend.git
cd notes-management-backend
```

### 2. Environment Configuration

1. Copy the environment template file:
   ```bash
   cp .env-template .env
   ```

2. Edit the `.env` file according to the following template:
   ```env
   # Name of the database to connect to
   DB_NAME=
   
   # Database username
   DB_USER=
   
   # Database password
   DB_PASSWORD=
   
   # JDBC connection URL for PostgreSQL
   # According to the format: jdbc:postgresql://<host>:<port>/<database_name>
   JDBC_CONNECTION_URL=
   
   # Secret key to generate jwt tokens
   SECRET_KEY=
   ```

### 3. Start the Database

Using Docker Compose, start the PostgreSQL database:

```bash
docker-compose up -d
```

This command will:
- Pull the PostgreSQL 15 image
- Create a database named as the env var `DB_NAME`
- Create a volume for data persistence
- Start the database in detached mode

### 4. Execute the database initial queries
To create all the tables required to start the application, through a PostgreSQL client execute 
the queries in the location: `./script/DDL-v1.sql` and `./script/DDL-v1-index.sql`

### 5. Build and Run the Application

Using Maven, build and run the Spring Boot application, follow the commands below:

```bash
# Build the application
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application will start successfully and you will see logs indicating:
- Spring Boot application startup
- Database connection established
- JPA/Hibernate initialization
- Application running on port 8080

## Accessing the Application

Once the application is running, you can access:

- **API Documentation (Swagger UI)**: http://localhost:8080/swagger-ui/index.html


## Stopping the Application

Press `Ctrl+C` in the terminal where the application is running.

### Stop the Database

Execute the command:

```bash
docker-compose down
```

To remove the data volume you can execute:
```bash
docker-compose down -v
```

## Completed tasks
- [x] Authentication and authorization using JWT
- [x] Users registration
- [x] Users authentication
- [x] Get authenticated user information 
- [x] Creation of notes
- [x] List notes from authenticated user with pagination and filtering
- [x] Obtain a note from authenticated user
- [x] Update a note from authenticated user
- [x] Delete a note from authenticated user
- [x] Creation of labels
- [x] List labels from authenticated user
- [x] Delete a label from authenticated user with option to reassign notes to another label
- [x] Archive a note from authenticated user
- [x] Restore an archived note from authenticated user
