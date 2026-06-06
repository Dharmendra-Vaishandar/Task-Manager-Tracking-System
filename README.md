# Task Tracker Management System

## Overview

Task Tracker Management System is a production-ready backend application built using Java, 
Spring Boot, Maven, and MySQL. The system enables organizations and teams to manage 
projects, tasks, collaboration, file attachments, comments, and real-time notifications.

The application provides secure JWT-based authentication, role-based access control, 
team/project collaboration features, task assignment, commenting, attachment management, 
and WebSocket-based notifications.

---
# Technology Stack

## Backend

* Java 25
* Spring Boot 4.0.6
* Spring Security
* Spring Data JPA
* Spring Validation
* Spring Web
* Spring WebSocket
* JWT Authentication
* Lombok
* Maven

## Database
* MySQL 8+

## File Storage
* Local File System Storage

## Real-Time Communication
* WebSocket
* STOMP

---

# Features
## Authentication & Authorization
* User Registration
* User Login
* JWT Authentication
* Secure Password Encryption (BCrypt)
* Role-Based Authorization
* Profile Management
* Change Password
* Logout

---
## User Management
* View Profile
* Update Profile
* Search Users
* Role Management
* User Details Retrieval

---
## Team Management
* Create Team
* Update Team
* Delete Team
* Invite Members
* Join Team
* View Team Members
* Team Ownership

---
## Project Management
* Create Project
* Update Project
* Delete Project
* Assign Members
* View Projects by Team
* Project Ownership

---
## Task Management
* Create Task
* Update Task
* Delete Task
* Assign Task
* Change Status
* Mark Complete
* Search Tasks
* Filter Tasks
* Sort Tasks
* Due Date Management

---
## Comments
* Add Comment
* Update Comment
* Delete Comment
* View Task Comments

---
## Attachments
* Upload Files
* Download Files
* Delete Attachments
* View Task Attachments

Supported file types:
* PDF
* DOC
* DOCX
* XLS
* XLSX
* PNG
* JPG
* JPEG

---
## Notifications
* Task Assigned Notification
* Comment Notification
* Task Update Notification
* Real-Time Notifications
* Notification History
* Read/Unread Status