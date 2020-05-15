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

  it('Clicking yes on warning message should close overlay', () => {
    cy.get('.card-footer > :nth-child(2)').click();
    cy.get('#cdk-overlay-0 > nb-dialog-container > app-add-audit-dialog > div > form').should(
      'not.exist',
    );
  });
});
