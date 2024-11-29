# Cinema Management System

The **Cinema Management System** is a web-based platform designed to handle the complete management of a cinema, including users, movies, schedules, and ticket reservations. The system provides different roles—**Admin**, **Manager**, and **Customer**—each with specific functionalities to ensure a seamless cinema experience.

## Features

### Admin:
Admins have the highest level of control and can:
- Manage users, movies, actors, and cinemas.
- Change user roles.
- Add new movies, actors, cinemas, and schedules.
- Delete any resource, including users, movies, or cinemas.
- Perform all actions available to Managers.

### Manager:
Managers handle operational tasks like:
- Adding schedules for movies in cinemas.
- Assigning movies to cinemas.
- Managing auditoriums in cinemas.

### Customer:
Customers can:
- View available schedules and movies.
- Reserve and cancel tickets.
- Manage their accounts, including charging balance and changing passwords.
- View their ticket history.

### Universal Features:
- All users must authenticate and obtain a **Bearer token** to make API requests.

---

## Installation

### Prerequisites:
1. Java 8 or higher.
2. A MySQL database set up.
3. Maven (to manage dependencies).

### Steps:
1. Clone the repository:
```bash
git clone https://github.com/Mehrnaz-Sahebi/Cinema-Management-System.git
```

2. Navigate into the project directory:
```bash
cd movie-reservation-system
```

3. Set up the MySQL database:
- Go to `src/main/resources/application.properties` and configure the database connection. You will need to add your database URL, username, and password.
- Example:
  ```
  spring.datasource.url=jdbc:mysql://localhost:3306/movie_reservation_system
  spring.datasource.username=root
  spring.datasource.password=yourpassword
  ```

4. Build the project using Maven:
```bash
mvn clean install
```

5. Run the application:
```bash
mvn spring-boot:run
```

6. Access the Application
- Use an HTTP client (e.g., Postman) to send requests.

---
## Usage

The following HTTP requests detail how to interact with the **Movie Reservation System** based on user roles.
### Authentication
- **POST**: Authenticate and receive a Bearer token (using `phoneNumber` and `password`):
- Endpoint: `/auth`
- Example request body:
 ```json
 {
   "phoneNumber": "1234567890",
   "password": "yourpassword"
 }
 ```
> **Note**: Include the Bearer token in the `Authorization` header for all subsequent requests:
> ```
> Authorization: Bearer <your_token_here>
> ```

### Sign Up
- **POST**: Register a new user by providing `id`, `firstName`, `lastName`, `email`, and `password`. By default, the user role will be **customer**.
- Endpoint: `/sign-up`
- Example request body:
 ```json
 {
   "id": "user123",
   "firstName": "John",
   "lastName": "Doe",
   "email": "john.doe@example.com",
   "password": "yourpassword"
 }
 ```

> **Note**: After signing up, the user role is set to **customer** by default. This role can be changed:
> - By an **Admin** via the `change-role` endpoint (detailed below).
> - **Manually** in the database by updating the `role` field for the user.

### Admin Requests

- **POST**: *(Add Resources)*   
  - Endpoint: `/admin/add-movie`  
  - Endpoint: `/admin/add-actor`  
  - Endpoint: `/admin/add-cinema`  

- **PUT**: *(Add Actors to Movies and Modify User Roles)*
- - Endpoint: `/admin/add-actor-to-movie/{movieTitle}` 
  - Endpoint: `/admin/change-role/{userPhoneNumber}/{role}`  

- **DELETE**: *(Delete Resources)*  
  - Endpoint: `/admin/delete-user/{phoneNumber}`  
  - Endpoint: `/admin/delete-movie/{movieTitle}`  
  - Endpoint: `/admin/delete-actor/{firstName}/{lastName}`  
  - Endpoint: `/admin/delete-cinema/{cinemaName}`  

### Manager Requests

- **POST**: *(Add Schedules and Resources)*  
  - Endpoint: `/manager/add-schedule`  
  - Endpoint: `/manager/add-auditorium`
  
- **PUT**: *(Modify Cinemas)*
  - Endpoint: `/manager/add-movie-to-cinema/{movieTitle}/{cinemaName}`  
  - Endpoint: `/manager/add-auditorium-to-cinema/{auditoriumName}/{cinemaName}`  

### Customer Requests

- **GET**: *(View Tickets and Schedules)*  
  - Endpoint: `/customer/my-tickets`  
  - Endpoint: `/customer/schedules-for-date`  
  - Endpoint: `/customer/schedules-for-cinema/{cinemaName}`  
  - Endpoint: `/customer/schedules-for-movie/{movieTitle}`  
  - Endpoint: `/customer/all-schedules`  

- **POST**: *(Reserve Tickets)*  
  - Endpoint: `/customer/reserve-ticket/{scheduleId}`  

- **PUT**: *(Manage Account)*  
  - Endpoint: `/customer/change-password/{oldPass}/{newPass}`  
  - Endpoint: `/customer/charge-account/{amount}`  

- **DELETE**: *(Cancel Tickets)*  
  - Endpoint: `/customer/cancel-ticket/{ticketId}`

---

 ## Technologies Used

- **Spring Boot**: The framework used to build the backend REST API.
- **Hibernate**: Used for ORM to map Java objects to MySQL database tables.
- **Spring Security (OAuth2)**: Manages authentication and authorization, including OAuth2 for secure login.
- **MySQL**: The relational database used to store user data, movies, schedules, etc.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Authors  
- **Mehrnaz Sahebi** [GitHub Profile](https://github.com/Mehrnaz-Sahebi)
