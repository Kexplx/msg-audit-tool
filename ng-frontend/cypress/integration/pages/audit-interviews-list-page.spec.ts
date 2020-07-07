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

  context('When focussing on the defaults page...', () => {
    it('shows message that no interview exists on creation', () => {
      // cy.get('[data-cy=faccrit-body]').each(body => {
      //   cy.wrap(body).should('contain', 'Keine Interviews vorhanden');
      // });
    });
  });

  context('When focussing on events it...', () => {
    it('displays a button to new interviews', () => {
      cy.get('[data-cy=new-interview]').click();
      cy.get('[data-cy=add-interview-form]').should('exist');
      cy.get('[data-cy=cancel-interview-data-form]').click();
    });

    it('shows audit contacts list when clicking on contacts tab');
    it('shows audit contacts list when clicking on contacts tab');
  });

  /**
   * Tests the interviews overview of an audit
   */
  context('When focussing on the faccrits ...', () => {
    it('displays an overview with all chosen factors');
    it('displays an overview with all chosen criteria in their parent factor card body');
    it('is expanded on default');
    it('collapses when all interviews have been conducted and their status is finished');
  });

  context('When focussing on the interviews ...', () => {
    it('displays interviews in all of their chosen faccrit bodys');
  });

  context('When focussing on the interview cards it...', () => {
    it('shows Keine Kontaktperson ausgewählt when no contact person was chosen');
    it('shows the first chosen contact person');
    it('shows a (+) if there are more than one contact person');
    it('shows the start date of the interview');
    it('displays status of the interview as status code');
    it('redirects to its interview when clicking on the card');
  });

  context('When focussing on the sidebar it', () => {
    it('shows all faccrits of an audit (scope)');

    it('opens sidebar on click', () => {
      cy.get('[data-cy=toggle-sidebar]').click();
      cy.get('[data-cy=toggle-sidebar]').should('not.have.class', 'collapsed');
      cy.get('[data-cy=toggle-sidebar]').click();
    });

    it('scrolls to the selected question when clicked', () => {
      // cy.get('[data-cy=toggle-sidebar]').click();
      // cy.get('.menu-title').each(item => {
      //   let questionLabel = item.text();
      //   cy.wrap(item).click();
      //   cy.get('[data-cy=factor-card]:visible').contains(questionLabel);
      // });
    });
  });

  context('When focussing on the network request it ...', () => {
    it('shows error message when malformed request received');
    it('shows error message when the network connection/requests failed');
  });
});
