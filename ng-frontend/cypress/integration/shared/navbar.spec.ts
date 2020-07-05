describe('Navbar', () => {
  beforeEach(() => {
    cy.visit(Cypress.config().baseUrl);
  });

  it('contains Menu Icon Button', () => {
    cy.get('[data-cy=toggle-sidebar]');
  });

  it('contains home button', () => {
    cy.get('[data-cy=home]').click();
    cy.url().should('contain', 'audits');
  });

  it('contains contacts button', () => {
    cy.get('[data-cy=contacts]').click();
    cy.url().should('contain', 'contact-persons');
  });

  it('opens the sidebar when clicking the sidebar toggle button', () => {
    cy.viewport('iphone-5');
    cy.get('[data-cy=toggle-sidebar]').click();
    cy.get('[data-cy=sidebar]').should('have.class', 'expanded');
  });
});
