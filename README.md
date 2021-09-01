[![Codacy Badge](https://app.codacy.com/project/badge/Grade/cbc6811f037e436196abbafc36b09a62)](https://www.codacy.com/gh/OlegChistyakov/voting/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=OlegChistyakov/voting&amp;utm_campaign=Badge_Grade)
[![Build Status](https://app.travis-ci.com/OlegChistyakov/voting.svg?branch=master)](https://app.travis-ci.com/OlegChistyakov/voting)
<h1 align="center">Graduation project "Voting system" </h1>

## The task is:
### Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.
#### Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
  * If it is before 11:00 we assume that he changed his mind.
  * If it is after 11:00 then it is too late, vote can't be changed
* Each restaurant provides a new menu each day.

-----------------------------
## The project is built on a stack:
* Spring Boot
* Spring MVC
* Spring Security
* Spring Data JPA
* H2
* Maven
* Lombok
* JUnit
-----------------------------
## Some curl requests

#### get all restaurants (without menu)
`curl -s http://localhost:8080/api/v1/restaurants`

#### get all restaurants with today menu
`curl -s http://localhost:8080/api/v1/restaurants/with-menu/today`

-----------------------------

## Link to swagger:

#### http://localhost:8080/swagger-ui.html