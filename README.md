🏘️ NextDoorPost – Community Engagement Platform

NextDoorPost is a web-based community platform designed to connect local residents through announcements, events, and updates. Built with Spring Boot, Thymeleaf, and CSS, it enables efficient communication while maintaining role-based access for secure content management.

🚀 Overview

NextDoorPost provides a central place for community members to stay informed about local activities and initiatives. Administrators can post and manage announcements or events, while a Super Admin oversees the entire platform. The system emphasizes simplicity, responsiveness, and secure access management.

🧩 Features

🏠 Home Page: Clean landing page with links to announcements, events, and gallery.

📰 Announcements: Display the latest community announcements with images and dates.

🎉 Events: Show upcoming community events, including volunteer opportunities.

🖼️ Gallery: View uploaded community images.

🧾 Submit Event Form: Allow admins or authorized users to submit new event details.

🔐 Role-Based Authentication: Super Admin and Admin access with restricted permissions.


👥 User Roles
🦸‍♂️ Super Admin

Automatically created when the application starts.

Can add, view, or remove admin users.

Has full control over system configuration and user management.

🧑‍💻 Admin

Can view, approve, or delete submitted announcements and events.

Cannot create or remove other admins.

Focused on content moderation and community updates.


⚙️ Technologies Used

| Layer      | Technology                                  |
| ---------- | ------------------------------------------- |
| Frontend   | Thymeleaf, HTML5, CSS3                      |
| Backend    | Spring Boot, Spring MVC                     |
| Security   | Spring Security (Role-Based Access Control) |
| Database   | H2 / PostgreSQL (configurable)              |
| Build Tool | Maven                                       |
| Language   | Java 17+                                    |

🧱 Project Structure

NextDoorPost/
│
├── src/
│   ├── main/
│   │   ├── java/za/ac/nextdoorpost/NextDoorPost/
│   │   │   ├── controller/CommunityController.java
│   │   │   ├── controller/AdminController.java
│   │   │   ├── controller/SuperAdminController.java
│   │   │   ├── config/SecurityConfig.java
│   │   │   ├── model/User.java, Role.java
│   │   │   ├── service/UserService.java
│   │   │   ├── repository/UserRepository.java
│   │   │   └── DataInitializer.java
│   │   ├── resources/
│   │   │   ├── templates/ (Thymeleaf pages)
│   │   │   │   ├── home.html
│   │   │   │   ├── announcements.html
│   │   │   │   ├── events.html
│   │   │   │   ├── gallery.html
│   │   │   │   ├── submit-event.html
│   │   │   │   ├── super-admin-dashboard.html
│   │   │   │   ├── admin-dashboard.html
│   │   │   └── static/css/
│   │   │       ├── style.css
│   │   │       └── admincss.css
│   │   └── application.properties
│   └── test/
│
└── pom.xml


🔒 Authentication Flow

Super Admin Auto-Creation

When the app starts, a default Super Admin account is created via DataInitializer.java.

Login

Super Admin and Admin users log in via /admin/login.

Access Control

Super Admin: /super-admin/dashboard

Admin: /admin/dashboard

Public: /, /events, /announcements, /gallery

Logout

Users can log out using the logout button on their dashboard.

🧑‍💼 Super Admin Dashboard: Manage admin accounts, add new admins, and oversee platform activity.

🧰 Admin Dashboard: Approve, reject, or delete submitted events and announcements.


🧠 How to Run

Clone the repository : git clone https://github.com/Mxolisi2285/NextDoorPost.git

Open the project in your IDE (IntelliJ or VS Code).

Configure your database connection in application.properties.

Build and run the app: mvn spring-boot:run
Access the application at:
http://localhost:8080

Default Super Admin credentials (for testing): Email: superadmin@nextdoorpost.com
Password: admin123


🧭 Future Enhancements
📸 Add image upload feature for announcements and gallery.

🔔 Implement email notifications for approved events.

🧾 Add pagination and search filters for events/announcements.

📱 Improve mobile responsiveness with advanced CSS grid and media queries.

🤖 Integrate AI-based recommendation system for events (future goal).

👨🏽‍💻 Author

Mxolisi Masina
Tshwane University of Technology
Full-Stack Developer | Java & Web Technologies
