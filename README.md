# Event-Driven Microservices with Axon Framework: Implementing SAGA, CQRS, and Event Sourcing

This Repository contains Codebase of Spring Boot application that shows how to implement Event-Driven Microservice architecture using SAGA, CQRS, Transactions and Event-Sourcing using Axon Framework.

This project showcases the implementation of an Event-Driven Microservice architecture using the Axon Framework. It leverages the SAGA pattern for managing transactions and the CQRS pattern for separating read and write operations. Event-Sourcing is used to persist the state of the application as a sequence of events.

## Technologies

- Java 17
- Maven
- MySQL
- Spring Boot
- Spring Data JPA
- Axon Server
- Docker

## Concepts used

- **Event-Driven Microservices:** Building microservices that communicate through events.
- **CQRS Design Pattern:** Separating the read and write operations to optimize performance and scalability.
- **SAGA Design Pattern:** Managing distributed transactions across multiple microservices.
- **Event-Sourcing Messages:** Persisting the state of the application as a sequence of events.
- **Axon Framework:** A framework for building scalable and extensible applications using DDD, CQRS, and Event Sourcing.
- **Transactions:** Ensuring data consistency across microservices.

## Features

- #### CommonService
    > Contains shared DTO classes, command, queries and event classes for all microservices

- #### UserService
    > Contains Aggregates, Event Handlers of UserService.
    >  
    > Contains details of user along with wallet details.

- #### ProductService
    > Contains Aggregates, Event Handlers of ProductService.

- #### PaymentService
    > Contains Aggregates, Event Handlers of PaymentService.

- #### ShipmentService
    > Contains Aggregates, Event Handlers of ShipmentService.

- #### OrderService
    > Contains Aggregates, Event Handlers of OrderService.
    > 
    > Manages SAGA Orchestration.
    > 
    > If any error occurs in the saga orchestration then their compensating transactions are managed.

## Installation

1. Clone the repository.

        git clone https://github.com/KarthikeyanNagarajan/EventDrivenMicroservice-SAGA-CQRS-EventSourcing-AxonFramework.git

2. Install and Run Axon Server using below method.

    - Using JAR File:
        Download the latest axonserver.jar file from the AxonIQ website. Copy the axonserver.jar to a directory of your choice. Run below command,

          java -jar axonserver.jar

    - Using Docker
        Pull the Axon Server Docker Image & Run the Docker Container.

          docker pull axoniq/axonserver
          docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver

2. Navigate to the project directory.

       cd EventDrivenMicroservice-SAGA-CQRS-EventSourcing-AxonFramework

3. Configure the properties of each microservice according to your requirements.

4. Run the Application in below order.
    - ##### Start UserService
          cd UserService
          mvn spring-boot:run

    - ##### Start ProductService
          cd ProductService
          mvn spring-boot:run

    - ##### Start PaymentService
          cd PaymentService
          mvn spring-boot:run

    - ##### Start ShipmentService
          cd ShipmentService
          mvn spring-boot:run

5. Check Service Status in Axon Dashboard. It will show the services, their commands, queries and Events.	
    - ##### Axon Server Dashboard:
          http://localhost:8024

6. Import Postman Collection which contains all the URL routes used.
      
       EventDrivenMicroservice-SAGA-CQRS-EventSourcing-AxonFramework.postman_collection.json


## Contributing

Contributions are welcome! Please fork the repository and create a pull request.
