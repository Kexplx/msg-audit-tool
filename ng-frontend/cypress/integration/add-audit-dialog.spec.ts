import { Audit } from 'src/app/data/models/audit.model';

describe('AddAuditDialog', () => {
  let baseUrl = Cypress.config().baseUrl + '/audits/';
  let testAudit: Audit;

  before(() => {
    cy.fixture('example-audit').then(json => {
      testAudit = json;
    });
  });

  it('Routing to audits/new opens AddAuditForm', () => {
    cy.visit(baseUrl + 'new');
    cy.get('nb-dialog-container');
  });

  it('Clicking abbrechen button opens warning message', () => {
    cy.get('.accordion-footer > .status-basic').click();
    cy.get('nb-dialog-container > .ng-star-inserted > nb-card > nb-card-header').should('exist');
  });
});
