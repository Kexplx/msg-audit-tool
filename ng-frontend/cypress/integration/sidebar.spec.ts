describe('AppNavbarComponent', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200');
  });

  it('Contains "Sidebar" in sidebar content', () => {
    cy.get('.sidebar-container-content').contains('Sidebar');
  });

  it('Is hidden on initial load on mobile', () => {
    cy.viewport('iphone-5');
    cy.get('nb-sidebar').should('have.class', 'collapsed');
  });

  it('Is fixed on mobile', () => {
    cy.viewport('iphone-5');
    cy.get('nb-sidebar').should('have.class', 'fixed');
  });
});
