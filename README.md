##NinjaOne Backend Interview Project

The project is configured to use an in-memory H2 database that is volatile. If you wish to make it maintain data on application shut down, you can change the spring.database.jdbc-url to point at a file like "jdbc:h2:file:/{your file path here}"

In order to see and interact with your db, access the h2 console in your browser.
After running the application, go to:

http://localhost:8080/h2-console

Enter the information for the url, username, and password in the application.yml.
You should be able to see a db console now that has the Sample Repository in it. 

Feel free to remove or repurpose the existing Sample Repository, Entity, Controller, and Service. 

