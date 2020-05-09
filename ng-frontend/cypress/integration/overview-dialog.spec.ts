describe('OverviewDialog', () => {
  let baseUrl = Cypress.config().baseUrl;
  let testAudit = {
    name: 'Auditname 1',
    contactPerson: {
      firstName: 'Max',
      lastName: 'Mustermann',
      information: '+123456789',
      title: 'Herr',
    },
    customerData: { department: 'HR', name: 'TestBank', sector: 'Banking' },
  };

  let testAuditEdited = {
    name: 'Auditname 2',
    contactPerson: {
      firstName: 'Max2',
      lastName: 'Mustermann2',
      information: '+1234567892',
      title: 'Herr',
    },
    customerData: { department: 'HR2', name: 'TestBank2', sector: 'Banking2' },
  };

  it('Overview dialog shows no audits on initialization', () => {
    cy.visit(baseUrl);
    cy.get('.text-hint').should('exist');
  });

  it('Opens the window to add an audit with a click on the new audits button', () => {
    cy.get('.grid-1-auto-auto > .status-primary').click();
    cy.get('nb-dialog-container').should('exist');
  });

  it('Shows an audit entry on the overwiev page when added by an user', () => {
    // Input Audit name and open next collapsed accordeon through click
    cy.get('.grid-3-1 > :nth-child(1) > .input-full-width').type(testAudit.name);
    cy.get(':nth-child(2) > .accordion-item-header-collapsed').click();

    // Input Customer data and open next collapsed accordeon through click
    cy.get('.grid-2-1-1 > :nth-child(1) > .input-full-width').type(testAudit.customerData.name);
    cy.get('.grid-2-1-1 > :nth-child(2) > .input-full-width').type(
      testAudit.customerData.department,
    );
    cy.get('.grid-2-1-1 > :nth-child(3) > .input-full-width').type(testAudit.customerData.sector);
    cy.get(':nth-child(3) > .accordion-item-header-collapsed').click();

    // Input Contact Information and open next collapsed accordeon through click
    cy.get(':nth-child(1) > .appearance-outline > .select-button').click();
    cy.get('#nb-option-4').click();
    cy.get('.grid-auto-1-1 > :nth-child(2) > .input-full-width').type(
      testAudit.contactPerson.firstName,
    );
    cy.get('.grid-auto-1-1 > :nth-child(3) > .input-full-width').type(
      testAudit.contactPerson.lastName,
    );
    cy.get('.field-item-contact-information > .input-full-width').type(
      testAudit.contactPerson.information,
    );
    cy.get('.accordion-footer > .status-primary').click();

    // Verify that an Audit exists
    cy.get('.accordion-item-header-collapsed').should('exist');
  });

  it('Adds the correct audit name and company name entered by the user to the overview', () => {
    // Verify that an Audit is showing and that it contains the correct Audit name and Company Name
    cy.get('.accordion-item-header-collapsed').contains(testAudit.name);
    cy.get('.accordion-item-header-collapsed').contains(testAudit.customerData.name);
  });

  it('Shows the audit status label', () => {
    cy.get('.banner-status').should('exist');
  });

  it('Shows the company, contact person and contact information of the audit', () => {
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
});
