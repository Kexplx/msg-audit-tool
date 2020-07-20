const baseUrl = Cypress.config().baseUrl;

function injectBackendMocks() {
  cy.server();
  cy.route({
    method: 'GET',
    url: new RegExp('faccrits', 'i'),
    response: 'fixture:backend-mock-data/facCrits.json',
  }).as('getFacCrits');
  cy.route({
    method: 'GET',
    url: '/answers',
    response: 'fixture:backend-mock-data/answers.json',
  }).as('getAnswers');
  cy.route({
    method: 'GET',
    url: '/audits',
    response: 'fixture:backend-mock-data/audits.json',
  }).as('getAudits');
  cy.route({
    method: 'GET',
    url: '/contactpersons',
    response: 'fixture:backend-mock-data/contactPersons.json',
  }).as('getContactPersons');
  cy.route({
    method: 'GET',
    url: '/interviews',
    response: 'fixture:backend-mock-data/interviews.json',
  }).as('getInterviews');

  cy.route({
    method: 'GET',
    url: '/questions/1',
    response: 'fixture:backend-mock-data/questions-1.json',
  }).as('getQuestion1');
  cy.route({
    method: 'GET',
    url: '/questions/2',
    response: 'fixture:backend-mock-data/questions-2.json',
  }).as('getQuestion2');

  cy.route({
    method: 'POST',
    url: '/audits',
    response: [],
  }).as('postAudits');
  cy.route({
    method: 'POST',
    url: '/contactpersons',
    response: [],
  }).as('postContacts');

  cy.route({
    method: 'PUT',
    url: '/audits/*',
    response: [],
  }).as('putAudits');
  cy.route({
    method: 'PUT',
    url: '/interviews/*',
    response: [],
  }).as('putInterviews');
}

function addAudit(testAudit) {
  cy.get('[data-cy=home]');
  cy.get('[data-cy=new-audit]').click();
  inputAudit(testAudit);
}

function inputAudit(testAudit) {
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
  cy.get('[data-cy=contacts]').click();
  cy.get('[data-cy=new-contact]').click();
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
  cy.get('[data-cy=contact-forename-input]').filter(':visible').clear().type(testPerson.firstName);
  cy.get('[data-cy=contact-surname-input]').filter(':visible').clear().type(testPerson.lastName);
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

Cypress.Commands.add('addAudit', addAudit);
Cypress.Commands.add('addPerson', addPerson);
Cypress.Commands.add('inputAudit', inputAudit);
Cypress.Commands.add('addInterview', addInterview);
Cypress.Commands.add('inputInterview', inputInterview);
Cypress.Commands.add('testInterviewListEntry', testInterviewListEntry);
Cypress.Commands.add('injectBackendMocks', injectBackendMocks);
