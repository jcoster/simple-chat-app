# Notes on the Simple Chat application

I really enjoyed building this web application. It allowed me to leverage my experience with server-side web development, explore how to employ websockets for real-time client updates, and dabble in basic UI development - all at once. 

### API Framework

I was aware that a high-level, web-specific language like node.js would probably be best suited for this type of web application. However, given most of my experience has been in backend development, I felt that building it in Scala using the Play framework would best demonstrate my programming abilities and coding style. In addition, the Play framework is lightweight, scalable, and has built-in capabilities around UI development, database access, websockets, and more -- so it's up for the task.

### UI

I used the Play framework's build-in templating engine, Twirl, to build the dynamic views, used jQuery to write concise client-side logic, and used Bootstrap stylesheets to give the application a decent-looking layout that's also mobile-friendly. Just keep in mind that I'm not a frontend developer (though have an interest in learning more) so I may not have followed best-practices in certain areas and my client-side coding style leaves something to be desired.   

### Messaging in Real-Time

I did some research to determine the appropriate way to implement the real-time responsiveness in this web application, and was between AJAX polling and websockets. I opted for websockets once I discovered Play has built-in support for them (backed by Akka streams), and knowing they provide the best responsiveness and just a single, persistent TCP connection. Seeing as I hadn't worked with websockets at all before, I had to reference various documentation, tutorials, and example applications. I wound up getting the idea of using Akka's `MergeHub` and `BroadcastHub` classes from [this open-source example project](https://github.com/playframework/play-scala-chatroom-example) but made sure to understand what I was using before adding the approrpiate code to my controller.

### Datastore

I opted to use SQLite as it's a very lightweight datastore that can be checked into the codebase as a single file to keep the application self-contained. Whether checking the DB into the codebase is the best idea is another question, but I opted for that approach for simplicity.

### Potential Future Work

* TESTS. There isn't a ton of business logic that needs testing so far, but there are still opportunities for unit and integration tests. I've added an example test `MessageSpec` but there's more to do.
* We'd want to add the concepts of authentication, a user session, user registration, account management, etc.
* Allow a user to have a set of contacts (users) they know, not just all other users in the application.
* On a user's homepage, I was hoping to add a number by each of their contacts to denote how many messages they've shared. It wouldn't be that hard but I didn't get around to it.
* I added a few TODOs and FIXMEs to the code already to clean things up and implement things better.
* Lastly, it's worth noting that controllers typically shouldn't be accessing the DAOs directly, but instead through a service layer. Seeing as there's so little business logic at the moment, that service layer didn't make sense, but as the application develops I'd want to add it. 