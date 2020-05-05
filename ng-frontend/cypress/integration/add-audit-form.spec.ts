describe('AddAuditForm', () => {
  let baseUrl = 'http://localhost:4200/audits/';

  it('Route audits/neu opens AddAuditForm', () => {
    cy.visit(baseUrl + 'neu');
    cy.get('#cdk-overlay-0 > nb-dialog-container > app-add-audit-dialog > div > form');
  });

  it('Clicking abbrechen button closes form', () => {
    cy.get('.accordion-footer > .status-basic').click();

    cy.get('#cdk-overlay-0 > nb-dialog-container > app-add-audit-dialog > div > form').should(
      'not.exist',
    );
  });
});
