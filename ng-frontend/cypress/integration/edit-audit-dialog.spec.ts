describe('EditAuditDialog', () => {
  let baseUrl = Cypress.config().baseUrl + '/audits/';

  it('Routing to audits/edit on invalid id closes dialog', () => {
    cy.visit(baseUrl + '123/edit');

    cy.get('nb-dialog').should('not.exist');
  });
});
