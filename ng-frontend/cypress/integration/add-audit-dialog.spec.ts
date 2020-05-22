describe('AddAuditDialog', () => {
  let baseUrl = Cypress.config().baseUrl + '/audits/';
  let testAudit;

  before(() => {
    cy.fixture('example-audit').then(json => {
      testAudit = json;
    });
  });

  it('Routing to audits/new opens AddAuditForm', () => {
    cy.visit(baseUrl + 'new');
    cy.get('[data-cy=audit-data-form]');
  });

  it('Clicking abbrechen button opens warning message', () => {
    cy.get('[data-cy=cancel-audit-data-form]').click();
    cy.get('[data-cy=discard-back-dialog]').should('exist');
  });

  it('Clicking yes on warning message should close overlay', () => {
    cy.get('[data-cy=discard]').click();
    cy.get('[data-cy=discard-back-dialog]').should('not.exist');
  });

  it('Form gives inputable element for audit name', () => {
    cy.visit(baseUrl + 'new');
    cy.get('[data-cy=audit-name-input]').clear().type(testAudit.name);
    cy.get('[data-cy=audit-name-input]').should('have.value', testAudit.name);
  });

  it('End date cannot be before the start date', () => {
    cy.get('[data-cy=audit-end-input]').click();
    cy.get(
      '[ng-reflect-row="Sun May 17 2020 00:00:00 GMT+0"] > :nth-child(1) > .cell-content',
    ).click();
    cy.get('[data-cy=submit-audit-data-form]').should('be.disabled');
  });

  it('Start and end date can be set', () => {
    cy.on('uncaught:exception', (err, runnable) => {
      expect(err.message).to.include('nebular issue: https://github.com/akveo/nebular/issues/2338');
      done();
      return false;
    });
    cy.get('[data-cy=audit-start-input]').click();
    cy.get('.today > .cell-content').click();
    cy.get('[data-cy=audit-end-input]').click();
    cy.get('.today > .cell-content').click();
    cy.get('[data-cy=submit-audit-data-form]').should('not.be.disabled');
  });

  it('Clicking on company tab opens accordeon body', () => {
    cy.get('[data-cy=audit-customer-data-form]').click();
    cy.get('[data-cy=audit-customer-data-form]').should('contain', 'Unternehmen');
  });

  it('Form gives inputable element for company name', () => {
    cy.get('[data-cy=audit-customer-name-input]').clear().type(testAudit.customerData.name);
    cy.get('[data-cy=audit-customer-name-input]').should('have.value', testAudit.customerData.name);
  });

  it('Form gives inputable element for company department', () => {
    cy.get('[data-cy=audit-customer-department-input]')
      .clear()
      .type(testAudit.customerData.department);
    cy.get('[data-cy=audit-customer-department-input]').should(
      'have.value',
      testAudit.customerData.department,
    );
  });

  it('Form gives inputable element for corporate division', () => {
    cy.get('[data-cy=audit-customer-division-input]')
      .clear()
      .type(testAudit.customerData.corporateDivision);
    cy.get('[data-cy=audit-customer-division-input]').should(
      'have.value',
      testAudit.customerData.corporateDivision,
    );
  });

  it('Form gives inputable element for company sector', () => {
    cy.get('[data-cy=audit-customer-sector-input]').clear().type(testAudit.customerData.sector);
    cy.get('[data-cy=audit-customer-sector-input]').should(
      'have.value',
      testAudit.customerData.sector,
    );
  });

  it('Clicking on contact information tab opens accordeon body', () => {
    cy.get('[data-cy=audit-contact-data-form]').click();
    cy.get('[data-cy=audit-contact-data-form]').should('contain', 'Kontaktdaten');
  });

  it('Form gives the gendered salutations for Herr, Frau, Divers', () => {
    cy.get('[data-cy=audit-contact-salutation-input]').click();
    cy.get('[data-cy=salutation-option]').should('contain', 'Herr');
    cy.get('[data-cy=salutation-option]').should('contain', 'Frau');
    cy.get('[data-cy=salutation-option]').should('contain', 'Divers');
  });

  it('Form gives usable drop down for choosing a gendered salutation for the contact person', () => {
    cy.get('[data-cy=salutation-option]').contains(testAudit.contactPerson.title).click();
  });

  it('Form gives inputable element for professional salutation', () => {
    cy.get('[data-cy=audit-contact-title-input]').clear().type(testAudit.contactPerson.salutation);
    cy.get('[data-cy=audit-contact-title-input]').should(
      'have.value',
      testAudit.contactPerson.salutation,
    );
  });

  it('Form gives inputable element for first name', () => {
    cy.get('[data-cy=audit-contact-firstname-input]')
      .clear()
      .type(testAudit.contactPerson.firstName);
    cy.get('[data-cy=audit-contact-firstname-input]').should(
      'have.value',
      testAudit.contactPerson.firstName,
    );
  });

  it('Form gives inputable element for last name', () => {
    cy.get('[data-cy=audit-contact-lastname-input]').clear().type(testAudit.contactPerson.lastName);
    cy.get('[data-cy=audit-contact-lastname-input]').should(
      'have.value',
      testAudit.contactPerson.lastName,
    );
  });

  it('Form gives inputable element for contact information', () => {
    cy.get('[data-cy=audit-contact-info-input]').clear().type(testAudit.contactPerson.information);
    cy.get('[data-cy=audit-contact-info-input]').should(
      'have.value',
      testAudit.contactPerson.information,
    );
  });

  it('Clicking hinzufügen button closes window', () => {
    cy.get('[data-cy=submit-audit-data-form]').click();
    cy.get('[data-cy=audit-data-form]').should('not.exist');
  });

  it('Clicking hinzufügen should not be possible when no audit name was entered', () => {
    cy.visit(baseUrl + 'new');
    cy.get('[data-cy=submit-audit-data-form]').should('be.disabled');
  });

  it('Adding only a name should be sufficient to enable hinzufügen button', () => {
    cy.get('[data-cy=audit-name-input]').clear().type(testAudit.name);
    cy.get('[data-cy=submit-audit-data-form]').should('not.be.disabled');
  });
});
