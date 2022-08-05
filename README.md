# nisum-users-api-challenge

The application works with an H2 in memory that it raises when starting the application from the Hibernate model.

So with running it, it would already be working, it is not necessary to generate any database, just have java and maven installed.


Once running, you can check your endpoints in swagger
In case of running it by default, this would be the uri: http://localhost:8080/swagger-ui/index.html#/

In the application.yml you can configure the following properties:

hash.salt: used for password encryption salt
jwt.secret: keyword to generate the api token with JWT
regex.email : regex used to validate the email.
regex.password: regex used to validate the password

