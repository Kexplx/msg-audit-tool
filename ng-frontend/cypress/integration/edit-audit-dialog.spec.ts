import { Audit } from 'src/app/data/models/audit.model';

describe('EditAuditDialog', () => {
  const baseUrl = Cypress.config().baseUrl;
  const auditsUrl = baseUrl + '/audits';
  let testAudit: Audit;
  let testAuditEdited: Audit;

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

    cy.get('nb-dialog').should('not.exist');
  });

  it('Audit edit formular redirects on button click', () => {
    cy.visit(auditsUrl);
    cy.get('.grid-1-auto-auto > .status-primary').click();
    cy.inputAudit(testAudit);
    cy.get(':nth-child(1) > nb-card > nb-card-header > .appearance-filled').click();
    cy.get('a.ng-tns-c118-11').click();
    cy.url().should('contain', 'edit');
  });

  it('Audit edit formular takes inputs and closes on button click', () => {
    cy.visit(auditsUrl);
    cy.get('.grid-1-auto-auto > .status-primary').click();
    cy.inputAudit(testAuditEdited);
    cy.get('nb-dialog').should('not.exist');
  });

  it('Audit was edited in the audits overview', () => {
    cy.testAuditsOverviewPage(testAuditEdited);
  });

  it('Audit was edited in the concrete audit page', () => {
    cy.get('.content-active > :nth-child(1) > nb-card > nb-card-header').click();
    cy.get('h4').should('contain.text', testAuditEdited.name);
  });

  it('Audit was edited in the concrete audit info page', () => {
    cy.get(':nth-child(2) > .tab-link').click();
    cy.testAuditInfoPage(testAuditEdited);
  });

  it('Audit edit formular redirects on button click on concrete audit page', () => {
    cy.get('.grid-1-auto > .appearance-hero').click();
    cy.get('.menu-title').click();
    cy.url().should('contain', 'edit');
  });

  it('Audit edit formular takes inputs, closes on button click and redirects to concrete audit page', () => {
    cy.inputAudit(testAudit);
    cy.get('nb-dialog').should('not.exist');
    cy.url().should('not.contain', 'edit');
  });

  it('Audit was edited in the concrete audit info page', () => {
    cy.testAuditInfoPage(testAudit);
  });

  it('Audit was edited in the audits overview', () => {
    cy.get(':nth-child(2) > .appearance-hero').click();
    cy.testAuditsOverviewPage(testAudit);
  });
});
