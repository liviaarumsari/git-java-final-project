# Git Final Project - TRANSPORTATION RESERVATION SYSTEM
This repository is made to complete Final Project Java of GIT Scholarship

## Setup
This repository contains Spring Boot Application with these specifications:
* Maven
* Java 17
* JDK 21
* PostgreSQL
Change database config in `application.yml`

## End User Feature
|                       Feature                      |       Endpoint       |
|:--------------------------------------------------:|:--------------------:|
| Signup                                             | POST api/auth/signup |
| Login                                              | POST api/auth/login  |
| Update their profile                               | PUT api/v1/profile   |
| Create an agency                                   | POST api/v1/agency   |
| Add buses to the agency                            | POST api/v1/bus      |
| Add trips consisting of predefined stops and buses | POST api/v1/trip     |

## Admin User Feature
|                                 Feature                                 |                                    Endpoint                                     |
|:-----------------------------------------------------------------------:|:-------------------------------------------------------------------------------:|
| Signup                                                                  | POST api/auth/signup                                                            |
| Login                                                                   | POST api/auth/login                                                             |
| List all available stops                                                | GET api/v1/stop                                                                 |
| Search a trip between any two stops and filter search results with date | GET api/v1/trip?sourceStop={sourceStop}&destStop={destStop}&tripDate={tripDate} |
| Book a ticket for a given trip schedule                                 | POST api/v1/ticket                                                              |

## API Documentation
[Swagger](https://git-java-final-project-production.up.railway.app/swagger-ui.html)
