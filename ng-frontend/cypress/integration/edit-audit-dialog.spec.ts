describe('EditAuditDialog', () => {
  const baseUrl = Cypress.config().baseUrl;
  const auditsUrl = baseUrl + '/audits'

  it('Routing to audits/edit on invalid id closes dialog', () => {
    cy.visit(auditsUrl);
    cy.visit(auditsUrl + '/123/edit');

    cy.get('nb-dialog').should('not.exist');
  });
});
