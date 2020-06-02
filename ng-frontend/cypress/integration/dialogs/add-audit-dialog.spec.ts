describe('AddAuditDialog', () => {
  let auditsUrl = Cypress.config().baseUrl + '/audits';
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

  it('opens a form to input audit information when routing to audits/new', () => {
    cy.visit(auditsUrl + '/new');
    cy.get('[data-cy=audit-data-form]');
  });

  /**
   * Tests the basic information form
   */
  context('When focussing on the basic information it... ', () => {
    before(() => {
      cy.visit(auditsUrl + '/new');
    });

    beforeEach(() => {
      cy.get('[data-cy=audit-name-input]').clear().type(testAudit.name);
    });

    it('opens and closes accordeon body when clicking on company tab ', () => {
      cy.get('[data-cy=audit-basic-data-form]').should('contain', 'Auditname');
      cy.get('[data-cy=audit-basic-header]').click();
      cy.get('[data-cy=audit-basic-data-form]').contains('Auditname').should('not.be.visible');
      cy.get('[data-cy=audit-basic-header]').click();
    });

    it('gives an inputable element for audit name', () => {
      cy.get('[data-cy=audit-name-input]').should('have.value', testAudit.name);
    });

    it('gives a possibility to set start and end date', () => {
      cy.on('uncaught:exception', (err, runnable) => {
        expect(err.message).to.include(
          'nebular issue: https://github.com/akveo/nebular/issues/2338',
        );
        done();
        return false;
      });
      cy.get('[data-cy=audit-start-input]').click();
      cy.get('.today > .cell-content').click();
      cy.get('[data-cy=audit-end-input]').click();
      cy.get('.today > .cell-content').click();
      cy.get('[data-cy=submit-audit-data-form]').should('not.be.disabled');
    });

    it('disallows end date before the start date', () => {
      cy.get('[data-cy=audit-name-input]').should('have.value', testAudit.name);
      cy.get('[data-cy=audit-end-input]').click();
      cy.get('.bounding-month').first().click();
      cy.get('[data-cy=submit-audit-data-form]').should('be.disabled');
    });
  });

  /**
   * Tests the company information form
   */
  context('When focussing on the company information it... ', () => {
    before(() => {
      cy.visit(auditsUrl + '/new');
      cy.get('[data-cy=audit-customer-data-header]').click();
    });

    it('opens and closes accordeon body when clicking on company tab ', () => {
      cy.get('[data-cy=audit-customer-data-form]').should('contain', 'Unternehmen');
      cy.get('[data-cy=audit-customer-data-header]').click();
      cy.get('[data-cy=audit-customer-data-form]').contains('Unternehmen').should('not.be.visible');
      cy.get('[data-cy=audit-customer-data-header]').click();
    });

    it('gives an inputable element for company name', () => {
      cy.get('[data-cy=audit-customer-name-input]').clear().type(testAudit.customerData.name);
      cy.get('[data-cy=audit-customer-name-input]').should(
        'have.value',
        testAudit.customerData.name,
      );
    });

    it('gives an inputable element for company department', () => {
      cy.get('[data-cy=audit-customer-department-input]')
        .clear()
        .type(testAudit.customerData.department);
      cy.get('[data-cy=audit-customer-department-input]').should(
        'have.value',
        testAudit.customerData.department,
      );
    });

    it('gives an inputable element for corporate division', () => {
      cy.get('[data-cy=audit-customer-division-input]')
        .clear()
        .type(testAudit.customerData.corporateDivision);
      cy.get('[data-cy=audit-customer-division-input]').should(
        'have.value',
        testAudit.customerData.corporateDivision,
      );
    });

    it('gives an inputable element for company sector', () => {
      cy.get('[data-cy=audit-customer-sector-input]').clear().type(testAudit.customerData.sector);
      cy.get('[data-cy=audit-customer-sector-input]').should(
        'have.value',
        testAudit.customerData.sector,
      );
    });
  });

  /**
   * Tests the contact person formular
   */
  context('When focussing on the contact person it... ', () => {
    before(() => {
      cy.visit(auditsUrl + '/new');
      cy.get('[data-cy=audit-contact-data-header]').click();
    });

    it('opens and closes accordeon body when clicking on company tab ', () => {
      cy.get('[data-cy=audit-contact-data-form]').should('contain', 'Vorname');
      cy.get('[data-cy=audit-contact-data-header]').click();
      cy.get('[data-cy=audit-contact-data-form]').contains('Vorname').should('not.be.visible');
      cy.get('[data-cy=audit-contact-data-header]').click();
    });

    it('gives the gendered salutations for Herr, Frau, Divers', () => {
      cy.get('[data-cy=audit-contact-salutation-input]').click();
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
      cy.get('[data-cy=audit-contact-title-input]')
        .clear()
        .type(testAudit.contactPerson.salutation);
      cy.get('[data-cy=audit-contact-title-input]').should(
        'have.value',
        testAudit.contactPerson.salutation,
      );
    });

    it('gives inputable element for first name', () => {
      cy.get('[data-cy=audit-contact-firstname-input]')
        .clear()
        .type(testAudit.contactPerson.firstName);
      cy.get('[data-cy=audit-contact-firstname-input]').should(
        'have.value',
        testAudit.contactPerson.firstName,
      );
    });

    it('gives inputable element for last name', () => {
      cy.get('[data-cy=audit-contact-lastname-input]')
        .clear()
        .type(testAudit.contactPerson.lastName);
      cy.get('[data-cy=audit-contact-lastname-input]').should(
        'have.value',
        testAudit.contactPerson.lastName,
      );
    });

    it('gives inputable element for contact information', () => {
      cy.get('[data-cy=audit-contact-info-input]')
        .clear()
        .type(testAudit.contactPerson.information);
      cy.get('[data-cy=audit-contact-info-input]').should(
        'have.value',
        testAudit.contactPerson.information,
      );
    });
  });

  context('When focussing on the scope it...', () => {
    let allCriteria = [];

    before(() => {
      isoConstants.forEach(factor => {
        factor.criteria.forEach(c => {
          allCriteria.push(c);
        });
      });
    });

    beforeEach(() => {
      cy.visit(auditsUrl + '/new');
      cy.get('[data-cy=audit-scope-header]').click();
    });

    it('opens and closes accordeon body when clicking on scope tab ', () => {
      cy.get('[data-cy=audit-scope-form]').should('contain', 'Effektivität');
      cy.get('[data-cy=audit-scope-header]').click();
      cy.get('[data-cy=audit-scope-form]').contains('Effektivität').should('not.be.visible');
      cy.get('[data-cy=audit-scope-header]').click();
    });

    it('shows all factors and criteria from the ISO Norm', () => {
      cy.get('[data-cy=factor-entry]').each((el, index) => {
        cy.wrap(el).should('contain', isoConstants[index].title);
      });
      cy.get('[data-cy=criteria-entry]').each((el, index) => {
        cy.wrap(el).should('contain', allCriteria[index].title);
      });
    });

    it('checks all factors and criteria (default)', () => {
      cy.get('[data-cy=criteria-entry] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('have.class', 'checked');
      });
      cy.get('[data-cy=factor-entry] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('have.class', 'checked');
      });
    });

    it('allows unchecking all entries', () => {
      cy.get('[data-cy=criteria-entry] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('have.class', 'checked');
        cy.wrap(el).click();
        cy.wrap(el).should('not.have.class', 'checked');
      });
      cy.get('[data-cy=factor-entry] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('have.class', 'checked');
        cy.wrap(el).click();
        cy.wrap(el).should('not.have.class', 'checked');
      });
    });

    it('automatically checks/unchecks all criteria if factor was checked/unchecked', () => {
      cy.get('[data-cy=factor-entry]').each((el, index) => {
        cy.wrap(el).click();
        cy.wrap(el)
          .get('[data-cy=criteria-entry]')
          .each(criteria => {
            cy.wrap(criteria).should('not.have.class', 'checked');
          });
      });
    });
  });

  /**
   * Tests the buttons and their status depending on different inputs
   */
  context('When focussing on the buttons it...', () => {
    beforeEach(() => {
      cy.visit(auditsUrl + '/new');
    });

    it('shows an alert message when clicked on cancel', () => {
      cy.get('[data-cy=cancel-audit-data-form]').click();
      cy.get('[data-cy=discard-back-dialog]').should('exist');
    });

    it('closes window when auditname has been entered and clicking submit button', () => {
      cy.get('[data-cy=audit-name-input]').clear().type(testAudit.name);
      cy.get('[data-cy=submit-audit-data-form]').click();
      cy.get('[data-cy=audit-data-form]').should('not.exist');
    });

    it('should not be possible to click submit when no audit name was entered', () => {
      // No error message should be shown at first
      cy.get('[data-cy=submit-audit-data-form]').should('be.disabled');
      cy.get('[data-cy=auditname-missing-label]').should('not.be.visible');
      cy.get('[data-cy=audit-data-buttons]').click();
      // An error message should be shown when clicked somewhere without entering a name
      cy.get('[data-cy=auditname-missing-label]').should('be.visible');
      cy.get('[data-cy=submit-audit-data-form]').should('be.disabled');
      // The error message should disappear when entering a name
      cy.get('[data-cy=audit-name-input]').clear().type(testAudit.name);
      cy.get('[data-cy=auditname-missing-label]').should('not.be.visible');
      cy.get('[data-cy=submit-audit-data-form]').should('not.be.disabled');
    });

    it('Adding only a name should be sufficient to enable hinzufügen button', () => {
      cy.get('[data-cy=audit-name-input]').clear().type(testAudit.name);
      cy.get('[data-cy=submit-audit-data-form]').should('not.be.disabled');
      cy.get('[data-cy=cancel-audit-data-form]').click();
      cy.get('[data-cy=discard]');
    });
  });

  /**
   * Tests the consistency of an added audit in the audits overview and the infopage
   */
  context('When an audit was added it ...', () => {
    before(() => {
      cy.visit(auditsUrl);
      cy.addAudit(testAudit);
    });

    beforeEach(() => {
      cy.get('[data-cy=home]');
    });

    it('populates the audits list overview page with consistent information', () => {
      cy.testAuditListEntry(testAudit);
    });

    it('populates the concrete audit page with consistent information', () => {
      cy.get('[data-cy=audit-short-infos]').first().click();
      cy.get('[data-cy=audit-short-infos]').should('contain.text', testAudit.name);
      cy.contains('infos').click();
      cy.testAuditInfoPage(testAudit);
    });
  });
});
