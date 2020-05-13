# Backend
Our Java-Backend uses the Spring Boot Framework and mysql.

## Development

### Getting Started
1. Install mysql server:

   e.g. mariaDB on Arch Linux
   ```
   sudo pacman -S mariadb
   mariadb-install-db --user=mysql --basedir=/usr --datadir=/var/lib/mysql
   ``` 

2. Install Java

### Establish database connection
1. Start your mysql server:

   e.g. `sudo systemctl start mysqld` on Arch Linux

2. Connect to your Database:

   `mysql -u root -p`
   
   If you forgot your mysql root password, you can reset it like [this](https://www.digitalocean.com/community/tutorials/how-to-reset-your-mysql-or-mariadb-root-password).

2. In your mysql shell create a database named `msg_audit_database`:

   `CREATE DATABASE msg_audit_database;`
   
3. In your mysql shell create a user `amos` with the password `MsgAuditTool2020!`:

   `CREATE USER 'amos'@'localhost' IDENTIFIED BY 'MsgAuditTool2020!';`
   
4. Grant user all privileges:

   `GRANT ALL PRIVILEGES ON * . * TO 'amos'@'localhost';`
   
   `FLUSH PRIVILEGES;`

### Run
To start the server while developing you can use Eclipse or another IDE and run `com.amos2020.javabackend.JavaBackendApplication` as Java Application. 

### Package and run 
We use Maven to handle our dependencies and to package our source code.

To package the backend server use `mvn package` in the `java-backend` folder. The output will be in `java-backend/target`.

To run this package as a standalone server use `java -jar target/java-backend-0.0.1-SNAPSHOT.jar`.

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
