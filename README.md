# Election Results
Election Result is a Spring Boot application meant to show election results from an UK general election. 
Results are provided as XML files that are ingested and processed. An application user interface shows in real time the ongoing election.

## File Ingestion
The file ingestion is realised with a Spring Integration flow. It creates a pipe implementing some common EIP. 

An inbound file adapter polls file from a configured folder.
Once the file is ingested it undergoes a series of transformation and manipulation before being saved in a database.

The ingestion flow will terminate raising an election scores result update event. 
Then an event listener will send the new scores to a pubsub topic. 

Successfully processed file are marked as PROCESSED. Failed processed file are marked as FAILED and an email is sent to a system supervisor.

## User interface
The user interface make use of STOMP to connect to a configured WebSocket endpoint and 
subscribes to the pubsub topic where the live scores are pushed.

Once new live scores reach the topic these will be shown on the application main page.

## Election Score Board
The Election Score Board carry on the top scores for the ongoing election.

Top Scores show the top party scores. The number of seats won and the current share of votes.
A special score is added to the list to aggregate score for the other parties.

Once a party reach the majority it will be highlighted in green in the UI.                   
    
## Technology stack
- jdk 1.8
- Maven
- Spring Boot
- Spring Integration
- Websocket
- HTML, angularJS, STOMP

# Configuration

All configuaration are made in ```application.properties``` located under the ```resource``` folder

## Database Configuration

The application make use of an in memory H2 database. No configuration is needed.

A management console can be found at

http://localhost:8080/h2/

To login into the console leave all the predefined values and use the following as JDBC URL:

```jdbc:h2:mem:testdb```

##### NOTE
Not sure where to save the total number of seats to assign I created 
an ```Election``` Object where to save some information. The table is created and populated
automatically during application start up. 
        
During startup a ```schema.sql``` and ```data.sql``` script are executed. Both can be found under ```resource``` folder.
  
As an in memory database, every application restart will cause the loose of the previously saved data.

## Mail SMTP configuration

To setup the SMTP server configure the following properties:
 
```
spring.mail.host=localhost
spring.mail.port=5025
spring.mail.username=
spring.mail.password=
```

##### NOTE
For the tests I used fake-smtp-server. Leave the properties as they are if you decide to use it.
You can download the server and run it in a local machine from
https://github.com/gessnerfl/fake-smtp-server


## File ingestion configuration

Setup the inbound folder where XML files will be copied:
```
poll.inbound.path = path_to_inbound_folder
```

Setup the start up mode for the ingestion flow. If left as it is the polling will start automatically 
once the application is up and running:

```
poll.autostartup = true
```

When you set the value to ```false``` the polling needs to be manually started using the REST 
endpoint:

http://localhost:8080/ingestion/startPoll

To stop the polling use the endpoint:

http://localhost:8080/ingestion/stopPoll

##Build and run the application

### From IDE
You can simply import the project as a Maven project and run the main class 

```
com.example.electionresults.ElectionResultsApplication
```

### From command line
From the project root folder execute the following to build the application ```jar```. This will also run the automated test: 

```
mvn clean package
```

Then execute the following command to run the application:

```
java -jar target/election-results-1.0.jar
```

Once the application is up and running you can see the Election Score Board at:

http://localhost:8080/

## Test

Some unit and integration test are provided. Run it with:

```
mvn test
```

# Set of behaviours

I used the set to address some behaviours happens in my application. This behaviours try to map
Object and Configurations used in the application so that a first time reader can be easely 
go through the code and the various configurations

## Ingestion flow (happy flow)

**Given** that the ```InboundChannelAdapter``` is active 

**And** that a new or modified file with ```xml``` extension has been detected
 
**And** the ingestion flow transaction is synchronized with the ```JpaTransactionManager```

**When** that ```File``` is ingested

**Then** the transaction manager start a new transaction

**And** the file is transformed in a String from a ```FileToStringTransformer```
    
**And** the ```String``` is unmarshalled  in an ```ConstituencyResults``` Object representation of that String

**And** the ```ConstituencyResults``` content is validated

**And** the new ```ElectionScore``` is updated in the database 

**And** the ```ElectionResultUpdateEvent``` is published

**And** the transaction is committed
 
**Then** the ingested ```File``` is marked as *PROCESSED*
 
 ## Ingestion flow (failed flow)
 
**Given** that the ```InboundChannelAdapter``` is active 
 
**And** that a new or modified file with ```xml``` extension has been detected
  
**And** the ingestion flow transaction is synchronized with the ```JpaTransactionManager```
 
**When** that ```File``` is ingested
 
**Then** the transaction manager start a new transaction
 
**And** the file is transformed in a String from a ```FileToStringTransformer```
     
**And** the ```String``` can't be unmarshalled in a ```ConstituencyResults``` Object 
  
**And** an "Invalid XML file" ```Exception``` is raised
 
**And** the transaction is rolled back
   
**Then** the ingested ```File``` is marked as *FAILED*
  
**And** an Email is built

**And** the built Email is sent to the system supervisor
   
## Browse live scores
   
**Given** the user is showing the ```ElectionScoreBoard``` from the UI

**And** the UI is connected to the websocket endpoint

**And** the UI is subscribed to the live scores topic   

**And** the ```ElectionResultUpdateEvent``` is published
   
**And** the ```ElectionResultUpdateEventListener``` detect the event and requests for ```liveScore``` and send it to the topic

**Then** the UI will show the new live scores received from the websocket endpoint

## Election Score Board creation with the Builder

**Given** a list of election scores

**And** a number of wanted top scores

**And** a label for the aggregate scores

**And** and a number of total seats to assign

**Then** build an ```ElectionScoreBoard```
