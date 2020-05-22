import { Audit } from 'src/app/data/models/audit.model';

function inputAudit(testAudit) {
  cy.on('uncaught:exception', (err, runnable) => {
    expect(err.message).to.include('nebular issue: https://github.com/akveo/nebular/issues/2338');
    done();
    return false;
  });
  // Input Audit name, start date, end date and open next collapsed accordeon through click
  cy.get(':nth-child(1) > .accordion-item-header-expanded').should('exist');
  cy.get('.field-item-audit-name > .input-full-width').type(testAudit.name);
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
  cy.get(':nth-child(1) > .accordion-item-header-expanded').click();
  cy.get(':nth-child(2) > .accordion-item-header-collapsed').click();

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
  cy.get(':nth-child(2) > .accordion-item-header-expanded').click();
  cy.get(':nth-child(3) > .accordion-item-header-collapsed').click();

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

function testAuditInfoPage(testAudit) {
  cy.get(':nth-child(3) > :nth-child(1) > .audit-params').should('contain.text', testAudit.name);
  cy.get(':nth-child(5) > :nth-child(1) > .audit-params').should(
    'contain.text',
    testAudit.customerData.name,
  );
  cy.get(':nth-child(5) > :nth-child(2) > .audit-params').should(
    'contain.text',
    testAudit.customerData.department,
  );
  cy.get(':nth-child(6) > :nth-child(1) > .audit-params').should(
    'contain.text',
    testAudit.customerData.corporateDivision,
  );
  cy.get(':nth-child(6) > :nth-child(2) > .audit-params').should(
    'contain.text',
    testAudit.customerData.sector,
  );
  cy.get(':nth-child(8) > :nth-child(1) > .audit-params').should(
    'contain.text',
    testAudit.contactPerson.firstName,
  );
  cy.get(':nth-child(8) > :nth-child(1) > .audit-params').should(
    'contain.text',
    testAudit.contactPerson.lastName,
  );
  cy.get(':nth-child(8) > :nth-child(1) > .audit-params').should(
    'contain.text',
    testAudit.contactPerson.salutation,
  );
  cy.get(':nth-child(8) > :nth-child(1) > .audit-params').should(
    'contain.text',
    testAudit.contactPerson.title,
  );
  cy.get(':nth-child(9) > :nth-child(1) > .audit-params').should(
    'contain.text',
    testAudit.contactPerson.information,
  );
}

function testAuditsOverviewPage(testAudit) {
  cy.get('app-audit-card.ng-star-inserted > nb-card > nb-card-header > :nth-child(1)').should(
    'contain.text',
    testAudit.name,
  );
  cy.get('app-audit-card.ng-star-inserted > nb-card > nb-card-header > :nth-child(1)').should(
    'contain.text',
    testAudit.customerData.name,
  );
}

Cypress.Commands.add('inputAudit', inputAudit);
Cypress.Commands.add('testAuditInfoPage', testAuditInfoPage);
Cypress.Commands.add('testAuditsOverviewPage', testAuditsOverviewPage);
