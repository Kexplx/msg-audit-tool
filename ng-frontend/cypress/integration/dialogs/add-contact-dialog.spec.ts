describe('AddPersonDialog', () => {
  let contactsUrl = Cypress.config().baseUrl + '/contact-persons';
  let testPerson;

  before(() => {
    cy.fixture('user-input-data/example-contact').then(json => {
      testPerson = json;
    });
  });

  /**
   * Tests the contact form
   */
  context('When focussing on the contact person it... ', () => {
    beforeEach(() => {
      cy.injectBackendMocks();
      cy.visit(contactsUrl);
      cy.visit(contactsUrl + '/new');
    });

    it('gives an inputable element for company name', () => {
      cy.get('[data-cy=company-name-input]').clear().type(testPerson.companyName);
      cy.get('[data-cy=company-name-input]').should('have.value', testPerson.companyName);
    });

    it('gives an inputable element for company department', () => {
      cy.get('[data-cy=company-department-input]').clear().type(testPerson.department);
      cy.get('[data-cy=company-department-input]').should('have.value', testPerson.department);
    });

    it('gives an inputable element for corporate division', () => {
      cy.get('[data-cy=company-division-input]').clear().type(testPerson.corporateDivision);
      cy.get('[data-cy=company-division-input]').should('have.value', testPerson.corporateDivision);
    });

    it('gives an inputable element for company sector', () => {
      cy.get('[data-cy=company-sector-input]').clear().type(testPerson.sector);
      cy.get('[data-cy=company-sector-input]').should('have.value', testPerson.sector);
    });

    it('gives the gendered salutations for Herr, Frau, Divers', () => {
      cy.get('[data-cy=contact-salutation-input]').click();
      cy.get('[data-cy=salutation-option]').should('contain', 'Mann');
      cy.get('[data-cy=salutation-option]').should('contain', 'Frau');
      cy.get('[data-cy=salutation-option]').should('contain', 'Divers');
    });

    it('gives inputable element for professional salutation', () => {
      cy.get('[data-cy=contact-title-input]').clear().type(testPerson.title);
      cy.get('[data-cy=contact-title-input]').should('have.value', testPerson.title);
    });

    it('gives inputable element for first name', () => {
      cy.get('[data-cy=contact-forename-input]').clear().type(testPerson.firstName);
      cy.get('[data-cy=contact-forename-input]').should('have.value', testPerson.firstName);
    });

    it('gives inputable element for last name', () => {
      cy.get('[data-cy=contact-surname-input]').clear().type(testPerson.lastName);
      cy.get('[data-cy=contact-surname-input]').should('have.value', testPerson.lastName);
    });

    it('gives inputable element for contact information', () => {
      cy.get('[data-cy=contact-info-input]').clear().type(testPerson.information);
      cy.get('[data-cy=contact-info-input]').should('have.value', testPerson.information);
    });

    it('requires firstname, lastname, companyname, division to be submittable', () => {
      cy.get('[data-cy=submit-data-form]').should('be.disabled');
      cy.get('[data-cy=contact-title-input]').clear().type(testPerson.title);
      cy.get('[data-cy=contact-info-input]').clear().type(testPerson.information);
      cy.get('[data-cy=company-sector-input]').clear().type(testPerson.sector);
      cy.get('[data-cy=company-department-input]').clear().type(testPerson.department);
      cy.get('[data-cy=submit-data-form]').should('be.disabled');
      cy.get('[data-cy=contact-forename-input]').clear().type(testPerson.firstName);
      cy.get('[data-cy=submit-data-form]').should('be.disabled');
      cy.get('[data-cy=contact-surname-input]').clear().type(testPerson.lastName);
      cy.get('[data-cy=submit-data-form]').should('be.disabled');
      cy.get('[data-cy=company-name-input]').clear().type(testPerson.companyName);
      cy.get('[data-cy=submit-data-form]').should('be.disabled');
      cy.get('[data-cy=company-division-input]').clear().type(testPerson.corporateDivision);
      cy.get('[data-cy=submit-data-form]').should('not.be.disabled');
    });
  });

  context('When focussing on the network requests it ...', () => {
    it('builds a valid post request as form', () => {
      cy.injectBackendMocks();
      cy.visit(contactsUrl);
      cy.addPerson(testPerson);
      cy.wait('@postContacts').then(xhr => {
        assert.deepEqual(xhr.request.body, {
          companyName: testPerson.companyName,
          contactInformation: testPerson.information,
          corporateDivision: testPerson.corporateDivision,
          department: testPerson.department,
          forename: testPerson.firstName,
          salutation: testPerson.salutation.toUpperCase(),
          sector: testPerson.sector,
          surname: testPerson.lastName,
          title: testPerson.title,
        });
      });
    });

    it('shows error message when the network connection/requests failed', () => {});
  });
});
