describe('EditAuditDialog', () => {
  let baseUrl = 'http://localhost:4200/audits/';

  it('Routing to audits/edit on invalid id closes dialog', () => {
    cy.visit(baseUrl + '123/edit');

    cy.get('nb-dialog').should('not.exist');
  });
});
