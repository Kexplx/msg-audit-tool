import { Chance } from 'chance';

describe('NotFoundPage', () => {
  let baseUrl = Cypress.config().baseUrl;
  const chance = new Chance();

  beforeEach(() => {
    cy.visit(baseUrl);
  });

  it('Redirects to an 404 error page when a unknown route is requested', () => {
    const testString = chance.string({ alpha: true, numeric: true });
    cy.visit(baseUrl + '/' + testString);
    cy.get('[data-cy=not-found-hint]').contains(/Route .* wurde nicht gefunden/);
  });
});
