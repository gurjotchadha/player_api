# Player API

This API is a service that manages player data, 
and responds to client requests with varying levels of details based on user roles.
Currently the role is passed as a query param (isAdmin)

## Getting Started

### Running the API

#### Prerequisite
* Due to the inclusion of the tinyLLama LLM (Currently configured to only use a local instance at http://localhost:11434),
it is required to install and have a running tinyLLama instance running via OLLAMA. Please download OLLAMA from https://ollama.com/
* To run Ollama with tinyLlama instance follow the steps:
  * Install Ollama
  * Open CMD
  * Run command <ollama pull tinyllama> to fetch a tinyllama image for your local
  * Run command <ollama run tinyllama> to run the tinyllama instance

### Running the App via Intellij
You can run the app in Intellij by simply using the run option from the main class after setting up Ollama instance.

### Running the App via MVN
If you have mvn setup on your local machine, once you have completed the pre-requisite steps, you can run command 
"mvn clean package" to create a jar and then execute the jar file using "java -jar target/<jarname-0.0.1-SNAPSHOT>.jar"

### Postman call
Once you have the API running, Use the attached postman 
collection [here](src/docs/postman/Player API Collection.postman_collection.json) to test various endpoints

<font color="red"> Note: Make sure to update the password in the authorization tab.</font>

### Authentication
I am using default spring security configurations.
When making the postman request you will need to update the password field under Authorization(Basic Auth) with the generated password from the console.
Basic Auth:
  * username: user
  * password: Generated at runtime in console as a spring security key

## Database Model

I chose to use a SQL db(H2) as my base for the following reasons:
* The data we need to manage, that is Player Data has clearly defined relationships and is fairly **structured**.
* Based on the information provided, this API will be more focussed on querying the data rather than writing to the database. In such cases SQL db's allow for more Flexibility.
* Also, when working with "potentially" nested future classes, its better to use a SQL db as in case of NoSQL db such as mongo we can have performance issues when it comes to resolving the nested DB References.
* A potential use case for in-memory can be reasoned for here but it is not a viable solution if we need multiple users or any kind of data retention.


Note: The reason for specifically using H2 over any other db was simply due to ease of use as this is a sample project and Spring JPA allows us to replace the underlying db with ease.

![Player DB ER model.png](src/docs/Player%20DB%20ER%20model.png)

## Design Patterns Used

The following Design Patterns are being used as part of the project:
* **Singleton**: Default Scope that Spring Boot uses for initializing its Beans. I am using the default implementation over any other scopes because it allows us to have a single instance of every bean and it is designed for Dependency Injection.
* **Dependency Injection**: Implemented using the Autowired Annotation of spring boot which allows us to inject beans as needed and helps us avoid tight coupling.
* **Builder Pattern**: Implemented via Lombok's @Builder annotation. It allows us to create complex objects in steps which reduces potential errors and makes the code more readable/maintainable.
* **Repository Pattern**: Implemented via Spring Data JPA. We are using repositories as within the current scope of the project, we don't need complex queries and are just implementing the basic Save/Find functions.
* **DTO Pattern**: In this case, I am using the DTO pattern to specifically control what gets returned during runtime. For example in case of a request where "isAdmin=false", we return a UserPlayerResponseDto object which can limit the data that needs to be sent over.
* **MVC Pattern**: While not a complete implementation of a MVC patter, we can still call this MVC as it uses "Postman" for the view while seperating out the Model/Controller Parts within the app.

## Developer Contact

* Name: Gurjot Singh
* Email: chadha.gurjot@gmail.com