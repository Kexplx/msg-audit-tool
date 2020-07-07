describe('AuditInterviewsListPage', () => {
  let baseUrl = Cypress.config().baseUrl;

  beforeEach(() => {
    cy.injectBackendMocks();
    cy.visit(baseUrl);
    cy.get('[data-cy=audit-short-infos]').first().click();
  });

  it('generates a valid audit page with a unique id after adding an audit', () => {
    cy.url().should('contain', 'interviews');
  });

  it('shows audit name as heading');
  it('shows audit start date as subheading');
  it('shows audit end date as subheading');

  context('When focussing on a contact card ...', () => {
    it('shows the contact name');
    it('shows the company name');
    it('shows the department');
    it('shows the division');
    it('shows the sector');
  });

  context('When focussing on the contacts it...', () => {
    it('shows all contacts of the audit');
  });

  context('When focussing on the defaults page...', () => {
    it('shows message that no interview exists on creation', () => {
      // cy.get('[data-cy=faccrit-body]').each(body => {
      //   cy.wrap(body).should('contain', 'Keine Interviews vorhanden');
      // });
    });
  });

  context('When focussing on events it...', () => {
    it('displays a button to edit the audit');
    it('shows audit contacts list when clicking on contacts tab');
    it('shows audit contacts list when clicking on contacts tab');
  });

  context('When focussing on the sidebar it', () => {
    it('shows an empty sidebar');
  });

  context('When focussing on the network request it ...', () => {
    it('shows error message when malformed request received');
    it('shows error message when the network connection/requests failed');
  });
});
