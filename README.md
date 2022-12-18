# CreditCalculator

CreditCalculator is a web application for calculation a credit.

### Tools

![Java 11](https://img.shields.io/badge/-Java11-blue?style=plastic&appveyor)
![Spring](https://img.shields.io/badge/-Spring_Web-success?style=plastic&appveyor)
![Spring](https://img.shields.io/badge/-Spring_Data_JPA-success?style=plastic&appveyor)
![Spring](https://img.shields.io/badge/-Spring_Security-success?style=plastic&appveyor)
![Spring](https://img.shields.io/badge/-PostgreSQL-9cf?style=plastic&appveyor)
![Thymeleaf](https://img.shields.io/badge/-Thymeleaf-yellow?style=plastic&appveyor)
![Thymeleaf](https://img.shields.io/badge/-Validation-red?style=plastic&appveyor)

### Getting started

1. Clone the repo:

       git clone https://github.com/AskarSariev/CreditCalculator.git
      
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

1. Before using the application user should registr

   <image src="/images/Registration.jpg" alt="RegistrationPage" width="200" height="100">

2. Enter username, password

   <image src="/images/Auth.jpg" alt="AuthenticationPage" width="200" height="150">

3. Enter data for credit calculation

   <image src="/images/Main.jpg" alt="MainPage" width="300" height="300">

4. Output of the payment schedule

   <image src="/images/Schedule.jpg" alt="SchedulePage">