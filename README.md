# User Authentication API
API of system UserH with java and springboot

## Docker

1)Run `docker pull tutum/mongodb` to download mongodb image.
 
2)Run `docker run -d --name mongo -p 27017:27017 -p 28017:28017 -e AUTH=no tutum/mongodb` Criate MongoDb database without password.

3)Run `docker start mongo` Start mongodb image.

4)Run `./mvnw install dockerfile:build` to create application image. Build execute junit tests, create jar and docker image.

5)Run `docker run -p 8080:8080 -t userh` to start application docker image.

6)To access api: http://localhost:8080/login with to login:
{
	"email": "het1@hotmail.com",
	"senha": "1234"
}

7)After logon, you will have to use your token: 
ex: 
key:Authorization
value:Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoZXQxQGhvdG1haWwuY29tIiwiZXhwIjoxNTQ1OTUxMjIzfQ.q53CZKNvsBh7z7ttStWME7PA4HYHU5Ud1umATrD0SYepmCQCTt5jaoQwP5sRwEP-oXtYXqWAlI6kRNcz4uTN4A

-GET -> localhost:8080/users (Users list from DB)

-GET -> localhost:8080/users/{id} (Find specific user by ID)

-POST -> localhost:8080/users (Insert new user)

-PUT -> localhost:8080/users/{id} (Update user by ID)

-DELETE -> localhost:8080/users/{id} (Delete user by ID)

-GET -> localhost:8080/users/page?name=PARAM1&email=PARAM2&orderBy=PARAM3&direction=PARAM4 (Paginated search)

