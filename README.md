Todo API

A simple RESTful API for managing Todo tasks, built with Java and Gradle. Supports creating, reading, updating, and deleting tasks via JSON REST endpoints.

Features

Create a new todo task

Retrieve all tasks or a single task by ID

Update existing tasks

Delete tasks

JSON-based REST API

# API Endpoints 

GET /todos – Get all tasks

GET /todos/{id} – Get a task by ID

POST /todos – Create a new task

PUT /todos/{id} – Update an existing task

DELETE /todos/{id} – Delete a task

Request / Response Examples

# Create Todo:

POST /todos
Content-Type: application/json

{
  "title": "Finish Gradle setup",
  "description": "Set up the Todo API project with Gradle",
  "completed": false
}

**Response:**

{
  "id": 1,
  "title": "Finish Gradle setup",
  "description": "Set up the Todo API project with Gradle",
  "completed": false
}

# Get All Todos:

GET /todos

## Response:

[
  {
    "id": 1,
    "title": "Finish Gradle setup",
    "description": "Set up the Todo API project with Gradle",
    "completed": false
  }
]
Running Locally (Gradle)

**Clone the repository:**

git clone https://github.com/username/todo-api.git
cd todo-api

**Build the project:**

./gradlew build

(On Windows: gradlew.bat build)

**Run the application:**

./gradlew bootRun

(On Windows: gradlew.bat bootRun)

The API will be available at http://localhost:8080.
