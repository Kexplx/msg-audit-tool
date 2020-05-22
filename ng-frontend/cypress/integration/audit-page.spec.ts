import { factors } from '../../src/app/core/data/factors';

describe('AuditPage', () => {
  let baseUrl = Cypress.config().baseUrl;
  let testAudit;
  let testAuditUrl;

  before(() => {
    cy.visit(baseUrl);
    cy.fixture('example-audit').then(json => {
      testAudit = json;
    });
  });

  it('Should generate a valid test page with a unique id after adding an audit', () => {
    cy.get('[data-cy=new-audit]').click();
    cy.inputAudit(testAudit);
    cy.get('[data-cy=audit-short-infos]').first().click();
    cy.url().should('contain', 'overview');
    testAuditUrl = cy.url();
  });

  it('Should generate a valid info page displaying audit information', () => {
    cy.contains('Info').click();
    cy.testAuditInfoPage(testAudit);
  });

  // it('Should display an overview with all chosen factors', () => {
  //   cy.get(':nth-child(1) > .tab-link').click();
  //   for (let i = 0; i < factors.length; i++) {
  //     let factor_obj = `:nth-child(${i + 1}) > nb-card-header`;
  //     cy.get(factor_obj).should('contain.text', factors[i].title);
  //   }
  // });

  // it('Should display an overview with all chosen categories', () => {
  //   cy.get(':nth-child(1) > .tab-link').click();
  //   for (let i = 0; i < factors.length; i++) {
  //     const categories = factors[i].categories;
  //     cy.get(`:nth-child(${i + 1}) > nb-list > nb-list-item`).should(
  //       'contain.text',
  //       categories[0].title,
  //     );
  //   }
  // });
});
