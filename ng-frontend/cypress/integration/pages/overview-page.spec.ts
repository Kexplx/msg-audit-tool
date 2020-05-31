import { AddAudit } from "src/app/core/ngxs/audit.actions";

describe('OverviewPage', () => {
  let auditsUrl = Cypress.config().baseUrl + '/audits';

  before(() => {
    cy.visit(auditsUrl);
  });

  it('redirects to /audits when visiting the baseUrl', () => {
    cy.visit(Cypress.config().baseUrl).then(() => {
      cy.url().should('contain', '/audits');
    });
  });

  /**
   * Testing buttons and other triggable events
   */
  context('When focussing on events it...', () => {
    before(() => {
      cy.visit(auditsUrl);
    });

    it('opens the window to add an audit with a click on the new audits button', () => {
      cy.get('[data-cy=new-audit]').click();
      cy.get('[data-cy=audit-data-form]').should('exist');
    });
  });

  /**
   * Testing an audit card layout
   */
  context('When an audit was added it ...', () => {
    let testAudit = { name: 'Test' };
    before(() => {
      cy.visit(auditsUrl);
      cy.addAudit(testAudit);
    });

    it('shows an audit entry on the overview page when added by an user', () => {
      cy.get('[data-cy=audit-short-infos]').should('exist');
    });

    it('shows a button to see further options to do with the audit', () => {
      cy.get('[data-cy=audit-options]').should('exist');
    });

    it('redirects to audit page when clicking on an audit ', () => {
      cy.get('[data-cy=audit-short-infos]').first().click();
      cy.url().should('contain', 'interview');
      cy.get('[data-cy=home]').click();
    });

    it('removes an audit by clicking on an audit delete button', () => {
      let testAuditUrl;
      cy.get('[data-cy=audit-short-infos]').first().click();
      cy.url().then(url => {
        testAuditUrl = url;
      });
      cy.get('[data-cy=home]').click();
      cy.get('[data-cy=audit-options]').first().click();
      cy.get('.menu-title').filter(':visible').filter(':contains("Löschen")').click();
      cy.get('[data-cy=audits-list]').first().click();
      cy.url().should('not.equal', testAuditUrl);
    });

    it.only('sorts the audits descending by creationDate', () => {
      let auditsToAdd = [
        { name: 'Test1' },
        { name: 'Test2' },
        { name: 'Test3' },
        { name: 'Test4' },
      ];

      auditsToAdd.forEach((audit) => {
        cy.addAudit(audit);
      });

      auditsToAdd.reverse();

      cy.get('[data-cy=audit-card]').each((el, index) => {
        if (index < auditsToAdd.length) {
          cy.wrap(el).should('contain', auditsToAdd[index].name);
        }
      });
    });
  });
});
