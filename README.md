# Car Rental Application

# Description
The CarRentals application provides a simple user interface that allows customers to reserve rental cars from any 
existing stores within the parent company portfolio. The application uses MySQL to store information about the 
car inventory and the customer base. Please note that the classes attempt to use techniques outlined by the seminal 
book "Clean Code" by Robert Martin. As a result, the system avoids the type of documentation that does not add to the 
reader (e.g. Accessors and Mutators).

The application uses several object oriented design principles. For example:
1) The application uses the Spring framework to manage the interaction between the user interface and the backend 
logic. Spring uses the Inversion of Control principle.
As a result, the view subscribes to updates within the model. 
2) The application uses interface based programming techniques to reduce coupling across the system. 
3) The Abstract Factory pattern is used to provide an interface for create families of related products. The products 
in this case are car objects.
4) In addition to the Abstract Factory creational pattern, this system uses the Singleton pattern where appropriate. As 
an example, database connection objects should only have a single instance throughout the application.
5) The system also uses the Composite pattern to compose car and store objects into a tree structure. This is important 
because the parent company can own several stores and each store must have an inventory of cars to rent out to 
customers. As a result of the Composite pattern, the system also has a Composite Iterator and NullIterator. The 
Composite Iterator is responsible for iterating over the entire portfolio of stores and cars. The NullIterator is used 
to prevent clients from having to implement Null checks when iterating over the leaf nodes (which are cars in this 
case).
6) The database operations have been extracted into interfaces where possible. This technique allows us to easily 
encapsulate different algorithms for the various database operations and make them interchangeable.

# Usage
This application comes with an executable WAR file that can be downloaded from the target directory.
Follow the following instructions to use the application
1) Download the WAR file and CD into the downloaded directory
2) Run the 'java -jar CarRentals-1.0-SNAPSHOT.war' command
3) Launch a web browser and navigate to http://localhost:8080/? to interact with the application

# System Components
***CarFactory***\
The CarFactory declares an interface common to all car creation classes. Concrete factories must implement this 
interface.

***NorthEastCarSupplier***\
The NorthEastCarSupplier implements the CarFactory interface and creates concrete car objects.

***RentalComponent***\
An interface allowing for the composition of a part-whole tree based hierarchy of rental stores and the cars at each 
store.

***CompositeIterator***\
A CompositeIterator is capable of iterating over an entire rental portfolio (e.g. stores and individual cars).

***NullIterator***\
Used by the leaf nodes within the composite of stores and cars to ensure that clients can avoid implementing null 
checks.

***StoreNames***\
A set of all store name constants within our portfolio.

***StoreLocations***\
A set of location constants used to represent the locations of stores. These stores are also used within the 
Composite pattern and can contain other stores as well as cars.

***RentalStore***\
A Composite structure capable of recursively holding individual store locations and the cars managed by each location.

***CarTypes***\
A set of predefined car type constants. These car types are used by the concrete leaf nodes within the rental 
portfolio. These cars are the leaf nodes within the Composite pattern.

***Sedan***\
Data model for the Sedan car type. Sedan's are leaf nodes within the car rental portfolio. The concrete factories 
from the Abstract Factory pattern are responsible for instantiating any number of concrete Sedans.

***SUV***\
Data model for the SUV car type. Sedan's are leaf nodes within the car rental portfolio. The concrete factories 
from the Abstract Factory pattern are responsible for instantiating any number of concrete SUVs.

***Van***\
Data model for the Van car type. Sedan's are leaf nodes within the car rental portfolio. The concrete factories 
from the Abstract Factory pattern are responsible for instantiating any number of concrete Vans.

***CarUnavailable***\
A simple data model that is in the application when there are no cars available for rent at a given location and time.

***ApplicationConfig***\
The ApplicationConfig class provides the application with access to shared resources such as data constants.

***WebSocketConfig***\
The WebSocketConfig class is solely responsible for configuring and enable WebSocket and STOMP messaging. The purpose
of this class is to create the foundation for an interactive web application that uses message brokers and STOMP 
(simple text oriented messaging protocol that sits on top of a lower level WebSocket)

***ReservationRequest***\
The ReservationRequest class provides a data model representing the customers request to rent a car. The customer 
enters information on a website and submits a request to reserve a car. On the submit, the app.js file appropriately 
routes the details to the ReservationController over a mapping defined with Spring.

***ReservationResponse***\
The ReservationResponse class provides a data model representing the response to the reservation request. The object 
is converted into JSON and sent back to the view. The registered observer extracts necessary information and updates 
the view. In particular, the response tells the view whether or not a car is available to rent given the constraint 
specified by the customer. If a car is available, the observer initiates a call to confirm the reservation.

***ReservationConfirmation***\
The ReservationConfirmation class provides a data model representing reservation confirmation details. The confirmation 
is initiated whenever the ReservationResponse object notifies its observer that a car is available. This class then 
creates a random sequence of characters to represent a confirmation number. The object is then converted into JSON and 
sent back to the view. The registered observer extracts necessary information and updates the view.

***ReservationController***\
The ReservationController class is an annotated class that works with Spring to route STOMP messages. It handles a web 
request and uses the @MessageMapping annotation to route that request to the appropriate destination. In particular, 
it handles the /request and /confirmation routes and subsequently works the the database to perform select and update 
operations. Finally, it use the @SendTo annotation to respond to the client with a message that updates the view.

***CreateConnection***\
Creates a thread safe connection to a SQL database using the Singleton pattern.

***CreateTableStrategy***\
Declares an interface common to all supported database table creation algorithms. Concrete strategies must implement 
this interface.

***CreateInventoryTable***\
Create a SQL table for the inventory of cars if it doesn't already exist. The system checks whether or not this table 
exists at startup because the application depends on a table existing.

***CreateCustomerTable***\
Create a SQL table for our customers if it doesn't already exist. The system checks whether or not this table exists at 
startup because the application depends on a table existing.

***InsertStrategy***\
Declares an interface common to all supported database insert algorithms. Concrete strategies must implement this 
interface.

***InsertInventory***\
Inserts individual cars into a MySQL database. Because the application lets customers request reservations against an 
existing product, the product must first exist. Therefore, ensure that the inventory table within the database has 
a set of cars.

***InsertCustomers***\
Update the database record for the customers who have made a reservation. Customers make reservations after the system 
is running, therefore it is not required to insert until a reservation has been made.

***SelectStrategy***\
Declares an interface common to all supported database Select algorithms. Concrete strategies must implement this 
interface.

***SelectInventory***\
Selects one car that matches the location and car type. If no car is available that matches the customer requirements, 
then an CarUnavailable object is created and used to notify the customer that there are no cars available that match 
the requirements.

If a car is reserved, it can still be available because the existing reservation is in the future. This select 
statement will only find cars that have a reservation start date and time greater than the current customer's request. 
This statement should not allow reservations to be made against a car if the reservation end date and time is equal to 
the current customer's request because the company will perform routine cleaning services before the car can be rented 
again.

***UpdateStrategy***\
Declares an interface common to all supported database update operations algorithms. Concrete strategies must implement 
this interface.

***UpdateInventoryTable***\
Updates the database after the reservation has been confirmed by the customer. Importantly, the database maintains the 
isReserved and isAvailable fields. If a reservation is confirmed by the customer, then the isReserved field will be set 
to true. However, this car can still be available for rent if the reservation date and time are in the future. As a 
result, the system will perform an operation against the reservationStartDateAndTime to before updating the database 
with the correct value.

***CloseConnection***\
A collection of JDBC helper methods to close connections. Utility classes should not have a public or default 
constructor. It is important to close ResultSets, PreparedStatements, Statements, and Connections.

# System Design
The system architecture is based on SOLID principles and core Object Oriented design patterns. Each class within the 
application has a well-defined single-responsibility, which has been highlighted in the System Components section. 
Additionally, the application uses the following patterns:

1) Singleton pattern
2) Abstract Factory pattern
3) Factory methods
4) Composite pattern
5) Iterator pattern
6) Observer pattern
7) Strategy pattern

UML Diagram:

# Extreme Scenarios and Limitations

# Output Analysis


