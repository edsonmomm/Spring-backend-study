#NinjaOne Backend Interview Project

The project is configured to use an in-memory H2 database that is volatile. If you wish to make it maintain data on application shut down, you can change the spring.database.jdbc-url to point at a file like "jdbc:h2:file:/{your file path here}"

##Starting the Application

Run the BackendInterviewProjectApplication class

Go to:
* http://localhost:8080/sample/1
* http://localhost:8080/sample/2

You should see results for both of these. The application is working and connected to the H2 database. 

##H2 Console 

In order to see and interact with your db, access the h2 console in your browser.
After running the application, go to:

http://localhost:8080/h2-console

Enter the information for the url, username, and password in the application.yml:
```
url: jdbc:h2:mem:localdb
username: sa 
password: password
```


You should be able to see a db console now that has the Sample Repository in it.

Type:

```
"SELECT * FROM SAMPLE"
````

Click "Run", you should see two rows, for ids 1 and 2

###Suggestions

Feel free to remove or repurpose the existing Sample Repository, Entity, Controller, and Service. 
