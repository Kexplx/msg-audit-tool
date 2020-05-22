describe('EditAuditDialog', () => {
  const baseUrl = Cypress.config().baseUrl;
  const auditsUrl = baseUrl + '/audits';
  let testAudit;
  let testAuditEdited;

  before(() => {
    cy.fixture('example-audit').then(json => {
      testAudit = json;
    });
    cy.fixture('example-audit2').then(json => {
      testAuditEdited = json;
    });
  });

  it('Routing to audits/edit on invalid id closes dialog', () => {
    cy.visit(auditsUrl);
    cy.visit(auditsUrl + '/123/edit');

    cy.get('[data-cy=audit-data-form]').should('not.exist');
  });

  it('Audit edit formular redirects on button click', () => {
    cy.visit(auditsUrl);
    cy.get('[data-cy=new-audit]').click();
    cy.inputAudit(testAudit);
    cy.get('[data-cy=audit-options]').first().click();
    cy.contains('Bearbeiten').click();
    cy.url().should('contain', 'edit');
  });

  it('Audit edit formular takes inputs and closes on button click', () => {
    cy.visit(auditsUrl);
    cy.get('[data-cy=new-audit]').click();
    cy.inputAudit(testAuditEdited);
    cy.get('[data-cy=audit-data-form]').should('not.exist');
  });

  it('Audit was edited in the audits overview', () => {
    cy.testAuditListEntry(testAuditEdited);
  });

  it('Audit was edited in the concrete audit page', () => {
    cy.get('[data-cy=audit-short-infos]').first().click();
    cy.get('[data-cy=audit-short-infos]').should('contain.text', testAuditEdited.name);
  });

  it('Audit was edited in the concrete audit info page', () => {
    cy.contains('Info').click();
    cy.testAuditInfoPage(testAuditEdited);
  });

  it('Audit edit formular redirects on button click on concrete audit page', () => {
    cy.get('[data-cy=audit-options]').click();
    cy.contains('Bearbeiten').click();
    cy.url().should('contain', 'edit');
  });

  it('Audit edit formular takes inputs, closes on button click and redirects to concrete audit page', () => {
    cy.inputAudit(testAudit);
    cy.get('[data-cy=audit-data-form]').should('not.exist');
    cy.url().should('not.contain', 'edit');
  });

  it('Audit was edited in the concrete audit info page', () => {
    cy.testAuditInfoPage(testAudit);
  });

  it('Audit was edited in the audits overview', () => {
    cy.get(':nth-child(2) > .appearance-hero').click();
    cy.testAuditListEntry(testAudit);
  });
});
