describe('AddAuditDialog', () => {
  let auditsUrl = Cypress.config().baseUrl + '/audits';
  let testAudit;
  let contactPersons;

  before(() => {
    cy.fixture('user-input-data/example-audit').then(json => {
      testAudit = json;
    });
    cy.fixture('backend-mock-data/contactPersons').then(json => {
      contactPersons = json;
    });
  });

  beforeEach(() => {
    cy.clearLocalStorage();
    cy.injectBackendMocks();
    cy.visit(auditsUrl).visit(auditsUrl + '/new');
    cy.wait('@getFacCrits').its('status').should('eq', 200);
    cy.wait('@getAudits').its('status').should('eq', 200);
    cy.wait('@getContactPersons').its('status').should('eq', 200);
    cy.wait('@getInterviews').its('status').should('eq', 200);
  });

  it('opens a form to input audit information when routing to audits/new', () => {
    cy.get('[data-cy=audit-data-form]').should('exist');
  });

  context('When focussing on the basic information it... ', () => {
    beforeEach(() => {
      cy.get('[data-cy=audit-name-input]').should('exist').clear().type(testAudit.name);
    });

    it('gives an inputable element for audit name', () => {
      cy.get('[data-cy=audit-name-input]').should('exist').should('have.value', testAudit.name);
      cy.get('[data-cy=submit-audit-data-form]').should('not.be.disabled');
    });

    it('gives a possibility to set start and end date', () => {
      cy.get('[data-cy=audit-start-input]')
        .should('exist')
        .click()
        .get('.today > .cell-content')
        .click();
      cy.get('[data-cy=audit-end-input]')
        .should('exist')
        .click()
        .get('.today > .cell-content')
        .click();
      cy.get('[data-cy=submit-audit-data-form]').should('not.be.disabled');
    });

    it('disallows end date before the start date', () => {
      cy.get('[data-cy=audit-end-input]')
        .should('exist')
        .click()
        .get('.prev-month')
        .click()
        .get('.bounding-month')
        .first()
        .click();
      cy.get('[data-cy=submit-audit-data-form]').should('be.disabled');
    });

    it('disallows start date behind end date', () => {
      cy.get('[data-cy=audit-end-input]')
        .should('exist')
        .click()
        .get('.today > .cell-content')
        .should('exist')
        .click();
      cy.get('[data-cy=audit-start-input]')
        .should('exist')
        .click()
        .get('.next-month')
        .should('exist')
        .click()
        .get('.bounding-month')
        .should('exist')
        .last()
        .click();
      cy.get('[data-cy=submit-audit-data-form]').should('be.disabled');
    });

    it('allows start date and end date in the past', () => {
      cy.get('[data-cy=audit-start-input]')
        .should('exist')
        .click()
        .get('.prev-month')
        .click()
        .get('.bounding-month')
        .first()
        .click();
      cy.get('[data-cy=audit-end-input]')
        .should('exist')
        .click()
        .get('.prev-month')
        .click()
        .get('.bounding-month')
        .first()
        .click();
      cy.get('[data-cy=submit-audit-data-form]').should('not.be.disabled');
    });

    it('allows choosing a contact person', () => {
      cy.get('[data-cy=audit-contacts]')
        .should('exist')
        .click()
        .get('[data-cy=audit-contacts]')
        .each(contact => {
          cy.wrap(contact).click();
        });
    });

    it('shows all existing contact persons', () => {
      cy.get('[data-cy=audit-contacts]')
        .should('exist')
        .click()
        .get('[data-cy=audit-contact]')
        .each((contact, i) => {
          cy.wrap(contact).should('contain', contactPersons[i].forename);
          cy.wrap(contact).should('contain', contactPersons[i].surname);
        });
    });
  });

  context('When focussing on the scope it...', () => {
    beforeEach(() => {
      cy.get('[data-cy=audit-scope-header]').click();
    });

    it('opens and closes accordeon body when clicking on scope tab ', () => {
      cy.get('[data-cy=factor-entry]').should('exist').should('be.visible');
      cy.get('[data-cy=criteria-entry]').should('exist').should('be.visible');
      cy.get('[data-cy=audit-scope-header]').should('exist').click();
      cy.get('[data-cy=factor-entry]').should('exist').should('not.be.visible');
      cy.get('[data-cy=criteria-entry]').should('exist').should('not.be.visible');
      cy.get('[data-cy=audit-scope-header]').click();
    });

    it('shows all factors and criteria from the ISO Norm', () => {
      cy.fixture('backend-mock-data/facCrits.json')
        .should('exist')
        .then(facCrits => {
          facCrits.forEach(el => {
            cy.get('[data-cy=facCrits]').should('contain', el.name);
          });
        });
    });

    it('checks all factors and criteria (default)', () => {
      cy.get('[data-cy=criteria-entry] > .label > .custom-checkbox')
        .should('exist')
        .each((el, index) => {
          cy.wrap(el).should('have.class', 'checked');
        });
      cy.get('[data-cy=factor-entry] > .label > .custom-checkbox')
        .should('exist')
        .each((el, index) => {
          cy.wrap(el).should('have.class', 'checked');
        });
    });

    it('allows unchecking entries', () => {
      cy.get('[data-cy=criteria-entry] > .label > .custom-checkbox')
        .should('exist')
        .first()
        .then(el => {
          cy.wrap(el).should('have.class', 'checked').click().should('not.have.class', 'checked');
        });
      cy.get('[data-cy=factor-entry] > .label > .custom-checkbox')
        .should('exist')
        .first()
        .then(el => {
          cy.wrap(el).should('have.class', 'checked').click().should('not.have.class', 'checked');
        });
    });

    it('automatically checks/unchecks criteria if factor was checked/unchecked', () => {
      cy.get('[data-cy=factor-entry]  > .label > .custom-checkbox')
        .should('exist')
        .last()
        .should('have.class', 'checked')
        .click()
        .should('not.have.class', 'checked')
        .get('[data-cy=criteria-entry]')
        .each(criteria => {
          cy.wrap(criteria).should('not.have.class', 'checked');
        });
    });
  });

  /**
   * Tests the buttons and their status depending on different inputs
   */
  context('When focussing on the buttons it...', () => {
    it('shows an alert message when clicked on cancel', () => {
      cy.get('[data-cy=cancel-audit-data-form]').should('exist').click();
      cy.get('[data-cy=discard-back-dialog]').should('exist');
    });

    it('closes window when auditname has been entered and clicking submit button', () => {
      cy.get('[data-cy=audit-name-input]')
        .should('exist')
        .clear()
        .type(testAudit.name)
        .get('[data-cy=submit-audit-data-form]')
        .click();
      cy.get('[data-cy=audit-data-form]').should('not.exist');
    });

    it('should not be possible to click submit when no audit name was entered', () => {
      cy.get('[data-cy=submit-audit-data-form]')
        .should('exist')
        .should('be.disabled')
        .get('[data-cy=auditname-missing-label]')
        .should('not.be.visible');
      cy.get('[data-cy=audit-data-buttons]')
        .should('exist')
        .click()
        .get('[data-cy=auditname-missing-label]')
        .should('be.visible')
        .get('[data-cy=submit-audit-data-form]')
        .should('be.disabled');
      cy.get('[data-cy=audit-name-input]')
        .should('exist')
        .clear()
        .type(testAudit.name)
        .get('[data-cy=auditname-missing-label]')
        .should('not.be.visible');
      cy.get('[data-cy=submit-audit-data-form]').should('not.be.disabled');
    });

    it('Adding only a name should be sufficient to enable hinzufügen button', () => {
      cy.get('[data-cy=audit-name-input]')
        .should('exist')
        .clear()
        .type(testAudit.name)
        .get('[data-cy=submit-audit-data-form]')
        .should('not.be.disabled');
    });
  });

  context('When focussing on the network request it ...', () => {
    it('builds a valid post request as form', () => {
      cy.inputAudit(testAudit)
        .wait('@postAudits')
        .then(xhr => {
          assert.deepEqual(xhr.request.body, {
            name: testAudit.name,
            contactPersons: [],
            endDate: null,
            scope: Array.from(Array(54), (_, i) => i + 1),
            startDate: new Date(Date.now()).toISOString().slice(0, 10),
          });
        });
    });
    it('shows error message when malformed request received');
    it('shows error message when the network connection/requests failed');
  });
});
