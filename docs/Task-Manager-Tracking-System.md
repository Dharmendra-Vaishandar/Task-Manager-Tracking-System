Application URL:

``` text
http://localhost:8080
```

---

# Authentication APIs

## Register

```http
POST /api/v1/auth/register
```

Request:
``` json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "Password@123"
}
```
---
## Login

```http
POST /api/v1/auth/login
```

Request:

``` json
{
  "email": "john@example.com",
  "password": "Password@123"
}
```

Response:
``` json
{
  "token": "JWT_TOKEN"
}
```
---
# Team APIs

## Create Team

```http
POST /api/v1/teams
```

---

## Invite Member

```http
POST /api/v1/teams/{teamId}/invite
```

---

## Join Team

```http
POST /api/v1/teams/{teamId}/join
```

---

# Project APIs

## Create Project

```http
POST /api/v1/projects
```
---

## Add Project Member

```http
POST /api/v1/projects/{projectId}/members
```

---

# Task APIs

## Create Task

```http
POST /api/v1/tasks
```

---

## Assign Task

```http
PUT /api/v1/tasks/{taskId}/assign
```

---

## Complete Task

```http
PUT /api/v1/tasks/{taskId}/complete
```

---

# Comment APIs

## Add Comment

```http
POST /api/v1/tasks/{taskId}/comments
```

---

## Get Comments

```http
GET /api/v1/tasks/{taskId}/comments
```

---

# Attachment APIs

## Upload Attachment

```http
POST /api/v1/tasks/{taskId}/attachments
```

## Download Attachment

``` http
GET /api/v1/tasks/attachments/{attachmentId}/download
```

---

# Notification APIs

## Get Notifications

```http
GET /api/v1/notifications
```

---

## Mark Notification Read

```http
PUT /api/v1/notifications/{notificationId}/read
```

---

# Security

## JWT Authentication Header

```http
Authorization: Bearer <JWT_TOKEN>
```

All secured APIs require a valid JWT token.
