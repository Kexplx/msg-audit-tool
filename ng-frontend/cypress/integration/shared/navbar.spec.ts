// describe('Navbar', () => {
//   beforeEach(() => {
//     cy.visit(Cypress.config().baseUrl);
//   });

//   it('contains Menu Icon Button', () => {
//     cy.get('[data-cy=toggle-sidebar]');
//   });

//   it('contains "Audit"', () => {
//     cy.get('[data-cy=home]');
//   });

//   it('contains GitHub Icon Button', () => {
//     cy.get('[data-cy=github]');
//   });

//   it('opens the sidebar when clicking the sidebar toggle button', () => {
//     cy.viewport('iphone-5');
//     cy.get('[data-cy=toggle-sidebar]').click();
//     cy.get('[data-cy=sidebar]').should('have.class', 'expanded');
//   });
// });
