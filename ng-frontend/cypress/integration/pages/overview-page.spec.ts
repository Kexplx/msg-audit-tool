describe('OverviewPage', () => {
  let baseUrl = Cypress.config().baseUrl;
  let testAudit;
  let testAuditEdited;

  before(() => {
    cy.visit(baseUrl);
    cy.fixture('audits/example-audit').then(json => {
      testAudit = json;
    });
  });

  // function checkAuditHeader(testAudit) {
  //   // Verify that an Audit is showing and that it contains the correct Audit name and Company Name
  //   cy.get('[data-cy=audit-short-infos]').contains(testAudit.name);
  //   cy.get('[data-cy=audit-short-infos]').contains(testAudit.customerData.name);
  //   if (testAudit.start || testAudit.end) {
  //     cy.get('[data-cy=audit-short-infos]').should('exist');
  //   }
  // }

  function checkAuditStatusLabel(testAudit) {
    cy.get('[data-cy=audit-status]').first().should('exist');
  }

  it('Opens the window to add an audit with a click on the new audits button', () => {
    cy.get('[data-cy=new-audit]').click();
    cy.get('[data-cy=audit-data-form]').should('exist');
  });

  it('Shows an audit entry on the overview page when added by an user', () => {
    cy.inputAudit(testAudit);
    cy.get('[data-cy=audit-short-infos]').should('exist');
  });

  it('Adds the correct audit name and company name entered by the user to the overview', () => {
    cy.testAuditListEntry(testAudit);
  });

  it('Shows the audit status label', () => {
    checkAuditStatusLabel(testAudit);
  });

  it('Shows a button to see further options to do with the audit', () => {
    cy.get('[data-cy=audit-options]').should('exist');
  });

  it('Clicking on an audit redirects to audit page', () => {
    cy.get('[data-cy=audit-short-infos]').first().click();
    cy.url().should('contain', 'interview');
  });

  it('Clicking on an audit delete button should remove the audit', () => {
    cy.get('[data-cy=home]').click();
    cy.get('[data-cy=audit-options]').first().click();
    cy.get('.menu-title').filter(':visible').filter(':contains("Löschen")').click();
    cy.get('[data-cy=audits-list]').should('not.contain', testAudit.name);
  });
});
