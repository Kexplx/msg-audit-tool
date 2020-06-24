const baseUrl = Cypress.config().baseUrl;

function addAudit(testAudit) {
  //cy.visit(baseUrl + '/audits/new');
  cy.get('[data-cy=home]');
  cy.get('[data-cy=new-audit]').click();
  inputAudit(testAudit);
}

function inputAudit(testAudit) {
  // Input Audit name, start date, end date and open next collapsed accordeon through click
  cy.get('[data-cy=audit-data-form]').should('exist');
  cy.get('[data-cy=audit-name-input]').clear().type(testAudit.name);
  if (testAudit.start) {
    cy.get('[data-cy=audit-start-input]').click();
    cy.get('.today > .cell-content').click();
  }
  if (testAudit.end) {
    cy.get('[data-cy=audit-end-input]').click();
    cy.get('.today > .cell-content').click();
  }
  cy.get('[data-cy=submit-audit-data-form]').click();
}

function addPerson(testPerson) {
  cy.get('[data-cy=new-contact]').click();
  inputPerson(testPerson);
}

function inputPerson(testPerson) {
  cy.get('[data-cy=company-name-input]').filter(':visible').clear().type(testPerson.companyName);
  cy.get('[data-cy=company-department-input]')
    .filter(':visible')
    .clear()
    .type(testPerson.department);
  cy.get('[data-cy=company-division-input]')
    .filter(':visible')
    .clear()
    .type(testPerson.corporateDivision);
  cy.get('[data-cy=company-sector-input]').filter(':visible').clear().type(testPerson.sector);
  cy.get('[data-cy=contact-salutation-input]').click();
  cy.get('[data-cy=salutation-option]').contains(testPerson.salutation).click();
  cy.get('[data-cy=contact-title-input]').filter(':visible').clear().type(testPerson.title);
  cy.get('[data-cy=contact-firstname-input]').filter(':visible').clear().type(testPerson.firstName);
  cy.get('[data-cy=contact-lastname-input]').filter(':visible').clear().type(testPerson.lastName);
  if (testPerson.information) {
    cy.get('[data-cy=contact-info-input]').clear().type(testPerson.information);
  }
  cy.get('[data-cy=submit-data-form]').click();
}

function addInterview(testInterview) {
  cy.get('[data-cy=new-interview]').click();
  inputInterview(testInterview);
}

function inputInterview(testInterview) {
  if (testInterview.start) {
    // TODO pick a start date
    cy.get('[data-cy=interview-start-input]').click();
    cy.get('.today > .cell-content').first().click();
  }
  if (testInterview.contacts) {
    // TODO enter a contact name and a contact role
    cy.get('[data-cy=interview-contacts]').click();
    cy.get('[data-cy=interview-contact]').each(contact => {
      cy.wrap(contact).click();
    });
    cy.get('[data-cy=interview-contacts]').click();
  }
  // TODO choose an iso criteria
  cy.get('[data-cy=interview-scope-header]').click();
  cy.get('[data-cy=interview-scope-radio] > .label > .custom-checkbox').each((el, index) => {
    cy.wrap(el).click();
  });
  cy.get('[data-cy=interview-scope-header]').click();
  // submit form
  cy.get('[data-cy=submit-interview-data-form]').click({ force: true });
}

function testAuditInfoPage(testAudit) {
  // cy.get('[data-cy=audit-name]').should('contain.text', testAudit.name);
  // if (!testAudit.end) {
  //   cy.get('[data-cy=audit-timeframe]').contains('TBD');
  // }
  // cy.get('[data-cy=audit-customer-name]').should('contain.text', testAudit.customerData.name);
  // cy.get('[data-cy=audit-customer-department]').should(
  //   'contain.text',
  //   testAudit.customerData.department,
  // );
  // cy.get('[data-cy=audit-customer-division]').should(
  //   'contain.text',
  //   testAudit.customerData.corporateDivision,
  // );
  // cy.get('[data-cy=audit-customer-sector]').should('contain.text', testAudit.customerData.sector);
  // cy.get('[data-cy=audit-contact-person]').should(
  //   'contain.text',
  //   testAudit.contactPerson.firstName,
  // );
  // cy.get('[data-cy=audit-contact-person]').should('contain.text', testAudit.contactPerson.lastName);
  // cy.get('[data-cy=audit-contact-person]').should(
  //   'contain.text',
  //   testAudit.contactPerson.salutation,
  // );
  // cy.get('[data-cy=audit-contact-person]').should('contain.text', testAudit.contactPerson.title);
  // cy.get('[data-cy=audit-contact-info]').should(
  //   'contain.text',
  //   testAudit.contactPerson.information,
  // );
}

function testAuditListEntry(testAudit) {
  cy.get('[data-cy=audit-short-infos]').first().should('contain.text', testAudit.name);
  if (!testAudit.end) {
    cy.get('[data-cy=audit-short-infos]').contains('TBD');
  }
  // A newly added audit always has the status 'Planned'
  cy.get('[data-cy=audit-status]').first().should('have.attr', 'nbPopover');
  cy.get('[data-cy=audit-status]').first().invoke('attr', 'nbPopover').should('contain', 'Geplant');
}

function testInterviewListEntry(testInterview) {
  cy.get('[data-cy=interview]')
    .contains(testInterview.name)
    .then(el => {
      let testDate;
      if (!testInterview.start) {
        testDate = new Date(Date.now()).toLocaleDateString('de-DE', {
          year: 'numeric',
          month: '2-digit',
          day: '2-digit',
        });
      }
      cy.wrap(el).get('[data-cy=interview-timestamp]').contains(testDate);
      if (testInterview.contacts) {
        testInterview.contacts.forEach(contact => {
          cy.wrap(el).get('[data-cy=interview-contact-persons]').contains(contact.name);
          cy.wrap(el).get('[data-cy=interview-contact-persons]').contains(contact.role);
        });
      }
    });
}

// TODO testAlertDialog
// it('Clicking yes on warning message should close overlay', () => {
//     cy.get('[data-cy=discard]').click();
//     cy.get('[data-cy=discard-back-dialog]').should('not.exist');
//   });

Cypress.Commands.add('addAudit', addAudit);
Cypress.Commands.add('addPerson', addPerson);
Cypress.Commands.add('addInterview', addInterview);
Cypress.Commands.add('inputAudit', inputAudit);
Cypress.Commands.add('inputInterview', inputInterview);
Cypress.Commands.add('inputPerson', inputPerson);
Cypress.Commands.add('testAuditInfoPage', testAuditInfoPage);
Cypress.Commands.add('testAuditListEntry', testAuditListEntry);
Cypress.Commands.add('testInterviewListEntry', testInterviewListEntry);
