import { factors } from '../../../src/app/core/data/factors';

describe('AuditPage', () => {
  let baseUrl = Cypress.config().baseUrl;
  let testAudit;
  let testAuditUrl;

  before(() => {
    cy.fixture('audits/example-audit')
      .then(json => {
        testAudit = json;
      })
      .then(() => {
        cy.visit(baseUrl);
        cy.get('[data-cy=new-audit]').click();
        cy.inputAudit(testAudit);
        cy.get('[data-cy=audit-short-infos]').first().click();
        cy.url().then(url => {
          testAuditUrl = url;
        });
      });
  });

  beforeEach(() => {
    cy.server();
    cy.route(testAuditUrl);
  });

  it('generates a valid audit page with a unique id after adding an audit', () => {
    cy.url().should('contain', 'interviews');
  });

  context('When in interview overview it ...', () => {
    before(() => {
      cy.server();
      cy.route(testAuditUrl);
    });

    it('displays an overview with all chosen factors', () => {
      for (let i = 0; i < factors.length; i++) {
        let factor_obj = `:nth-child(${i + 1}) > nb-card-header`;
        cy.get(factor_obj).should('contain.text', factors[i].title);
      }
    });

    it('displays a button to new interviews', () => {
      cy.get('[data-cy=new-interview]').click();
      cy.get('[data-cy=add-interview-form]').should('exist');
      cy.get('[data-cy=cancel-interview-data-form]').click();
    });

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

  context('When on info page it ...', () => {
    before(() => {
      cy.server();
      cy.route(testAuditUrl);
      cy.contains('info').click();
    });

    it('generates a valid audit info page displaying audit information', () => {
      cy.testAuditInfoPage(testAudit);
    });

    it('shows a button to edit the audit information', () => {
      cy.get('[data-cy=audit-options]').click();
      cy.url().should('contain', 'edit');
    });
  });
});
