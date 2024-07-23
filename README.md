## Event Driven Micro Service application using kafka
Code base is a demo to show case event driven microservice and has 4 springboot applications.
The image below shows the interactions and functionalities of each application.

![springboot-event-driven.jpg](readme-resource%2Fspringboot-event-driven.jpg)

Use the Below commands to start kafka broker.
1st - Start zookeeper
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
2nd - Start kafka broker
.\bin\windows\kafka-server-start.bat .\config\server.properties
