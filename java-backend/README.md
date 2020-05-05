# Read Me 
The following was discovered as part of building this project:

# Getting Started
* install mysql server
* install java

# Establish database connection
* start mysql server
* create a database named "msg_audit_database"
* create a user "amos" with the password "MsgAuditTool2020!"
    * CREATE USER 'amos'@'localhost' IDENTIFIED BY 'MsgAuditTool2020!';
* grant user all privileges 
    * GRANT ALL PRIVILEGES ON * . * TO 'amos'@'localhost';
    * FLUSH PRIVILEGES;
- the database will be created with the first build if it doesn't exist


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/maven-plugin/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

