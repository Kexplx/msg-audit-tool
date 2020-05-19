import { Audit } from 'src/app/data/models/audit.model';

describe('OverviewDialog', () => {
  let baseUrl = Cypress.config().baseUrl;
  let testAudit: Audit;
  let testAuditEdited: Audit;

  before(() => {
    cy.visit(baseUrl);
    cy.fixture('example-audit').then(json => {
      testAudit = json;
    });
  });

  function checkAuditHeader(testAudit) {
    // Verify that an Audit is showing and that it contains the correct Audit name and Company Name
    cy.get(':nth-child(1) > nb-card > nb-card-header').contains(testAudit.name);
    cy.get(':nth-child(1) > nb-card > nb-card-header').contains(testAudit.customerData.name);
    if (testAudit.start || testAudit.end) {
      cy.get(':nth-child(1) > nb-card > nb-card-header').should('exist');
    }
  }

  function checkAuditStatusLabel(testAudit) {
    cy.get('.banner-status').should('exist');
  }

  it('Opens the window to add an audit with a click on the new audits button', () => {
    cy.get('.grid-1-auto-auto > .status-primary').click();
    cy.get('nb-dialog-container').should('exist');
  });

  it('Shows an audit entry on the overview page when added by an user', () => {
    cy.inputAudit(testAudit);
    // Verify that an Audit exists
    cy.get('nb-card > nb-card-header').should('exist');
  });

  it('Adds the correct audit name and company name entered by the user to the overview', () => {
    checkAuditHeader(testAudit);
  });

  it('Shows the audit status label', () => {
    checkAuditStatusLabel(testAudit);
  });

  it('Shows a button to see further options to do with the audit', () => {
    cy.get(':nth-child(1) > nb-card > nb-card-header > .appearance-filled').should('exist');
  });

  it('Clicking on an audit redirects to audit page', () => {
    cy.get('app-audit-card.ng-star-inserted > nb-card > nb-card-header').click();
    cy.url().should('contain', 'overview');
  });

  it('Clicking on an audit delete button should remove the audit', () => {
    cy.get(':nth-child(2) > .appearance-hero').click();
    cy.get(':nth-child(1) > nb-card > nb-card-header > .appearance-filled').click();
    cy.get('.menu-title').filter(':visible').filter(':contains("Löschen")').click();
    cy.get(
      'app-audit-card.ng-star-inserted > nb-card > nb-card-header > .appearance-filled',
    ).should('not.contain', testAudit.name);
  });
});
