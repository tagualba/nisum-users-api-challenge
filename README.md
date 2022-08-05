# Nisum Users Upi Challenge

The application works with an H2 in memory that it raises when starting the application from the Hibernate model.

So with running it, it would already be working, it is not necessary to generate any database, **just have java and maven installed.**


Once running, you can [**check your endpoints in swagger default url**](https://pages.github.com/)

In the **application.yml** you can configure the following properties:

- **hash.salt:** used for password encryption salt
- **jwt.secret:** keyword to generate the api token with JWT
- **regex.email:** regex used to validate the email.
- **regex.password:** regex used to validate the password


Curls Examples:

Create User:

```
curl --location --request POST 'http://localhost:8080/nisum/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"tagualba",
    "email":"tomas.albnja@mercrad3olibre.com",
    "password":"Holas.4489",
    "phones":[
        {
            "number":"123123",
            "cityCode":"dasdasd",
            "countryCode":"asdasd"
        },
                {
            "number":"123123",
            "cityCode":"dasdasd",
            "countryCode":"asdasd"
        }

    ]
}'
```

Login:

```
curl --location --request GET 'http://localhost:8080/nisum/users/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"tomas.albnja@mercrad3olibre.com",
    "password":"Holas.4489"
}'
```

Check Token:

```
curl --location --request GET 'http://localhost:8080/nisum/users/check-token?token=TOKEN_TEST'
```

Check Last Persisted Token by Email:

```
curl --location --request GET 'http://localhost:8080/nisum/users/check-last-token?email=tomas.albnja@mercrad3olibre.com'
```
