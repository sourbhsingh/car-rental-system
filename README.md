# 🚗 Ridevia – Rent. Ride. Repeat.

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring--Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-blue.svg)](https://www.thymeleaf.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> A full-featured **Car Rental Management System** where users can rent cars, return them, make payments, and admins can manage everything — all through a modern web interface built with Spring Boot & Thymeleaf.

---

## ✨ Features

- 🔐 Role-based login (Admin & User)
- 📸 Upload and manage multiple car images
- 📅 Book cars for hours or days
- 💳 Dynamic bill generation + payment simulation
- 🧾 View past booking history
- 📂 Image storage in file system
- 🛠️ Admin dashboard: manage users, bookings, and cars

---

## 🛠 Tech Stack

| Layer          | Tools Used                               |
|----------------|--------------------------------------------|
| **Backend**    | Java 17, Spring Boot 3, Spring Security   |
| **Frontend**   | Thymeleaf, HTML5, CSS3, JavaScript        |
| **ORM / DB**   | Hibernate, Spring Data JPA, MySQL         |
| **Templating** | Thymeleaf                                 |
| **Storage**    | Local file system (`/uploads/`)           |
| **Build Tool** | Maven                                     |

---

## 🧑‍💻 How to Run Locally

### ✅ Prerequisites

- Java 17+
- Maven 3.6+
- MySQL (running)
- IDE (e.g., IntelliJ IDEA, Eclipse)

---

### ⚙️ Step-by-Step Setup


# 1. Clone the repository
git clone https://github.com/sourbhsingh/car-rental-system.git
cd car-rental-system

# 2. Create MySQL Database
Login to MySQL and execute:
CREATE DATABASE ridevia;

# 3. Configure application.properties
## Edit `src/main/resources/application.properties`:
spring.datasource.url=jdbc:mysql://localhost:3306/ridevia
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=10MB
spring.thymeleaf.cache=false

# 4. Run the application
mvn spring-boot:run

### 🔑 Test Login Credentials

| Role  | Email                | Password   |
|-------|----------------------|------------|
| Admin | admin@ridevia.com    | admin123   |  <- config/DataSeeder class  for admin changes
| User  | user@ridevia.com     | user123    |

_You can change these in the DB or via the registration screen._

---

---

## 🔴 Live Demo (UI Preview)

🚀 Try the project here:  
👉 [Ridevia – Live Deployed App](https://car-rental-system-production-7965.up.railway.app/)

---

## 🎥 Project Demo (GIF Preview)

> Here’s a quick look at how Ridevia works:

![Project Demo](project-demo.gif)



### 📂 File Upload Strategy

- All car images are stored in the `/uploads/` directory
- Located **outside the `.jar`** file for persistence after redeployments
- Accessible directly through URLs like:  
  `http://localhost:8080/uploads/car_img1.jpg`

---

### 🚀 Future Improvements

- ✅ Email notifications
- ✅ Invoice export (PDF)
- 🌍 Cloud image storage via **AWS S3**
- 📱 Responsive UI using **Bootstrap** or **React**

---

### 📄 License

This project is licensed under the [MIT License](LICENSE).

---

### 🙋‍♂️ Author

**Sourabh Singh**  
👨‍🎓 *B.E. in Computer Science and Engineering*  
🎓 *Chandigarh University*  
🔗 [LinkedIn](https://www.linkedin.com/in/sourbhsingh) | [GitHub](https://github.com/sourbhsingh)

---

> Made with ❤️ using **Java**, **Spring Boot**, and **Thymeleaf**
