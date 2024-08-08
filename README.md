# 📚 Library Management System
## 📝 Overview 
This project is a simple library system that allow librarians to manage books, patrons, and borrowing records.
## ✨ Features
- **📖 Book Management:** 
    - Add a new book
    - Update book information
    - Delete a book
    - Search for a book


- **👥 Patron Management:**
    - Add a new patron
    - Update patron information
    - Delete a patron
    - Search for a patron


- **📦 Borrowing Management:**
    - Borrow a book
    - Return a book
    - Search for borrowing records

## 🛠️ Technology Stack
- **Backend:** 
  - Java
  - Spring Boot
  - Spring Data JPA
  - H2 Database

## 🚀 Installation and Setup
1. Clone the repository
    ```bash
    git clone git@github.com:Armaritto/LibraryManagementSystem.git
    ```
2. Configure the Database:
    - Open `src/main/resources/application.properties` file
    - Change the `spring.datasource.url` to your desired database location
    - Change the `spring.datasource.username` and `spring.datasource.password` to your database username and password
3. Build the Application:
    ```bash
    mvn clean install
    ```
4. Run the Application:
    ```bash
    mvn spring-boot:run
    ```
   
## 🔗 API Documentation
### Base URL 
```http
http://localhost:8080/api/
```

### Books
- **GET** `/books`
  - Response: JSON Array of books
- **GET** `/books/{id}`
    - Response: JSON Object of the book
- **POST** `/books`
    - Request:
        ```json
        {
            "title": "Book Title",
            "author": "Author Name",
            "publicationYear": 1900,
            "ISBN": "123-4567890",
            "genre": "Programming"
        }
        ```
    - Response: JSON Object of the created book
- **PUT** `/books/{id}`
    - Request:
        ```json
        {
            "title": "Book Title",
            "author": "Author Name",
            "publicationYear": 1900,
            "ISBN": "123-4567890",
            "genre": "Programming"
        }
        ```
    - Response: JSON Object of the updated book
- **DELETE** `/books/{id}`
    - Response: Status 200 OK

### Patrons
- **GET** `/patrons`
    - Response: JSON Array of patrons
- **GET** `/patrons/{id}`
    - Response: JSON Object of the patron
- **POST** `/patrons`
    - Request:
        ```json
        {
            "name": "Patron Name",
            "age": 20,
            "email": "xyz@example.com",
            "phoneNumber": "1234567890",
            "address": "Patron Address"
        }
        ```
    - Response: JSON Object of the created patron
- **PUT** `/patrons/{id}`
    - Request:
        ```json
        {
            "name": "Patron Name",
            "age": 20,
            "email": "xyz@example.com",
            "phoneNumber": "1234567890",
            "address": "Patron Address"
        }
        ```
    - Response: JSON Object of the updated patron
- **DELETE** `/patrons/{id}`
    - Response: Status 200 OK

### Borrowings
- **GET** `/borrow/{bookId}/patron/{patronId}`
    - Response: Status 200 OK

### Returning
- **POST** `/return/{bookId}/patron/{patronId}`
    - Response: Status 200 OK
