# Simple Chat application

This is a simple chat application that allows users to send messages to each other in real time using the Play Framework, Twirl templating engine, a SQLite database, and websockets. The users in the application are currently hardcoded and don't require any form of authentication.

### To run

To run with sbt:
```
sbt start
```
Note: `sbt run` works as well, and runs in development mode, but the application is prone to occasional reloads, which prematurely close the websocket.

Alternatively, to run in a docker container:
```
# make sure docker is running locally
sbt docker:publishLocal # builds an image
docker run -it -p 8080:8080 <image_id> # runs in interative mode on port 8080
```

You can then access the landing page at http://localhost:8080/.

### To rebuild / update the datastore

The SQLite database (`conf/resources/db.sqlite`) can be directly queried and modified using the [sqlite3 command-line tool](http://zetcode.com/db/sqlite/tool/). The database schemas and initially-populated data is defined in the SQL script: `conf/resources/create-and-populate-db.sql`.  

### Notes

See `NOTES.md` for notes on the application and the development process.