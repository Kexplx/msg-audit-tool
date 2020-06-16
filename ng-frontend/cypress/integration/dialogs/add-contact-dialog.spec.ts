describe('AddPersonDialog', () => {
  let contactsUrl = Cypress.config().baseUrl + '/contact-people';
  let testPerson;

  before(() => {
    cy.fixture('contacts/example-contact').then(json => {
      testPerson = json;
    });
    cy.visit(contactsUrl);
  });

  /**
   * Tests the contact form
   */
  context('When focussing on the contact person it... ', () => {
    before(() => {
      cy.visit(contactsUrl + '/new');
    });

    after(() => {
      cy.get('[data-cy=cancel-audit-data-form]').click();
      cy.get('[data-cy=discard]').click();
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
      cy.get('[data-cy=salutation-option]').should('contain', 'Herr');
      cy.get('[data-cy=salutation-option]').should('contain', 'Frau');
      cy.get('[data-cy=salutation-option]').should('contain', 'Divers');
    });

    it('gives usable drop down for choosing a gendered salutation for the contact person', () => {
      cy.get('[data-cy=salutation-option]').should('contain', 'Herr');
      cy.get('[data-cy=salutation-option]').should('contain', 'Frau');
      cy.get('[data-cy=salutation-option]').should('contain', 'Divers');
      cy.get('[data-cy=salutation-option]').contains(testPerson.salutation).click();
    });

    it('gives inputable element for professional salutation', () => {
      cy.get('[data-cy=contact-title-input]').clear().type(testPerson.title);
      cy.get('[data-cy=contact-title-input]').should('have.value', testPerson.title);
    });

    it('gives inputable element for first name', () => {
      cy.get('[data-cy=contact-firstname-input]').clear().type(testPerson.firstName);
      cy.get('[data-cy=contact-firstname-input]').should('have.value', testPerson.firstName);
    });

    it('gives inputable element for last name', () => {
      cy.get('[data-cy=contact-lastname-input]').clear().type(testPerson.lastName);
      cy.get('[data-cy=contact-lastname-input]').should('have.value', testPerson.lastName);
    });

    it('gives inputable element for contact information', () => {
      cy.get('[data-cy=contact-info-input]').clear().type(testPerson.information);
      cy.get('[data-cy=contact-info-input]').should('have.value', testPerson.information);
    });
  });

  /**
   * Tests consistency of added data
   */
  context('When a contact was added it...', () => {
    before(() => {
      cy.addPerson(testPerson);
    });

    beforeEach(() => {
      cy.get('[data-cy=home]').click();
    });

    it('populates the people-list', () => {
      cy.get('[data-cy=contacts]').click();
      cy.get('[data-cy=contact-name]').last().should('contain', testPerson.firstName);
      cy.get('[data-cy=contact-name]').last().should('contain', testPerson.lastName);
      cy.get('[data-cy=contact-person-card]').last().should('contain', testPerson.companyName);
      cy.get('[data-cy=contact-person-card]')
        .last()
        .should('contain', testPerson.corporateDivision);
      cy.get('[data-cy=contact-person-card]').last().should('contain', testPerson.sector);
      cy.get('[data-cy=contact-person-card]').last().should('contain', testPerson.department);
    });

    // it('populates the concrete audit info page with consistent edited information', () => {
    //   cy.get('[data-cy=audit-short-infos]').first().click();
    //   cy.get('[data-cy=audit-short-infos]').should('contain.text', testAuditEdited.name);
    //   cy.contains('infos').click();
    //   cy.testAuditInfoPage(testAuditEdited);
    // });

    it('adds the person to the audit dialog contact picker', () => {
      cy.get('[data-cy=new-audit]').click();
      cy.get('[data-cy=audit-contacts]').click();
      cy.get('[data-cy=audit-contact]').last().should('contain', testPerson.firstName);
      cy.get('[data-cy=audit-contact]').last().should('contain', testPerson.lastName);
      cy.get('[data-cy=audit-contacts]').click();
      cy.get('[data-cy=cancel-audit-data-form]').click();
      cy.get('[data-cy=discard]').click();
    });

    // it('adds the person to the interview dialog', () => {});
  });
});
