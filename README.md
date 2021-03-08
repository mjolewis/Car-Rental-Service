# Vehicle Reservation System
Github Link: https://github.com/mjolewis/Vehicle-Reservation-System

# Project Tasks
#### Provide A Description of The Application Use Case
The Vehicle Reservation System provides a simple user interface to allow customers to reserve vehicles and check the 
details of existing reservations. The system is built around the Model-View-Controller design pattern. The front-end 
uses HTML, CSS, Javascript, and JQuery while the backend and model are built with Java and MySQL, respectively. This 
application also takes advantages of certain features within the Spring framework such as establishing routes between 
the frontend and backend. However, for illustrative purposes, the application does manually performs certain tasks that 
Spring would otherwise handle. For example, the system manually creates database connections and entities. In a 
production application, the system should be refactored to take full advantage of the Spring framework.

Please also note that the classes attempt to use techniques outlined by the seminal book "Clean Code" by Robert Martin. 
As a result, the system avoids the type of documentation that does not add to the reader (e.g. Accessors and Mutators).

#### What Are The Design Goals
The primary design goals are to make the system sufficient, flexible, and maintainable. As a result, the system makes 
an efficiency trade-off because the primary design objectives often require adding additional classes and function call 
overhead. For a more complete description, please see the list below.
1) The application uses interface based programming techniques to reduce coupling across the system. 
2) The Factory Method pattern is used to declare an interface for creating Vehicles. As is typical, the 
VehicleFactoryImpl implements this interface as a Singleton.
3) The Singleton pattern is used throughout the application. In particular, establishing database connections is 
expensive, so the system manages this overhead by ensuring that only a single connection instance is created. 
Additionally, the VehicleFactory is stateless and therefore takes advantage of the Singleton pattern.
4) The Composite pattern is used to compose vehicle and store objects into a tree structure. This is an important 
feature of the application as it simplifies many challenges associated with scaling business operations. For example, 
the company may expand into multiple locations or become a holding company for many other rental services. When this 
happens, the system can easily scale to add the associated stores and vehicles. Additionally, the system also has a 
Composite Iterator and NullIterator. The Composite Iterator is responsible for iterating over the entire portfolio of 
stores and vehicles. The NullIterator is used to prevent clients from having to implement Null checks when iterating 
over the leaf nodes (which are vehicles in this case).
5) The Strategy pattern is used to make database operations interchangeable. This technique allows us to easily 
encapsulate different algorithms for the various database operations in the event the system grows more complex and
needs to implement different CRUD operations.
6) The Data Transfer Object pattern is used to reduce the number of expensive method calls between the frontend and 
backend. The Data Transfer Object can hold all the data necessary to resolve a request from the customer, which is a 
more efficient technique than transferring one unit of data at a time.
7) The Callback Pattern is used by the frontend (app.js) to execute code after certain click events occur. For example, 
a customer can submit a vehicle reservation request by clicking on a button. The button will listen for a click event 
and execute an anonymous callback function whenever that click event occurs.

#### How Flexible Is The System
The design patterns used in this system make it very flexible. For example, the Strategy Pattern makes it very easy to 
alter how certain database operations occur should there ever be a need to extend the business logic. Additionally, the 
Composite Pattern allows the business and system to scale in a flexible manner. Whenever new branches are added, the 
system simply composes the stores and associated vehicles into a tree-like structure that can subsequently be 
maintained in the MySQL database.  

#### How Does The System Avoid Duplicated Code
As mentioned, the system uses abstract base classes where possible to avoid duplicated code. The specialized classes 
provide compile time polymorphism by overriding the abstract base methods wherever necessary.

# How To Compile The Project
The system uses Apache Maven. As a result, you need to install Apache Maven (https://maven.apache.org) on your system.

Type on the command line:
```bash
mvn clean compile
```

# How To Create A Binary Runnable Package
```bash
mvn clean compile assembly:single
```

# How To Run
The system uses JOptionPane to show option dialogs during system start. These option dialogs allow an administrator to, 
if necessary, create system tables, seed database tables, and add more vehicles to stores. As a result, the 
administrator must complete these tasks before the system fully launches.

After the system launches, navigate to http://localhost:8080/ to interact with the reservation system.
```bash
mvn -q clean compile exec:java -Dexec.executable="com.crd.carrental.Main"
```

# Run All Unit Tests
```bash
mvn clean compile test checkstyle:check spotbugs:check
```

# System Components
***WebSocketConfig***\
The WebSocketConfig class is solely responsible for configuring and enabling WebSocket and STOMP messaging. The purpose
of this class is to create the foundation for an interactive web application that uses message brokers and STOMP 
(simple text oriented messaging protocol that sits on top of a lower level WebSocket)

***VehicleCreator***\
The VehicleCreator declares an interface common to all vehicle factories. Concrete factories must implement this 
interface.

***VehicleCreatorImpl***\
The VehicleCreatorImpl implements the VehicleFactory interface and creates concrete vehicle objects. The 
VehicleCreatorImpl is also a Singleton and uses Factory Methods to instantiate the vehicles.

***RentalComponent***\
An interface allowing for the composition of a part-whole tree based hierarchy of rental stores and vehicles.

***CompositeIterator***\
A CompositeIterator is capable of iterating over an entire rental portfolio (e.g. stores and individual vehicles).

***NullIterator***\
Used by the leaf nodes within the composite of stores and vehicles to ensure that clients can avoid implementing null 
checks.

***RentalStore***\
A Composite structure capable of recursively holding individual stores and the vehicles. As the business expands and 
adds new branches, the system must be updated to include these branches and their associated vehicles.

***PriceSheet***\
An enum containing the standard price offerings allowed by the business.

***VehicleClassification***\
An enum containing the standard vehicle classifications allowed by the business (e.g., Sedan, SUV, Van). The system 
allows users to specify the classification of the car that he or she wants to reserve.

***VehicleManufacturer***\
An enum containing a list of vehicle manufacturers that have built the cars owned by the company. For example, the 
current business has purchased cars from Toyota and Ford.

***Vehicles***\
An enum containing vehicle metadata. The data includes the model, classification, price, and number of passengers that 
is associated with each concrete vehicle type.

***Camry***\
A POJO for Camry's. An Camry is a type of vehicle that this business has purchased and has available for rent.

***Escape***\
A POJO for Escape's. An Escape is a type of vehicle that this business has purchased and has available for rent.

***Sienna***\
A POJO for Sienna's. An Sienna is a type of vehicle that this business has purchased and has available for rent.

***Response***\
An interface for concrete Data Transfer Objects. The Data Transfer Object can hold all the data necessary to resolve a 
request from the customer, which is a more efficient technique than transferring one unit of data at a time. The Data 
Transfer Object is a useful design pattern when working with a remote interface.

***ExistingReservationRequest***\
The ExistingReservationRequest class provides a data model representing a customer request to check details about an 
existing reservation. The customer provides the reservation id and the system queries the database to see if the 
reservation exists. If it does, the system uses the ExistingReservationResponseto respond with useful 
reservation details.

***ExistingReservationController***\
The ExistingReservationController class is an annotated class that works with Spring to route STOMP messages. It 
handles a web request and uses the @MessageMapping annotation to route that request to the appropriate destination. In 
particular, it handles the ```/reservation/lookup``` route and subsequently works the the database to perform select 
and update operations. Finally, it use the @SendTo annotation to respond to the client with the 
ExistingReservationResponse and updates the view.

***ExistingReservationResponse***\
The ExistingReservationResponse class provides a data model representing the response to the customers request. The 
object is converted into JSON and sent back to the view. The registered observer extracts necessary information and 
updates the view.

***NewReservationRequest***\
The NewReservationRequest class provides a data model representing the customers request to rent a vehicle. The 
customer enters information on a website and submits a request to reserve a vehicle. Importantly, the system only 
allows a customer to submit the request if all input fields are populated. This protection is handled by logic included
in the existingReservation.html file. 

***NewReservationController***\
The NewReservationController class is an annotated class that works with Spring to route STOMP messages. It handles a 
web request and uses the @MessageMapping annotation to route that request to the appropriate destination. In 
particular, it handles the ```/reservation/request``` route and subsequently works the the database to perform select 
and update operations. Finally, it use the @SendTo annotation to respond to the client with the 
NewReservationResponse and updates the view.

***NewReservationResponse***\
The NewReservationResponse class provides a data model representing the response to the customers request. The object 
is converted into JSON and sent back to the view. The registered observer extracts necessary information and updates 
the view.

***DateAndTimeUtil***\
The DateAndTimeUtil is a useful utility class capable of converting strings into Timestamps as well as performing 
checks to ensure the customer has submitted a valid reservation (e.g., the end date must be greater than the start 
date).

***OpenConnection***\
Creates a thread safe connection to a SQL database using the Singleton pattern.

***CreateTableStrategy***\
Declares an interface common to all supported database table creation algorithms. Concrete strategies must implement 
this interface.

***CreateSystemTables***\
Create a SQL table for the inventory of vehicles if it doesn't already exist. The system checks whether or not this 
table exists at startup because the application depends on a table existing. When the application launches, the user
will be presented with several JOptionPane dialogs. One of these dialogs will ask the admin if he or she needs to 
create the initial tables. If so, the system uses DDL commands to create four different tables (e.g. Store, Vehicle, 
Customer, Reservation).

***DbLoader***\
The DbLoader can be used to seed the database with an initial set of records. When the application launches, the user
will be presented with several JOptionPane dialogs. One of these dialogs will ask the admin if he or she wants to seed 
the database with these initial records.

***InsertStrategy***\
Declares an interface common to all supported database insert algorithms. Concrete strategies must implement this 
interface.

***InsertCustomerStrategy***\
Insert customer information into the database. The customer table uses the customerId (e.g., the customers email) as 
the primary key, so a customer is only inserted if the provided email address doesn't already exist. This allows the 
system to maintain a unique record of customer information.

***InsertReservationStrategy***\
Insert reservation details whenever a new reservation is made. This insert operation includes the reservation id, 
customer id, start date, and end date. An ExistingReservationRequest also uses this table to check if a particular 
vehicle is available for reservation or not. For example, a vehicle cannot be reserved if the new request overlaps with 
the start and end date of an existing reservation.

***InsertVehicles***\
Inserts individual vehicles into a MySQL database. Because the application lets customers request reservations against 
an existing product, the product must first exist. Therefore, ensure that the inventory table within the database has a 
set of vehicles.

***SelectStrategy***\
Declares an interface common to all supported database Select algorithms. Concrete strategies must implement this 
interface.

***SelectAvailableReservation***\
The SelectAvailableReservation queries the reservation table to confirm whether or not a reservation can be made for  
a customers request. For example, a vehicle cannot be reserved if the new request overlaps with the start and end date 
of an existing reservation. If there is a conflict, the system responds with an null Data Transfer Object that is used 
to inform the customer that their request cannot be fulfilled.

***SelectExistingReservation***\
The SelectExistingReservation queries the reservation table for the reservation id provided by the customer. This query 
is initiated by the customer, routed to the ExistingReservationController, and then returns an 
ExistingReservationResponse that is used to update the view with either the reservation details or a message 
indicating that the reservation id is not in the system.

***CloseConnection***\
A collection of JDBC helper methods to close connections. Utility classes should not have a public or default 
constructor. It is important to close ResultSets, PreparedStatements, Statements, and Connections.

# System Design
The system architecture is based on SOLID principles and core Object Oriented design patterns. Each class within the 
application has a well-defined single-responsibility, which has been highlighted in the System Components section. 
Additionally, the application uses the following patterns:

1) Singleton Pattern
2) Factory Method Pattern
3) Composite Pattern
4) Iterator Pattern
5) Observer Pattern
6) Strategy Pattern
7) Data Transfer Object Pattern
8) Callback Pattern

UML Diagram:
![Vehicle Reservation System Class Diagram (1)](https://user-images.githubusercontent.com/12025538/110372953-ee46f780-801c-11eb-8140-466c6f49770a.png)



