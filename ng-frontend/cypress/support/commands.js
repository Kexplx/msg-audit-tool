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

Cypress.Commands.add('inputAudit', inputAudit);
Cypress.Commands.add('injectBackendMocks', injectBackendMocks);
