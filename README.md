![Angular Frontend Build](https://github.com/Kexplx/msg-audit-tool/workflows/Angular%20Frontend%20Build/badge.svg)
![Angular Frontend Build](https://github.com/Kexplx/msg-audit-tool/workflows/Java%20Backend%20Build/badge.svg)

<a href="https://badges.mit-license.org/" alt="MIT Licence">
    <img src="https://img.shields.io/badge/license-MIT-0677b7" />
</a>
<a href="https://github.com/Kexplx/msg-audit-tool/releases/tag/sprint_03_release"  alt="Sprint Release Link">
    <img src="https://img.shields.io/badge/release-sprint_3-1abc9c" />
</a>

<a href="https://kexplx.github.io/msg-audit-tool/"  alt="Documentation Link">
    <img src="https://img.shields.io/badge/documentation-compodoc-e74c3c" />
</a>

# MSG Audit Tool

An audit tool for software projects based on the ISO/IEC 25010 specification.

Developed in the context of the AMOS Project in summer of 2020 @ FAU for the company msg.

## Frontend

The frontend is a single page web application for mobile and desktop, built with [Angular](https://github.com/angular).

### Browser Support
The frontend uses [nebular Components](https://akveo.github.io/nebular/) for the UI design which supports the listed browsers.
Since we mostly implement within these Components, your guideline to determine if the tool works on your browser should be this:

| [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png" alt="Chrome" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Chrome | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png" alt="Firefox" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Firefox | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt="IE / Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>IE / Edge | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/safari/safari_48x48.png" alt="Safari" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Safari | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/opera/opera_48x48.png" alt="Opera" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Opera |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| last 2 versions                                                                                                                                                                                               | last 2 versions                                                                                                                                                                                                   | IE11, Edge                                                                                                                                                                                                      | last 2 versions                                                                                                                                                                                               | last 2 versions                                                                                                                                                                                           |

We currently (Mai 2020) use the following browser versions for development, so we can __guarantee__ that the MSG Audit Tool will function properly on these versions:
- Firefox 75
- Firefox 76
- Chromium/Chrome 80
- Chromium/Chrome 81
- iPhone 5

We can __not guarantee__ support for other browsers but we are quite certain that the MSG Audit Tool will run on most browsers with proper [ES6 Support](https://www.w3schools.com/js/js_es6.asp) (most modern browsers).

### Documentation

A comprehensive documentation for the angular application is available on [GitHub Pages](https://kexplx.github.io/msg-audit-tool/) or locally under ng-frontend/documentation

### Development

To set it up locally for development do the following:

1. Install [node.js](https://nodejs.org/en/).
2. Install the Angular CLI globally: `npm i -g @angular/cli`
3. Clone this repository.
4. Run `npm i` inside /ng-frontend to install all dependencies.
5. To serve and open the application, run `ng s -o`. The application will open under [http://localhost:4200](http://localhost:4200).

We use VS Code for development. To apply our coding guidelines use the following plugins:

- Prettier - Code Formatter (enable it as the default formatter)

### Testing

Set up your development environment as described above. Then use the following instructions to start the tests.

#### Unit tests

To run the unit tests do the following:

1. (Linux only) Make sure to have Chrome/Chromium installed and the path to it in the `CHROME_BIN` environment variable.
2. To start all unit tests use `npm run test` or `npm run test-headless`.

Unit tests are written in the `.spec` files in each components directory.

#### End-to-end tests

We use cypress for our end-to-end-tests. To start all End-to-end tests use `npm run e2e` or `npm run e2e-headless`.

End-to-end tests are written in the `cypress/` folder.

## Backend

The Java-Backend uses the Spring Boot Framework and MySQL.

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

3. In your mysql shell create a database named `msg_audit_database`:

   `CREATE DATABASE msg_audit_database;`

4. In your mysql shell create a user `amos` with the password `MsgAuditTool2020!`:

   `CREATE USER 'amos'@'localhost' IDENTIFIED BY 'MsgAuditTool2020!';`

5. Grant user all privileges:

   `GRANT ALL PRIVILEGES ON * . * TO 'amos'@'localhost';`

   `FLUSH PRIVILEGES;`

### Run

To start the server while developing you can use Eclipse or another IDE and run `com.amos2020.javabackend.JavaBackendApplication` as Java Application.

### Package and run

We use Maven to handle our dependencies and to package our source code.

To package the backend server use `mvn package` in the `java-backend` folder. The output will be in `java-backend/target`.

To run this package as a standalone server use `java -jar target/java-backend-0.0.1-SNAPSHOT.jar`.

### Testing

The tests can be started with `mvn test`
