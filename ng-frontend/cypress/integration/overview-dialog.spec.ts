describe('OverviewDialog', () => {
  let baseUrl = Cypress.config().baseUrl;
  const testAudit = {
    name: 'BankAudit',
    contactPerson: {
      firstName: 'Max',
      lastName: 'Mustermann',
      information: '+123456789',
      title: 'Herr',
    },
    customerData: { department: 'HR', name: 'TestBank', sector: 'Banking' },
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
    },
    customerData: { department: 'IT', name: 'TestInvestmentBank', sector: 'SuperBanking' },
  };

  function inputAudit(testAudit: {
    name: string;
    start?: any;
    end?: any;
    contactPerson: {
      firstName: string;
      lastName: string;
      information: string;
      title: string;
    };
    customerData: {
      department: string;
      name: string;
      sector: string;
    };
    status?: string;
  }) {
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
    cy.get('.grid-2-1-1 > :nth-child(1) > .input-full-width')
      .clear()
      .type(testAudit.customerData.name);
    cy.get('.grid-2-1-1 > :nth-child(2) > .input-full-width')
      .clear()
      .type(testAudit.customerData.department);
    cy.get('.grid-2-1-1 > :nth-child(3) > .input-full-width')
      .clear()
      .type(testAudit.customerData.sector);
    cy.get(':nth-child(3) > .accordion-item-header-collapsed').click();

    // Input Contact Information and open next collapsed accordeon through click
    cy.get(':nth-child(1) > .appearance-outline > .select-button').click();
    cy.get('nb-option').contains(testAudit.contactPerson.title).click();
    cy.get('.grid-auto-1-1 > :nth-child(2) > .input-full-width')
      .clear()
      .type(testAudit.contactPerson.firstName);
    cy.get('.grid-auto-1-1 > :nth-child(3) > .input-full-width')
      .clear()
      .type(testAudit.contactPerson.lastName);
    cy.get('.field-item-contact-information > .input-full-width')
      .clear()
      .type(testAudit.contactPerson.information);
    cy.get('.accordion-footer > .status-primary').click();
  }

  function checkAuditHeader(testAudit) {
    // Verify that an Audit is showing and that it contains the correct Audit name and Company Name
    cy.get('.accordion-item-header-collapsed').contains(testAudit.name);
    cy.get('.accordion-item-header-collapsed').contains(testAudit.customerData.name);
    if (testAudit.start || testAudit.end) {
        cy.get('.nb-accordion-item-header-wrapper > div > .label').should('exist');
    }
  }

  function checkAuditStatusLabel(testAudit) {
    cy.get('.banner-status').should('exist');
  }

  function checkAuditBody(testAudit) {
    // Click on the accordeon showing the new audit
    cy.get('.accordion-item-header-collapsed').click();
    // Verify that the body of the new audit exists
    cy.get('.item-body').should('exist');
    // Verify that the first body part shows Customer Data
    cy.get('.item-body > :nth-child(2)').contains(testAudit.customerData.name);
    cy.get('.item-body > :nth-child(2)').contains(testAudit.customerData.sector);
    cy.get('.item-body > :nth-child(2)').contains(testAudit.customerData.department);
    // Verity the second body part shows the Contact Person
    cy.get('.item-body > :nth-child(4)').contains(testAudit.contactPerson.firstName);
    cy.get('.item-body > :nth-child(4)').contains(testAudit.contactPerson.lastName);
    cy.get('.item-body > :nth-child(4)').contains(testAudit.contactPerson.title);
    //  Verity the third body part shows the Contact Person information
    cy.get('.item-body > :nth-child(6)').contains(testAudit.contactPerson.information);
  }

  it('Overview dialog shows no audits on initialization', () => {
    cy.visit(baseUrl);
    cy.get('.text-hint').should('exist');
  });

  it('Opens the window to add an audit with a click on the new audits button', () => {
    cy.get('.grid-1-auto-auto > .status-primary').click();
    cy.get('nb-dialog-container').should('exist');
  });

  it('Shows an audit entry on the overview page when added by an user', () => {
    inputAudit(testAudit);
    // Verify that an Audit exists
    cy.get('.accordion-item-header-collapsed').should('exist');
  });

  it('Adds the correct audit name and company name entered by the user to the overview', () => {
    checkAuditHeader(testAudit);
  });

  it('Shows the audit status label', () => {
    checkAuditStatusLabel(testAudit);
  });

  it('Shows the company, contact person and contact information of the audit', () => {
    checkAuditBody(testAudit);
  });

  it('Shows a button to access the audit', () => {
    cy.get('.card-options > .status-primary').should('exist');
  });

  it('Shows a button to see further options to do with the audit', () => {
    cy.get('.card-options > .status-basic').should('exist');
  });

  it('Shows further options on button click', () => {
    cy.get('.card-options > .status-basic').click();
    cy.get('a.ng-tns-c118-13').contains('Bearbeiten');
    cy.get('a.ng-tns-c118-13').click();
  });

  it('Shows an edit popup', () => {
    cy.url().should('include', '/edit');
  });

  it('Shows the edited audit', () => {
    inputAudit(testAuditEdited);
    // Verify that an Audit still exists
    cy.get('.accordion-item-header-collapsed').should('exist');
  });

  it('Shows the edited audit name and company name entered by the user to the overview', () => {
    checkAuditHeader(testAuditEdited);
  });

  it('Shows the edited audit status label', () => {
    checkAuditStatusLabel(testAuditEdited);
  });

  it('Shows the edited company, contact person and contact information of the audit', () => {
    checkAuditBody(testAuditEdited);
  });
});
