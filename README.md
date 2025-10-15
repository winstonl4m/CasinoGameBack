## Mino Casino Assessment Project:

## Full-stack application with React front-end, Spring Boot backend with H2 database (in-memory)
- Make sure both front end and back end are running, and make sure localhost:3000 and localhost:8080 aren’t being used

## Java Back end:
- Used https://start.spring.io/ to create a Spring Boot project with maven
- H2 in-memory database to store Games, Players

## Back end API demo video:
https://www.loom.com/share/95adfe69393f42e9b6f0eb660e2a303f?sid=8e27eb69-89f2-4f41-9f55-d4ffd56c5215 

## Features

- User authentication and registration
- JWT-based security
- Betting system
- Game management
- User profiles
- H2 database integration

## Prerequisites

- Java 17 or higher
- Maven 3.6+

## How to Run
- Clone the repository:
- https://github.com/winstonl4m/CasinoGameBack.git 
- Cd into application folder
- Run mvn spring-boot:run
- Access the application at `http://localhost:8080`
- If localhost:8080/h2-console isn’t populated with the Games table, then copy & paste the schema.sql file into the h2 console, and then copy & paste the data.sql file 

## API Endpoints

`/api/auth - Authentication endpoints
- POST /login 
Takes a username, password and returns a string, user object {user_id, name, username, dateOfBirth, balance=100}, and jwtToken if successful 
- POST /register
Takes a name, username, password, and dateOfBirth (Integer) and returns a string message if successful



`/api/bet - Betting operations
- POST 
Takes an integer bet amount, gameId, validates user based on jwtToken
Retrieves the Game object from H2 database, then randomly generates a value from 0-1, and compares this value to the Game’s winning chances. If user wins, then the user’s balance is updated with balance + game’s winning_multiplier * bet amount. If user loses, then user loses the bet amount, and user’s record in H2 database is updated

/api/game - Game library management
- GET 
Returns the list of all Games in H2 database (16)
- GET /{gameId} 
Retrieves a single Game record in H2 database by id, and returns it

/api/profile - User profile management
- GET 
Authenticates user in H2 database with jwtToken passed as Authorization Bearer header, then returns playerId, name, username, balance to update the auth-state in the React frontend
- PUT
Takes input amount, authenticates user in H2 database with jwtToken passed as Authorization Bearer header, then adds the input amount to the user’s balance. H2 database record is updated, then returns playerId, name, username, updated balance to update the auth-state in the React frontend



## Database

The application uses H2 database. Access the console at:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/casinodb`
- Username: `sa`
- Password: (empty)

## Technologies Used

- Spring Boot 3.5.6
- Spring Security
- Spring Data JPA
- H2 Database
- JWT (JSON Web Tokens)
- Lombok
- Maven


Github repository:
https://github.com/winstonl4m/CasinoGameBack 


