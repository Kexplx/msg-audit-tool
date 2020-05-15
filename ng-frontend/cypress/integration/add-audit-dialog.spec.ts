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

  it('Form gives inputable element for audit name', () => {
    cy.visit(baseUrl + 'new');
    cy.get('.grid-3-1 > :nth-child(1) > .input-full-width').clear().type(testAudit.name);
    cy.get('.grid-3-1 > :nth-child(1) > .input-full-width').should('have.value', testAudit.name);
  });

  // it('Form gives choosable element for startdate and enddate', () => {
  //   cy.get('.ng-tns-c155-1.ng-star-inserted > .ng-trigger > .item-body > .grid-1-1 > :nth-child(1) > .input-full-width').click();
  //   cy.get('.today > .cell-content').click();
  //   cy.get('.ng-tns-c155-1.ng-star-inserted > .ng-trigger > .item-body > .grid-1-1 > :nth-child(2) > .input-full-width').click();
  //   cy.get('.today > .cell-content').click();
  // });

  it('Clicking on company tab opens accordeon body', () => {
    cy.get(':nth-child(2) > .accordion-item-header-collapsed').click();
    cy.get('.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body').should(
      'contain',
      'Unternehmen',
    );
  });
});
