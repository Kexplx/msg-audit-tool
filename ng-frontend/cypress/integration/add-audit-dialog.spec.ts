import { Audit } from 'src/app/data/models/audit.model';

describe('AddAuditDialog', () => {
  let baseUrl = Cypress.config().baseUrl + '/audits/';
  let testAudit: Audit;

  before(() => {
    cy.fixture('example-audit').then(json => {
      testAudit = json;
    });
  });

  it('Routing to audits/new opens AddAuditForm', () => {
    cy.visit(baseUrl + 'new');
    cy.get('nb-dialog-container');
  });

  it('Clicking abbrechen button opens warning message', () => {
    cy.get('.accordion-footer > .status-basic').click();
    cy.get('nb-dialog-container > .ng-star-inserted > nb-card > nb-card-header').should('exist');
  });

  it('Clicking yes on warning message should close overlay', () => {
    cy.get('.card-footer > :nth-child(2)').click();
    cy.get('#cdk-overlay-0 > nb-dialog-container > app-add-audit-dialog > div > form').should(
      'not.exist',
    );
  });

  it('Form gives inputable element for audit name', () => {
    cy.visit(baseUrl + 'new');
    cy.get('.field-item-audit-name > .input-full-width').clear().type(testAudit.name);
    cy.get('.field-item-audit-name > .input-full-width').should('have.value', testAudit.name);
  });

  // it('Form gives choosable element for startdate and enddate', () => {
  //   cy.get('.ng-tns-c155-1.ng-star-inserted > .ng-trigger > .item-body > .grid-1-1 > :nth-child(1) > .input-full-width').click();
  //   cy.get('.today > .cell-content').click();
  //   cy.get('.ng-tns-c155-1.ng-star-inserted > .ng-trigger > .item-body > .grid-1-1 > :nth-child(2) > .input-full-width').click();
  //   cy.get('.today > .cell-content').click();
  // });

  it('Clicking on company tab opens accordeon body', () => {
    cy.get(':nth-child(2) > .accordion-item-header-collapsed').click();
    cy.get('.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body').should(
      'contain',
      'Unternehmen',
    );
  });

  it('Form gives inputable element for company name', () => {
    cy.get(
      '.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body > :nth-child(1) > :nth-child(1) > .input-full-width',
    )
      .clear()
      .type(testAudit.customerData.name);
    cy.get(
      '.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body > :nth-child(1) > :nth-child(1) > .input-full-width',
    ).should('have.value', testAudit.customerData.name);
  });

  it('Form gives inputable element for company department', () => {
    cy.get(
      '.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body > :nth-child(1) > :nth-child(2) > .input-full-width',
    )
      .clear()
      .type(testAudit.customerData.department);
    cy.get(
      '.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body > :nth-child(1) > :nth-child(2) > .input-full-width',
    ).should('have.value', testAudit.customerData.department);
  });

  it('Form gives inputable element for corporate division', () => {
    cy.get(
      '.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body > :nth-child(2) > :nth-child(1) > .input-full-width',
    )
      .clear()
      .type(testAudit.customerData.corporateDivision);
    cy.get(
      '.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body > :nth-child(2) > :nth-child(1) > .input-full-width',
    ).should('have.value', testAudit.customerData.corporateDivision);
  });

  it('Form gives inputable element for company sector', () => {
    cy.get(
      '.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body > :nth-child(2) > :nth-child(2) > .input-full-width',
    )
      .clear()
      .type(testAudit.customerData.sector);
    cy.get(
      '.ng-tns-c155-3.ng-star-inserted > .ng-trigger > .item-body > :nth-child(2) > :nth-child(2) > .input-full-width',
    ).should('have.value', testAudit.customerData.sector);
  });

  it('Clicking on contact information tab opens accordeon body', () => {
    cy.get(':nth-child(3) > .accordion-item-header-collapsed').click();
    cy.get('.ng-tns-c155-5.ng-star-inserted > .ng-trigger > .item-body').should(
      'contain',
      'Vorname',
    );
  });

  it('Form gives the gendered salutations for Herr, Frau, Divers', () => {
    cy.get(':nth-child(1) > .appearance-outline > .select-button').click();
    cy.get('nb-option').should('contain', 'Herr');
    cy.get('nb-option').should('contain', 'Frau');
    cy.get('nb-option').should('contain', 'Divers');
  });

  it('Form gives usable drop down for choosing a gendered salutation for the contact person', () => {
    cy.get('nb-option').contains(testAudit.contactPerson.title).click();
  });

  it('Form gives inputable element for professional salutation', () => {
    cy.get(
      '.ng-tns-c155-5.ng-star-inserted > .ng-trigger > .item-body > :nth-child(1) > :nth-child(2) > .input-full-width',
    )
      .clear()
      .type(testAudit.contactPerson.salutation);
    cy.get(
      '.ng-tns-c155-5.ng-star-inserted > .ng-trigger > .item-body > :nth-child(1) > :nth-child(2) > .input-full-width',
    ).should('have.value', testAudit.contactPerson.salutation);
  });

  it('Form gives inputable element for first name', () => {
    cy.get(
      '.ng-tns-c155-5.ng-star-inserted > .ng-trigger > .item-body > :nth-child(2) > :nth-child(1) > .input-full-width',
    )
      .clear()
      .type(testAudit.contactPerson.firstName);
    cy.get(
      '.ng-tns-c155-5.ng-star-inserted > .ng-trigger > .item-body > :nth-child(2) > :nth-child(1) > .input-full-width',
    ).should('have.value', testAudit.contactPerson.firstName);
  });

  it('Form gives inputable element for last name', () => {
    cy.get(
      '.ng-tns-c155-5.ng-star-inserted > .ng-trigger > .item-body > :nth-child(2) > :nth-child(2) > .input-full-width',
    )
      .clear()
      .type(testAudit.contactPerson.lastName);
    cy.get(
      '.ng-tns-c155-5.ng-star-inserted > .ng-trigger > .item-body > :nth-child(2) > :nth-child(2) > .input-full-width',
    ).should('have.value', testAudit.contactPerson.lastName);
  });

  it('Form gives inputable element for contact information', () => {
    cy.get('.field-item-contact-information > .input-full-width')
      .clear()
      .type(testAudit.contactPerson.information);
    cy.get('.field-item-contact-information > .input-full-width').should(
      'have.value',
      testAudit.contactPerson.information,
    );
  });

  it('Clicking hinzufügen button closes window', () => {
    cy.get('.accordion-footer > .status-primary').click();
    cy.get('#cdk-overlay-0 > nb-dialog-container > app-add-audit-dialog > div > form').should(
      'not.exist',
    );
  });

  it('Clicking hinzufügen should not be possible when no audit name was entered', () => {
    cy.visit(baseUrl + 'new');
    cy.get('.btn-disabled').should('exist');
  });

  it('Adding only a name should be sufficient to enable hinzufügen button', () => {
    cy.get('.field-item-audit-name > .input-full-width').clear().type(testAudit.name);
    cy.get('.btn-disabled').should('not.exist');
  });
});
