describe('AppNavbarComponent', () => {
  beforeEach(() => {
    cy.visit(Cypress.config().baseUrl);
  });

  it('contains "Sidebar" in sidebar content', () => {
    cy.get('[data-cy=sidebar-content]').contains('Sidebar');
  });

  it('is hidden on initial load on mobile', () => {
    cy.viewport('iphone-5');
    cy.get('[data-cy=sidebar]').should('have.class', 'collapsed');
  });

  it('is fixed on mobile', () => {
    cy.viewport('iphone-5');
    cy.get('[data-cy=sidebar]').should('have.class', 'fixed');
  });
});
