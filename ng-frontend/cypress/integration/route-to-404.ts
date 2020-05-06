import { Chance } from 'chance';

describe('AddAuditForm', () => {
  let baseUrl = 'http://localhost:4200';
  const chance = new Chance();

  beforeEach(() => {
    cy.visit(baseUrl);
  });

  it('Redirects to an 404 error page when a unknown route is requested', () => {
    const testString = chance.string({ alpha: true, numeric: true });
    cy.visit(baseUrl + '/' + testString);
    cy.get('nb-card > nb-card-header').and('contain', 'Sorry');
  });
});
