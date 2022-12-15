# CreditCalculator

CreditCalculator is a web application for calculation a credit.

### Tools

![Java 11](https://img.shields.io/badge/-Java11-blue?style=for-the-badge)
![Spring](https://img.shields.io/badge/-Spring_Web-success?style=for-the-badge)
![Spring](https://img.shields.io/badge/-Spring_Data_JPA-success?style=for-the-badge)
![Spring](https://img.shields.io/badge/-Spring_Security-success?style=for-the-badge)
![Spring](https://img.shields.io/badge/-PostgreSQL-9cf?style=for-the-badge)
![Thymeleaf](https://img.shields.io/badge/-Thymeleaf-yellow?style=for-the-badge)
![Thymeleaf](https://img.shields.io/badge/-Validation-red?style=for-the-badge)

### Installation

1. Clone the repo:

       git clone https://github.com/AskarSariev/CreditCalculator.git:
      
2. Create database:

       name database = credit_calculator_db;
       username = postgres;
       password = 12345;
      
3. Create table in database:

       CREATE TABLE Users (
       
           id BIGSERIAL PRIMARY KEY,
           
           username VARCHAR(255) NOT NULL UNIQUE,
           
           password VARCHAR(255) NOT NULL
           
       );

### Using

Before using the application user should registr. 

![RegistrationPage](C:\Users\Аскар\Downloads\Новая папка (4)\Registration.jpg)



http://localhost:8080/calculator
http://localhost:8080/auth/registration


