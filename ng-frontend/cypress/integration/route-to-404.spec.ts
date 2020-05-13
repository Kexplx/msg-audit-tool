import { Chance } from 'chance';

describe('AddAuditForm', () => {
  let baseUrl = Cypress.config().baseUrl;
  const chance = new Chance();

  beforeEach(() => {
    cy.visit(baseUrl);
  });

  it('Redirects to an 404 error page when a unknown route is requested', () => {
    const testString = chance.string({ alpha: true, numeric: true });
    cy.visit(baseUrl + '/' + testString);
    cy.get(
      'body > app-root > nb-layout > div > div > div > div > div > nb-layout-column > app-not-found > div',
    ).contains(/Route .* wurde nicht gefunden/);
  });
});
