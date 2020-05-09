![Angular Frontend Build](https://github.com/Kexplx/msg-audit-tool/workflows/Angular%20Frontend%20Build/badge.svg)
# MSG Audit Tool
An audit tool for software projects based on the ISO/IEC 25010 specification.

Developed in the context of the AMOS Project in summer of 2020 @ FAU for the company msg.

## Browser Support

| [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png" alt="Chrome" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Chrome | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png" alt="Firefox" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Firefox | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt="IE / Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>IE / Edge | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/safari/safari_48x48.png" alt="Safari" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Safari | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/opera/opera_48x48.png" alt="Opera" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Opera |
| --- | --- | --- | --- | --- |
| last 2 versions | last 2 versions | IE11, Edge | last 2 versions | last 2 versions 


## Frontend

The frontend is built with Angular.

### Development
To set it up locally for development do the following:

1. Install [node.js](https://nodejs.org/en/).
2. Install the Angular CLI globally: `npm i -g @angular/cli`
3. Clone this repository.
4. Run `npm i` inside /ng-frontend to install all dependencies.
4. To serve and open the application, run `ng s -o`. The application will open under [http://localhost:4200](http://localhost:4200).

We use VS Code for development. To apply our coding guidelines use the following plugins:
- Prettier - Code Formatter (enable it as the default formatter)

### Testing

Set up your development environment as described above. Then use the following instructions to start the tests.

#### Unit tests
To run the unit tests do the following:

1. (Linux only) Make sure to have Chrome/Chromium installed and the path to it in the `CHROME_BIN` environment variable.
2. To start all unit tests use `npm run test`.

Unit tests are written in the `.spec` files in each components directory.

#### End-to-end tests
We use cypress for our end-to-end-tests. To start all End-to-end tests use `npm run e2e`.

End-to-end tests are written in the `cypress/` folder.

