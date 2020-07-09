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

  it('shows "Kontakt" as heading', () => {
    cy.get('[data-cy=heading]').should('contain', 'Kontakt');
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
  context('When focussing on a contact card ...', () => {
    let person;

    beforeEach(() => {
      cy.fixture('backend-mock-data/contactPersons.json').then(persons => {
        person = persons[0];
        cy.route({
          method: 'GET',
          url: '/contact-persons',
          response: [person],
        });
        cy.visit(contactsUrl);
      });
    });

    it('shows the contact name', () => {
      cy.get('[data-cy=contact-name]')
        .first()
        .should('contain', person.forename)
        .should('contain', person.surname);
    });

    it('shows the company name below a label "Unternehmen"', () => {
      cy.get('[data-cy=contact-companyname]').should('contain', 'Unternehmen');
      cy.get('[data-cy=contact-companyname-entry]').first().should('contain', person.companyName);
    });

    it('shows the department below a label "Abteilung"', () => {
      cy.get('[data-cy=contact-department]').should('contain', 'Abteilung');
      cy.get('[data-cy=contact-department-entry]').first().should('contain', person.department);
    });

    it('shows the division below a label "Unternehmensbereich"', () => {
      cy.get('[data-cy=contact-division]').should('contain', 'Unternehmensbereich');
      cy.get('[data-cy=contact-division-entry]')
        .first()
        .should('contain', person.corporateDivision);
    });

    it('shows the sector below a label "Branche"', () => {
      cy.get('[data-cy=contact-sector]').should('contain', 'Branche');
      cy.get('[data-cy=contact-sector-entry]').first().should('contain', person.sector);
    });

    it('shows a "-" if not department was given', () => {
      cy.fixture('backend-mock-data/contactPersons.json').then(persons => {
        person = persons[0];
        delete person.department;
        cy.route({
          method: 'GET',
          url: '/contactpersons',
          response: [person],
        });
        cy.visit(contactsUrl);
      });
      cy.get('[data-cy=missing]').first().should('contain', '-');
    });

    it('shows a "-" if not sector was given', () => {
      cy.fixture('backend-mock-data/contactPersons.json').then(persons => {
        person = persons[0];
        delete person.sector;
        cy.route({
          method: 'GET',
          url: '/contactpersons',
          response: [person],
        });
        cy.visit(contactsUrl);
      });
      cy.get('[data-cy=missing]').first().should('contain', '-');
    });

    it('shows a button to edit the person and opens dialog to edit person when clicked', () => {
      cy.get('[data-cy=edit-person-button]').first().click();
      cy.get('[data-cy=contact-data-form]').should('be.visible');
    });

    it('removes an contact by clicking on an delete button');
  });

  context('When focussing on the contacts it...', () => {
    beforeEach(() => {
      cy.fixture('backend-mock-data/contactPersons.json').then(persons => {
        const person = persons[0];
        const person1 = { ...person };
        person1.id = person1.id + 1;
        const person2 = { ...person };
        person2.id = person2.id + 2;
        cy.route({
          method: 'GET',
          url: '/contactpersons',
          response: [person, person1, person2],
        });
        cy.visit(contactsUrl);
      });
    });

    it('shows all contacts received from the backend', () => {
      cy.get('[data-cy=contact-person-card]').its('length').should('be', 3);
    });

    it('shows number of contacts', () => {
      cy.get('nb-card-header').should('contain', 'Registrierte Kontaktpersonen (3)');
    });
  });

  context('When focussing on the network request it ...', () => {
    it('shows error message when malformed request received');
    it('shows error message when the network connection/requests failed');
  });
});
