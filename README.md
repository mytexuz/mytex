# My-tex project

### Project status

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mytexuz_mytex&metric=coverage)](https://sonarcloud.io/summary/new_code?id=mytexuz_mytex)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=mytexuz_mytex&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=mytexuz_mytex)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=mytexuz_mytex&metric=bugs)](https://sonarcloud.io/summary/new_code?id=mytexuz_mytex)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=mytexuz_mytex&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=mytexuz_mytex)

### Used technological stack

![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)

## Table of Contents

- [Introduction](#introduction)
- [How to build & How to run](#how-to-build--how-to-run)
- [Dependencies](#dependencies)
- [Diagrams](#diagrams)
- [API](#api)

## Introduction

Backend application for internal automation of the textiles

## How to build & How to run

```shell script
$ mvn clean install
```

```shell script
$ java -jar my-tex.jar --spring.profiles.active=dev
```

## Dependencies

There are not any third party integrations yet...

## Diagrams

![ERD](docs/diagrams/erdiagram.png)

## API

### Endpoints

<details>
<summary>ENDPOINTS</summary>

### Authentication

**Method:** POST<br>
**URI:** api/v1/auth/login<br>
**Request body:**<br>

```
{
	"usernameOrEmail": "username",
	"password": "********"
}
```

**Response body:**<br>

```
{
   "data":{
      "token":"38f336b646d3c45bae0d41720bb72c4b887d8bf7a176d4d5b80e48adeb7602f0",
      "fullName":"John Doe"
   },
   "message":"Success",
   "timestamp":"09.11.2022 17:16:32"
}
```

#

### Registration

**Method:** POST<br>
**URI:** api/v1/user/registration<br>
**Request body:**<br>

```
{
	"firstName": "firstname",
	"lastName": "lastname",
	"phoneNumber":"+998971234565",
	"photo": "https://mytex.uz/media/38f336b646d3c45bae.jpg",
	"username": "awesome",
	"email": "awesome@asgardia.team"
}
```

**Response body:**<br>

```
{
   "data":{
      "id":7895,
      "firstName":"Lisa",
      "lastName":"Doe",
      "phoneNumber":"+998905640103",
      "photo":"https://mytex.uz/media/38f336b646d3c45bae.jpg",
      "password":"**********",
      "username":"superwoman",
      "email":"john@asgardia.team",
      "status":"PENDING",
      "registeredDate":"09.11.2022 17:16:32"
   },
   "message":"Success",
   "timestamp":"09.11.2022 17:16:32"
}
```

#

### Update user

**Method:** PUT<br>
**URI:** api/v1/user/update<br>
**Request body:**<br>

```
{
    "id": 1243,
	"firstName": "firstname",
	"lastName": "lastname",
	"phoneNumber":"+998971234565",
	"photo": "https://mytex.uz/media/38f336b646d3c45bae.jpg",
	"username": "awesome",
	"email": "awesome@asgardia.team"
}
```

**Response body:**<br>

```
{
   "data":{
      "id":7895,
      "firstName":"Lisa",
      "lastName":"Doe",
      "phoneNumber":"+998905640103",
      "photo":"photo path",
      "password":"dsads3334!",
      "username":"superwoman",
      "email":"john@asgardia.team",
      "status":"PENDING",
      "registeredDate":"09.11.2022 17:16:32"
   },
   "message":"Success",
   "timestamp":"09.11.2022 17:16:32"
}
```

#

### User change status

**Method:** PUT<br>
**URI:** api/v1/user/change-status<br>
**Request body:**<br>

```
{
    "id": 6542,
	"status": "ACTIVE"
}
```

**Response body:**<br>

```
{
	"data": {
		"status": "ACTIVE",
		"userId": 3
	},
	"message": "Muvafaqqiyatli",
	"timestamp": "10.11.2022 18:51:07"
}
```

#

### Get user by his/her identifier

**Method:** GET<br>
**URI:** /api/v1/user/get?id={usereId}<br>
**Request body:**<br>

- Field -> userId

**Response body:**<br>

```
{
   "data":{
      "id":7895,
      "firstName":"Lisa",
      "lastName":"Doe",
      "phoneNumber":"+998905640103",
      "photo":"https://mytex.uz/media/38f336b646d3c45bae.jpg",
      "password":"**********",
      "username":"superwoman",
      "email":"john@asgardia.team",
      "status":"PENDING",
      "registeredDate":"09.11.2022 17:16:32"
   },
   "message":"Success",
   "timestamp":"09.11.2022 17:16:32"
}
```

#

### File upload

**Method:** POST<br>
**URI:** api/v1/media/upload<br>
**Request body:**<br>
*Multipart file*

**Response body:**<br>

```
{
	"data": {
		"filePath": "https://mytex.uz/media/38f336b646d3c45bae.jpg"
	},
	"message": "Success",
	"timestamp": "09.11.2022 17:16:32"
}
```

**ENUMS**

```STATUS```: ACTIVE, DISABLED, PENDING
```LANG```: UZ, RU, EN

</details>

### Error codes

Error code                                  | Description                   | Status code
-----------                                 |-------------------------------| ------------- 
user.not.found                              | User does not exist           | 404
data.not.found                              | Data not found                | 404
invalid.token                               | Invalid token                 | 403
incorrect.password                          | Incorrect password            | 401
success                                     | successfully                  | 200
failed.complete                             | Failed to compelete           | 400
internal.server.error                       | Internal server error         | 500
invalid.data                                | Invalid data                  | 400
username.exists                             | Username exists in the system | 400
email.exists                             | Email exists in the system    | 400
forbidden                                    | You do not have enough privileges to access this resource. Contact with your adiministrator                              |403                               
