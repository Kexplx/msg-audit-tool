describe('AddAuditForm', () => {
  let baseUrl = 'http://localhost:4200/audits/';

  it('Routing to audits/neu opens AddAuditForm', () => {
    cy.visit(baseUrl + 'neu');
    cy.get('app-add-audit-dialog.ng-star-inserted > .scrollable-container');
  });

  it('Clicking abbrechen button closes form', () => {
    cy.get('.accordion-footer > .status-basic').click();

    cy.get('#cdk-overlay-0 > nb-dialog-container > app-add-audit-dialog > div > form').should(
      'not.exist',
    );
  });
});
