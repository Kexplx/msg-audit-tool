describe('AppNavbarComponent', () => {
  beforeEach(() => {
    cy.visit(Cypress.config().baseUrl);
  });

  it('Contains "Sidebar" in sidebar content', () => {
    cy.get('[data-cy=sidebar-content]').contains('Sidebar');
  });

  it('Is hidden on initial load on mobile', () => {
    cy.viewport('iphone-5');
    cy.get('[data-cy=sidebar]').should('have.class', 'collapsed');
  });

  it('Is fixed on mobile', () => {
    cy.viewport('iphone-5');
    cy.get('[data-cy=sidebar]').should('have.class', 'fixed');
  });
});
