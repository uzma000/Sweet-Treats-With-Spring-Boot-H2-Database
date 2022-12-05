# Sweet-Treats-With-Spring-Boot-H2-Database
Created First Spring Boot Application With Local H2 Database


Creat Spring Boot Application with H2 Data base.

1) I initiliazed the spring boot project using spring initiliazer and added the the required dependecies such as "Spring Web", "H2" & "JPA" for local database.

2) I extracted the content of the project and opened it in InteliJ Idea.

3) In this project I have 4 Packages which contains different layers of application and a Main Class to run the application.

4) Inside "entity" package I have two classes (courier & order) which act as a model. The whole class has been added with annotation of @Entity. This time I have also added added another property called "ID" and added an @Id annotation before it so it can be read as a unique property by the application when we run it. This Id will be generated automatically when we add a courier to database.

5) Similar thing has been done it Order class but I haven't created any table for Order in database yet. As I didn't feel the need to save orders yet but this can be implement in future.

6) Next we have a "repository" package, which contains my repository Interfaces for both entities(Courier & Order). Courier repository interface extendes "JpaRepository" Interface which takes two parameteres the entity class and type of it's unique parameter. In this case Courier & Long(which is type of Id).
Jpa Repository has many inbuilt methods to perform the CRUD funtions. This is the Interface which will talk behind the back to our database and provide us the information we need. And we don't have to worry about any functionality of it.

7) Next we have a "service" layer which contains two interfaces and two classes. "Courier Service Implementation" class implements the methods defined in "Courier Service" Interface. "Courier Service Implementation" class has almost most of the logic happening in this application. Firstly here I have my logger to print out logs into a file for me. Next I am "autowiring" the Courier Repository so I don't have to create a new instance of it everytime I use it. Then below it I have all the methods which I am using to Create, Read, Update & Delete the couriers from data base.(CRUD approach)
a) Firstly I have function which checks if all the information regarding a courier is valid before we add it into a database.
b) Then I have a method "addCourier()" which uses inbuilt method of "courier repository" to save the courier in data base. Firstly it will check if the all the provided information is valid then it will add courier to database otherwise it will through an exception.
c) Next I have a method called "getAllCouriers()" which returns a List of all the couriers using inbuilt method of "courier repository".
d) Next I have a method called "findCourier()" which returs a courier on the bases of ID. I have used an optional class here because the it is not always the case the courier with ID exists. If courier with entered ID exists then we will return it else it will through an exception. This method also uses inbuilt methods of "courier repository" interface.
e) Next I have a method "isAvailable()" which checks if a courier is available or not and it returns boolean.
f) After this I have the most important method of this project "getBestSuitableCourier()" which checks and returns us the cheapest courier. This method has only minor change as compare to previous project that it gets the list of couriers from my H2 Database.
g) Next I have a method to update the information of an existing courier in the database. "updateCourierDetails()" takes courier ID and new details as parameters. It also verifies if the courier with the provided ID exists in the database and the also validates the provided new information. If everything is valid and a courier with provided ID exists then it will update the courier's information using getter() & setter() methods.
H) At the end I have a method "deleteCourier()" which takes a parameter of ID. Then it checks if the courier is present in database, it will be deleted else it will show an exception.


8) Next in the "web" layer of application it has a class "Controller" which controles the web side the of app. It mostly using the methods in service layer and responds to different end points on the basis of methods present in the class. This controller is annotated with @RestController.
Here I have imported Order & Coureir service and autowired them using @Autowired annotation.
Then I have all the methods with "@RequestMapping()", the path they are mapping to "path = "/courier"" and the request type we are sending, "method = RequestMethod.POST". 
In the method signature I have also defined if the method takes any payload. In addcourier() method it is taking a new courier details as a payload.
In the method signature it is also defined what response is expected after hitting the endpoint of the method. In addcourier() method it is returning the Response Entity of a courier with it's details.
If the request is successful it will return a HTTP response code of success("200") otherwise it will return a HTTP response code of bad request("400") and ResponseStatusException with the error message.

9) In the "getCourier()" with Id method it takes a path variable of "id". It will search for the courier on the basis of the id provided in the path variable. I am using it in a TRY CATCH block. First it will try to find the courier on the basis of id provided if none found then it will through an exception with appropriate HTTP Response code.

10) All below methods follow almost same approach.

11) If I go to "resources" floder, Inside this I have a file "application.properties" which holds all the information to connect to H2 database. And I also have an "sql.data" file which has sql queries to insert the data into local database when I run the application.

Now I will run my application and send appropriate requests using postman.
