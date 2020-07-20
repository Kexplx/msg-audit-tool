describe('AddPersonDialog', () => {
  let contactsUrl = Cypress.config().baseUrl + '/contact-persons';
  let testPerson;

  before(() => {
    cy.fixture('user-input-data/example-contact').then(json => {
      testPerson = json;
    });
  });

  context('When focussing on the contact person it... ', () => {
    beforeEach(() => {
      cy.injectBackendMocks();
      cy.visit(contactsUrl).visit(contactsUrl + '/new');
    });

    it('gives an inputable element for company name', () => {
      cy.get('[data-cy=company-name-input]')
        .type(testPerson.companyName)
        .should('have.value', testPerson.companyName);
    });

    it('gives an inputable element for company department', () => {
      cy.get('[data-cy=company-department-input]')
        .type(testPerson.department)
        .should('have.value', testPerson.department);
    });

    it('gives an inputable element for corporate division', () => {
      cy.get('[data-cy=company-division-input]')
        .type(testPerson.corporateDivision)
        .should('have.value', testPerson.corporateDivision);
    });

    it('gives an inputable element for company sector', () => {
      cy.get('[data-cy=company-sector-input]')
        .type(testPerson.sector)
        .should('have.value', testPerson.sector);
    });

    it('gives the gendered salutations for Herr, Frau, Divers', () => {
      cy.get('[data-cy=contact-salutation-input]').click();
      cy.get('[data-cy=salutation-option]')
        .should('contain', 'Herr')
        .should('contain', 'Frau')
        .should('contain', 'Divers');
    });

    it('gives inputable element for professional salutation', () => {
      cy.get('[data-cy=contact-title-input]')
        .type(testPerson.title)
        .should('have.value', testPerson.title);
    });

    it('gives inputable element for first name', () => {
      cy.get('[data-cy=contact-forename-input]')
        .type(testPerson.firstName)
        .should('have.value', testPerson.firstName);
    });

    it('gives inputable element for last name', () => {
      cy.get('[data-cy=contact-surname-input]')
        .type(testPerson.lastName)
        .should('have.value', testPerson.lastName);
    });

    it('gives inputable element for contact information', () => {
      cy.get('[data-cy=contact-info-input]')
        .type(testPerson.information)
        .should('have.value', testPerson.information);
    });

    it('requires firstname, lastname, companyname, division to be submittable', () => {
      cy.get('[data-cy=submit-data-form]').should('be.disabled');
      cy.get('[data-cy=contact-title-input]')
        .type(testPerson.title)
        .get('[data-cy=contact-info-input]')
        .type(testPerson.information)
        .get('[data-cy=company-sector-input]')
        .type(testPerson.sector)
        .get('[data-cy=company-department-input]')
        .type(testPerson.department)
        .get('[data-cy=submit-data-form]')
        .should('be.disabled');
      cy.get('[data-cy=contact-forename-input]')
        .type(testPerson.firstName)
        .get('[data-cy=submit-data-form]')
        .should('be.disabled');
      cy.get('[data-cy=contact-surname-input]')
        .type(testPerson.lastName)
        .get('[data-cy=submit-data-form]')
        .should('be.disabled');
      cy.get('[data-cy=company-name-input]')
        .type(testPerson.companyName)
        .get('[data-cy=submit-data-form]')
        .should('be.disabled');
      cy.get('[data-cy=company-division-input]')
        .type(testPerson.corporateDivision)
        .get('[data-cy=submit-data-form]')
        .should('not.be.disabled');
    });
  });

  context('When focussing on the network requests it ...', () => {
    it('builds a valid post request as form', () => {
      cy.injectBackendMocks();
      cy.visit(contactsUrl);
      cy.get('[data-cy=contacts]').click();
      cy.get('[data-cy=new-contact]').click();
      cy.get('[data-cy=company-name-input]')
        .filter(':visible')
        .clear()
        .type(testPerson.companyName);
      cy.get('[data-cy=company-department-input]')
        .filter(':visible')
        .clear()
        .type(testPerson.department);
      cy.get('[data-cy=company-division-input]')
        .filter(':visible')
        .clear()
        .type(testPerson.corporateDivision);
      cy.get('[data-cy=company-sector-input]').filter(':visible').clear().type(testPerson.sector);
      cy.get('[data-cy=contact-salutation-input]').click();
      cy.get('[data-cy=salutation-option]').contains(testPerson.salutation).click();
      cy.get('[data-cy=contact-title-input]').filter(':visible').clear().type(testPerson.title);
      cy.get('[data-cy=contact-forename-input]')
        .filter(':visible')
        .clear()
        .type(testPerson.firstName);
      cy.get('[data-cy=contact-surname-input]')
        .filter(':visible')
        .clear()
        .type(testPerson.lastName);
      if (testPerson.information) {
        cy.get('[data-cy=contact-info-input]').clear().type(testPerson.information);
      }
      cy.get('[data-cy=submit-data-form]').click();
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
  });
});
