# Warehouse Management System - ClothesWareHouse_HSF

A web-based Warehouse Management System built with Spring Boot, using Thymeleaf for front-end rendering and MySQL for database management.

## 📦 Features

- 👤 User authentication and role management
- 📁 Manage products, suppliers, and warehouses
- 📑 Create and track orders
- 📉 Inventory management and reporting
- 📧 Email notification support
- 🧾 Export data to Excel using Apache POI
- 📷 QR code generation (using ZXing)

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.5.3
- Spring MVC & Spring Data JPA
- Thymeleaf
- MySQL
- ZXing (QR code)
- Jakarta Mail (email sending)
- Apache POI (Excel export)
- Maven

🚀 How to Run This Project:

🧱 Requirements:
- Docker & Docker Compose

- Java 17

- Maven (or ./mvnw)

- Git

🐬 Step 1: Set up MySQL with Docker
Start MySQL container:

docker compose up -d

📌 MySQL is exposed on port 3307. Credentials and DB name are defined in docker-compose.yml.

🗃️ Step 2: Import the Database
After the MySQL container is running:

1.Copy the SQL file into the container:

docker cp WareHouseDataBase.sql mysql_db:/WareHouseDataBase.sql

2.Open MySQL terminal inside the container:

docker exec -it mysql_db mysql -u root -p
Password: mystrongpassword

3.Inside MySQL, run:

CREATE DATABASE spring_warehouse;
USE spring_warehouse;
SOURCE /WareHouseDataBase.sql;

🔧 Step 3: Build the Project

- git clone https://github.com/Hiepbq2003/Warehouse-System-SpringBoot.git

- cd Warehouse-System-SpringBoot

- ./mvnw clean package

To run:

java -jar target/ClothesWareHouse_HSF-0.0.1-SNAPSHOT.jar
