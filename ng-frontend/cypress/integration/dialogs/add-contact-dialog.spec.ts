describe('AddPersonDialog', () => {
  let contactsUrl = Cypress.config().baseUrl + '/contact-people';
  let isoConstants;
  let testAudit;

  before(() => {
    cy.fixture('audits/example-audit').then(json => {
      testAudit = json;
    });
    cy.fixture('iso-constants/factors-criteria.json').then(json => {
      isoConstants = json;
    });
  });

  /**
   * Tests the company information form
   */
  context('When focussing on the contact person it... ', () => {
    before(() => {
      cy.visit(contactsUrl + '/new');
    });

    it('gives an inputable element for company name', () => {
      cy.get('[data-cy=company-name-input]').clear().type(testAudit.customerData.name);
      cy.get('[data-cy=company-name-input]').should('have.value', testAudit.customerData.name);
    });

    it('gives an inputable element for company department', () => {
      cy.get('[data-cy=company-department-input]').clear().type(testAudit.customerData.department);
      cy.get('[data-cy=company-department-input]').should(
        'have.value',
        testAudit.customerData.department,
      );
    });

    it('gives an inputable element for corporate division', () => {
      cy.get('[data-cy=company-division-input]')
        .clear()
        .type(testAudit.customerData.corporateDivision);
      cy.get('[data-cy=company-division-input]').should(
        'have.value',
        testAudit.customerData.corporateDivision,
      );
    });

    it('gives an inputable element for company sector', () => {
      cy.get('[data-cy=company-sector-input]').clear().type(testAudit.customerData.sector);
      cy.get('[data-cy=company-sector-input]').should('have.value', testAudit.customerData.sector);
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
      cy.get('[data-cy=salutation-option]').contains(testAudit.contactPerson.title).click();
    });

    it('gives inputable element for professional salutation', () => {
      cy.get('[data-cy=contact-title-input]').clear().type(testAudit.contactPerson.salutation);
      cy.get('[data-cy=contact-title-input]').should(
        'have.value',
        testAudit.contactPerson.salutation,
      );
    });

    it('gives inputable element for first name', () => {
      cy.get('[data-cy=contact-firstname-input]').clear().type(testAudit.contactPerson.firstName);
      cy.get('[data-cy=contact-firstname-input]').should(
        'have.value',
        testAudit.contactPerson.firstName,
      );
    });

    it('gives inputable element for last name', () => {
      cy.get('[data-cy=contact-lastname-input]').clear().type(testAudit.contactPerson.lastName);
      cy.get('[data-cy=contact-lastname-input]').should(
        'have.value',
        testAudit.contactPerson.lastName,
      );
    });

    it('gives inputable element for contact information', () => {
      cy.get('[data-cy=contact-info-input]').clear().type(testAudit.contactPerson.information);
      cy.get('[data-cy=contact-info-input]').should(
        'have.value',
        testAudit.contactPerson.information,
      );
    });
  });

  //TODO Test consistency of person data in different views
  context('When a contact was added it...', () => {
    before(() => {
      cy.visit(contactsUrl);
      cy.visit(contactsUrl + '/new');
      cy.inputPerson(testAudit);
    });

    // it('populates the people-list', () => {

    // });

    // it('populates the concrete audit info page with consistent edited information', () => {
    //   cy.get('[data-cy=audit-short-infos]').first().click();
    //   cy.get('[data-cy=audit-short-infos]').should('contain.text', testAuditEdited.name);
    //   cy.contains('infos').click();
    //   cy.testAuditInfoPage(testAuditEdited);
    // });

    // it('adds the person to the audit dialog', () => {});

    // it('adds the person ro the interview dialog', () => {});
  });
});
