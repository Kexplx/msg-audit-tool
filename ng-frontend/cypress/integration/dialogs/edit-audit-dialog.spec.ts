describe('EditAuditDialog', () => {
  const auditsUrl = Cypress.config().baseUrl + '/audits';
  let testAudit;
  let testAuditEdited;

  before(() => {
    cy.fixture('audits/example-audit').then(json => {
      testAudit = json;
    });
    cy.fixture('audits/example-audit2').then(json => {
      testAuditEdited = json;
    });
  });

  beforeEach(() => {
    cy.server();
    cy.route(auditsUrl);
  });

  it('does not show an edit dialog when audit id is invalid', () => {
    cy.visit(auditsUrl);
    cy.visit(auditsUrl + '/kaputt/edit');
    cy.get('[data-cy=audit-data-form]').should('not.exist');
    cy.visit(auditsUrl + '/kaputt/infos/edit');
    cy.get('[data-cy=audit-data-form]').should('not.exist');
  });

  it('shows an edit formular that takes inputs and closes on button click', () => {
    editAuditFromAuditsOverview(testAudit, testAuditEdited);
    cy.get('[data-cy=audit-data-form]').should('not.exist');
  });

  /**
   * Simulates an edit from the audits overview.
   * 
   * @param testAudit 
   * @param testAuditEdited 
   */
  function editAuditFromAuditsOverview(testAudit, testAuditEdited) {
    cy.addAudit(testAudit);
    cy.get('[data-cy=audit-options]').first().click();
    cy.contains('Bearbeiten').click();
    cy.url().should('contain', 'edit');
    cy.inputAudit(testAuditEdited);
  }

  /**
   * Simulates an edit from the audit information page.
   * 
   * @param testAudit 
   * @param testAuditEdited 
   */
  function editAuditFromInfoPage(testAudit, testAuditEdited) {
    cy.addAudit(testAudit);
    cy.get('[data-cy=audit-short-infos]').first().click();
    cy.contains('infos').click();
    cy.get('[data-cy=audit-options]').click();
    cy.inputAudit(testAuditEdited);
  }

  /**
   *  Tests the consistency of audit information when edited at different pages
   */
  [
    { text: 'from the audits list overview', func: editAuditFromAuditsOverview },
    { text: 'from the audit info page', func: editAuditFromInfoPage },
  ].forEach(beforeFunc => {
    context('When an audit was edited ' + beforeFunc.text + ' it ...', () => {
      before(() => {
        beforeFunc.func(testAudit, testAuditEdited);
      });

      beforeEach(() => {
        cy.server();
        cy.route(auditsUrl);
      });

      it('populates the audits list overview page with consistent edited information', () => {
        cy.testAuditListEntry(testAuditEdited);
      });

      it('populates the concrete audit page with consistent edited information', () => {
        cy.get('[data-cy=audit-short-infos]').first().click();
        cy.get('[data-cy=audit-short-infos]').should('contain.text', testAuditEdited.name);
        cy.contains('infos').click();
        cy.testAuditInfoPage(testAuditEdited);
      });
    });
  });
});
