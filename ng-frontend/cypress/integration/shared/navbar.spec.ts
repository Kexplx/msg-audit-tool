describe('Navbar', () => {
  beforeEach(() => {
    cy.visit(Cypress.config().baseUrl);
  });

  it('Contains Menu Icon Button', () => {
    cy.get('[data-cy=toggle-sidebar]');
  });

  it('Contains "Audit"', () => {
    cy.get('[data-cy=home]');
  });

  it('Contains GitHub Icon Button', () => {
    cy.get('[data-cy=github]');
  });

  it('Clicking the sidebar toggle button opens the sidebar', () => {
    cy.viewport('iphone-5');
    cy.get('[data-cy=toggle-sidebar]').click();
    cy.get('[data-cy=sidebar]').should('have.class', 'expanded');
  });
});
