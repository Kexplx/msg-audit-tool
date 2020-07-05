/**
 * Tests the dialog to edit an audit.
 *
 * Hint: Since the dialogs for editing and adding audits are the same, we don't test for the
 * form here explicitly. These explicit tests can be found in the add-audit-dialog-tests.
 * The form input is done the same way in both edit/add-testsgroups with
 * cy.addAudit() so it will implicitely guarantee the form is inputtable and working.
 */
describe('EditAuditDialog', () => {
  const auditsUrl = Cypress.config().baseUrl + '/audits';

  beforeEach(() => {
    cy.injectBackendMocks();
  });

  it('does not show an edit dialog when audit id is invalid', () => {
    cy.visit(auditsUrl);
    cy.visit(auditsUrl + '/kaputt/edit');
    cy.get('[data-cy=audit-data-form]').should('not.exist');
    cy.visit(auditsUrl + '/kaputt/infos/edit');
    cy.get('[data-cy=audit-data-form]').should('not.exist');
  });

  context('When focussing on the existing form content it ...', () => {
    it('shows the correct audit name', () => {});
    it('shows the correct start and end date', () => {});
    it('shows the correct contact person(s)', () => {});
    it('shows the correct scope', () => {});
  });

  context('When focussing on the network request it ...', () => {
    it('builds a valid post request as form', () => {});
    it('shows error message when the network connection/requests failed', () => {});
  });
});
