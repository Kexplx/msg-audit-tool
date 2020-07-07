describe('ContactsListPage', () => {
  let contactsUrl = Cypress.config().baseUrl + '/contact-persons';

  beforeEach(() => {
    cy.injectBackendMocks();
    cy.visit(contactsUrl);
  });

  it('redirects to /contact-persons when visiting the ', () => {
    cy.visit(contactsUrl).then(() => {
      cy.url().should('contain', '/contact-persons');
    });
  });

  it('shows Kontakt as heading');

  /**
   * Testing buttons and other triggable events
   */
  context('When focussing on events it...', () => {
    it('opens the window to add a person with a click on the new contacts button', () => {
      cy.get('[data-cy=new-contact]').click();
      cy.get('[data-cy=contact-data-form]').should('exist');
    });
  });

  /**
   * Testing an contacts card layout
   */
  context('When focussing on a contact card ...', () => {
    it('shows the contact name');
    it('shows the company name');
    it('shows the department');
    it('shows the division');
    it('shows the sector');

    it('shows a button to edit the person and opens dialog to edit person when clicked', () => {
      cy.get('[data-cy=edit-person-button]').first().click();
      cy.get('[data-cy=contact-data-form]').should('be.visible');
    });

    it('removes an contact by clicking on an delete button');
  });

  context('When focussing on the contacts it...', () => {
    it('shows all contacts received from the backend');
    it('shows number of contacts');
  });

  context('When focussing on the network request it ...', () => {
    it('shows error message when malformed request received');
    it('shows error message when the network connection/requests failed');
  });
});
