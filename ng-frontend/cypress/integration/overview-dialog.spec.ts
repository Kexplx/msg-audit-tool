describe('OverviewDialog', () => {
  let baseUrl = Cypress.config().baseUrl;
  const testAudit = {
    name: 'BankAudit',
    contactPerson: {
      firstName: 'Max',
      lastName: 'Mustermann',
      information: '+123456789',
      title: 'Herr',
      salutation: 'Dr.',
    },
    customerData: {
      department: 'HR',
      name: 'TestBank',
      sector: 'Banking',
      corporateDivision: 'Human Resources',
    },
  };

  const testAuditEdited = {
    name: 'InvestmentBankAudit',
    start: true,
    end: true,
    contactPerson: {
      firstName: 'Anna',
      lastName: 'Mustermann',
      information: 'anna@investmentbank.o',
      title: 'Frau',
      salutation: 'Dr. Dr. hab.',
    },
    customerData: {
      department: 'IT',
      name: 'TestInvestmentBank',
      sector: 'SuperBanking',
      corporateDivision: 'Alien Resources',
    },
  };

  function inputAudit(testAudit) {
    // Input Audit name, start date, end date and open next collapsed accordeon through click
    cy.get('.grid-3-1 > :nth-child(1) > .input-full-width').clear().type(testAudit.name);
    if (testAudit.start) {
      cy.get('.grid-1-1 > :nth-child(1) > .input-full-width').click();
      cy.get('.today > .cell-content').click();
    }
    if (testAudit.end) {
      cy.get('.grid-1-1 > :nth-child(2) > .input-full-width').click();
      cy.get('.today > .cell-content').click();
    }
    cy.get(':nth-child(2) > .accordion-item-header-collapsed').click();

    // Input Customer data and open next collapsed accordeon through click
    cy.get(
      '.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body > :nth-child(1) > :nth-child(1) > .input-full-width',
    )
      .clear()
      .type(testAudit.customerData.name);
    cy.get(
      '.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body > :nth-child(1) > :nth-child(2) > .input-full-width',
    )
      .clear()
      .type(testAudit.customerData.department);
    cy.get(
      '.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body > :nth-child(2) > :nth-child(1) > .input-full-width',
    )
      .clear()
      .type(testAudit.customerData.corporateDivision);
    cy.get(
      '.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body > :nth-child(2) > :nth-child(2) > .input-full-width',
    )
      .clear()
      .type(testAudit.customerData.sector);
    cy.get(':nth-child(3) > .accordion-item-header-collapsed').click();

    // Input Contact Information and open next collapsed accordeon through click
    cy.get(':nth-child(1) > .appearance-outline > .select-button').click();
    cy.get('nb-option').contains(testAudit.contactPerson.title).click();
    cy.get(
      '.ng-tns-c155-5.ng-star-inserted > .ng-trigger > .item-body > :nth-child(1) > :nth-child(2) > .input-full-width',
    )
      .clear()
      .type(testAudit.contactPerson.salutation);
    cy.get(
      '.ng-tns-c155-5.ng-star-inserted > .ng-trigger > .item-body > :nth-child(2) > :nth-child(1) > .input-full-width',
    )
      .clear()
      .type(testAudit.contactPerson.firstName);
    cy.get(
      '.ng-tns-c155-5.ng-star-inserted > .ng-trigger > .item-body > :nth-child(2) > :nth-child(2) > .input-full-width',
    )
      .clear()
      .type(testAudit.contactPerson.lastName);
    cy.get('.field-item-contact-information > .input-full-width')
      .clear()
      .type(testAudit.contactPerson.information);
    cy.get('.accordion-footer > .status-primary').click();
  }

  function checkAuditHeader(testAudit) {
    // Verify that an Audit is showing and that it contains the correct Audit name and Company Name
    cy.get(':nth-child(1) > nb-card > nb-card-header').contains(testAudit.name);
    cy.get(':nth-child(1) > nb-card > nb-card-header').contains(testAudit.customerData.name);
    if (testAudit.start || testAudit.end) {
      cy.get(':nth-child(1) > nb-card > nb-card-header').should('exist');
    }
  }

  function checkAuditStatusLabel(testAudit) {
    cy.get('.banner-status').should('exist');
  }

  it('Opens the window to add an audit with a click on the new audits button', () => {
    cy.get('.grid-1-auto-auto > .status-primary').click();
    cy.get('nb-dialog-container').should('exist');
  });

  it('Shows an audit entry on the overview page when added by an user', () => {
    inputAudit(testAudit);
    // Verify that an Audit exists
    cy.get('nb-card > nb-card-header').should('exist');
  });

  it('Adds the correct audit name and company name entered by the user to the overview', () => {
    checkAuditHeader(testAudit);
  });

  it('Shows the audit status label', () => {
    checkAuditStatusLabel(testAudit);
  });

  it('Shows a button to see further options to do with the audit', () => {
    cy.get(':nth-child(1) > nb-card > nb-card-header > .appearance-filled').should('exist');
  });
});
