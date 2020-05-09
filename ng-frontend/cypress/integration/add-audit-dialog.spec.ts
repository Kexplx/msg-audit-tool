describe('AddAuditDialog', () => {
  let baseUrl = Cypress.config().baseUrl + '/audits/';

  it('Routing to audits/new opens AddAuditForm', () => {
    cy.visit(baseUrl + 'new');
    cy.get('nb-dialog-container');
  });

  it('Clicking abbrechen button closes form', () => {
    cy.get('.accordion-footer > .status-basic').click();

    cy.get('#cdk-overlay-0 > nb-dialog-container > app-add-audit-dialog > div > form').should(
      'not.exist',
    );
  });
});
