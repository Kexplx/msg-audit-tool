// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add("login", (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add("drag", { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add("dismiss", { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite("visit", (originalFn, url, options) => { ... })

function inputAudit(testAudit) {
  cy.on('uncaught:exception', (err, runnable) => {
    expect(err.message).to.include('nebular issue: https://github.com/akveo/nebular/issues/2338');
    done();
    return false;
  });
  // Input Audit name, start date, end date and open next collapsed accordeon through click
  cy.get('.grid-3-1 > :nth-child(1) > .input-full-width').clear().type(testAudit.name);
  if (testAudit.start) {
    cy.get(
      '.ng-tns-c155-1.ng-star-inserted > .ng-trigger > .item-body > .grid-1-1 > :nth-child(1) > .input-full-width',
    ).click();
    cy.get('.today > .cell-content').click();
  }
  if (testAudit.end) {
    cy.get(
      '.ng-tns-c155-1.ng-star-inserted > .ng-trigger > .item-body > .grid-1-1 > :nth-child(2) > .input-full-width',
    ).click();
    cy.get('.today > .cell-content').click();
  }
  cy.get(':nth-child(2) > .accordion-item-header-collapsed').click();
  cy.get(':nth-child(1) > .accordion-item-header-expanded').click();

  // Input Customer data and open next collapsed accordeon through click
  cy.get('.ng-trigger > .item-body > :nth-child(1) > :nth-child(1) > .input-full-width')
    .filter(':visible')
    .clear()
    .type(testAudit.customerData.name);
  cy.get('.ng-trigger > .item-body > :nth-child(1) > :nth-child(2) > .input-full-width')
    .filter(':visible')
    .clear()
    .type(testAudit.customerData.department);
  cy.get('.ng-trigger > .item-body > :nth-child(2) > :nth-child(1) > .input-full-width')
    .filter(':visible')
    .clear()
    .type(testAudit.customerData.corporateDivision);
  cy.get('.ng-trigger > .item-body > :nth-child(2) > :nth-child(2) > .input-full-width')
    .filter(':visible')
    .clear()
    .type(testAudit.customerData.sector);
  cy.get(':nth-child(3) > .accordion-item-header-collapsed').click();
  cy.get(':nth-child(2) > .accordion-item-header-expanded').click();

  // Input Contact Information and open next collapsed accordeon through click
  cy.get(':nth-child(1) > .appearance-outline > .select-button').click();
  cy.get('nb-option').contains(testAudit.contactPerson.title).click();
  cy.get('.ng-trigger > .item-body > :nth-child(1) > :nth-child(2) > .input-full-width')
    .filter(':visible')
    .clear()
    .type(testAudit.contactPerson.salutation);
  cy.get('.ng-trigger > .item-body > :nth-child(2) > :nth-child(1) > .input-full-width')
    .filter(':visible')
    .clear()
    .type(testAudit.contactPerson.firstName);
  cy.get('.ng-trigger > .item-body > :nth-child(2) > :nth-child(2) > .input-full-width')
    .filter(':visible')
    .clear()
    .type(testAudit.contactPerson.lastName);
  cy.get('.field-item-contact-information > .input-full-width')
    .clear()
    .type(testAudit.contactPerson.information);
  cy.get('.accordion-footer > .status-primary').click();
}

Cypress.Commands.add('inputAudit', inputAudit);
