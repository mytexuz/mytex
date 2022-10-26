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

### Card To Card

**Method:** POST<br>
**URI:** /v1/samples/sample<br>
**Request body:**<br>
```
{
    "field1": "<value1:String>",
    "field2": "<value2:String>",
    "field3": "<value3:Decimal>",
    "field4": "<value4:String>"
}
```
**Response body:**<br>
```
{
    "response": "<sampleResponse:String>"
}
```
```SAMPLE_ENUM```: UZ, USD

</details>

### Error codes

Error code                                  | Description         | Status code
-----------                                 |---------------------| ------------- 
user.not.found                              | User does not exist | 404
