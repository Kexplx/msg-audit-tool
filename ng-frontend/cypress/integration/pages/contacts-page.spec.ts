describe('ContactsPage', () => {
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
  context('When an contact was added it ...', () => {
    before(() => {
      // TODO add contact (tests below work because of the dummy contacts)
    });

    it('shows an contact entry when added by an user', () => {
      cy.get('[data-cy=contact-person-card]').should('exist');
      cy.get('[data-cy=contact-name]').should('exist');
      cy.get('[data-cy=contact-companyname]').should('exist');
      cy.get('[data-cy=contact-department]').should('exist');
      cy.get('[data-cy=contact-division]').should('exist');
      cy.get('[data-cy=contact-sector]').should('exist');
    });

    it('shows a button to edit the person and opens dialog to edit person when clicked', () => {
      cy.get('[data-cy=edit-person-button]').first().click();
      cy.get('[data-cy=contact-data-form]').should('be.visible');
    });

    // it('removes an contact by clicking on an audit delete button', () => {
    //   cy.get('[data-cy=contact-person-card]').first().as('first_contact');
    //   cy.get('[data-cy=delete-person-button]').first().click();
    //   cy.get('[data-cy=contact-person-card]').first().should('not.be', '@first_contact');
    // });
  });
});
