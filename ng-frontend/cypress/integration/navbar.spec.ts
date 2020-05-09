describe('Navbar', () => {
  beforeEach(() => {
    cy.visit(Cypress.config().baseUrl);
  });

  it('Contains Menu Icon Button', () => {
    cy.get(':nth-child(1) > .appearance-ghost');
  });

  it('Contains "Audit"', () => {
    cy.get(':nth-child(2) > .appearance-hero');
  });

  it('Contains GitHub Icon Button', () => {
    cy.get(':nth-child(4) > .appearance-ghost');
  });

  it('Clicking the sidebar toggle button opens the sidebar', () => {
    cy.viewport('iphone-5');
    cy.get(':nth-child(1) > .appearance-ghost').click();
    cy.get('nb-sidebar').should('have.class', 'expanded');
  });
});
