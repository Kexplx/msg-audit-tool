const defaultScopeComplement = [
  {
    id: 8,
    referenceId: null,
    name: 'Austauschbarkeit, Übertragbarkeit',
  },
  {
    id: 14,
    referenceId: null,
    name: 'Sonstige Anforderungen',
  },
  {
    id: 30,
    referenceId: 8,
    name: 'Koexistenz',
  },
  {
    id: 31,
    referenceId: 8,
    name: 'Interoperabilität',
  },
];

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
        .click({ force: true })
        .get('.bounding-month')
        .first()
        .click({ force: true });
      cy.get('[data-cy=submit-audit-data-form]').should('be.disabled');
    });

    it('disallows start date behind end date', () => {
      cy.get('[data-cy=audit-end-input]')
        .should('exist')
        .click()
        .get('.today > .cell-content')
        .should('exist')
        .click({ force: true });
      cy.get('[data-cy=audit-start-input]')
        .should('exist')
        .click()
        .get('.next-month')
        .should('exist')
        .click({ force: true })
        .get('.bounding-month')
        .should('exist')
        .last()
        .click({ force: true });
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

    it('shows contact person suggestions when typed in and adds it on click', () => {
      cy.get('[data-cy=interview-contacts]')
        .clear()
        .type(contactPersons[0].forename)
        .get('[data-cy=contact-option]')
        .first()
        .should('contain', contactPersons[0].forename + ' ' + contactPersons[0].surname)
        .click()
        .get('[data-cy=contact-chosen]')
        .should('contain', contactPersons[0].forename + ' ' + contactPersons[0].surname)
        .should('contain', contactPersons[0].companyName);
    });

    it('can add and remove multiple contact persons', () => {
      cy.get('[data-cy=interview-contacts]')
        .clear()
        .type(contactPersons[0].forename)
        .get('[data-cy=contact-option]')
        .first()
        .should('contain', contactPersons[0].forename + ' ' + contactPersons[0].surname)
        .click()
        .get('[data-cy=contact-chosen]')
        .first()
        .should('contain', contactPersons[0].forename + ' ' + contactPersons[0].surname)
        .should('contain', contactPersons[0].companyName);

      cy.get('[data-cy=interview-contacts]')
        .clear()
        .type(contactPersons[1].forename)
        .get('[data-cy=contact-option]')
        .first()
        .should('contain', contactPersons[1].forename + ' ' + contactPersons[1].surname)
        .click()
        .get('[data-cy=contact-chosen]')
        .last()
        .should('contain', contactPersons[1].forename + ' ' + contactPersons[1].surname)
        .should('contain', contactPersons[1].companyName);

      cy.get('[data-cy=contact-delete]')
        .first()
        .click()
        .get('[data-cy=contact-chosen]')
        .first()
        .should('not.contain', contactPersons[0].forename + ' ' + contactPersons[0].surname)
        .should('contain', contactPersons[1].forename + ' ' + contactPersons[1].surname)
        .should('contain', contactPersons[1].companyName);

      cy.get('[data-cy=contact-delete]')
        .first()
        .click()
        .get('[data-cy=contact-chosen]')
        .should('not.exist');
    });

    it('disallows adding the same contact twice', () => {
      cy.get('[data-cy=interview-contacts]')
        .clear()
        .type(contactPersons[0].forename)
        .get('[data-cy=contact-option]')
        .first()
        .should('contain', contactPersons[0].forename + ' ' + contactPersons[0].surname)
        .click()
        .get('[data-cy=contact-chosen]')
        .its('length')
        .should('be', 1);

      cy.get('[data-cy=interview-contacts]')
        .clear()
        .type(contactPersons[0].forename)
        .get('[data-cy=contact-option]')
        .first()
        .should('contain', contactPersons[0].forename + ' ' + contactPersons[0].surname)
        .click()
        .get('[data-cy=contact-chosen]')
        .its('length')
        .should('be', 1);
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
          if (
            !defaultScopeComplement
              .filter(fc => fc.referenceId)
              .map(fc => fc.id - 15)
              .includes(index)
          ) {
            cy.wrap(el).should('have.class', 'checked');
          }
        });
      cy.get('[data-cy=factor-entry] > .label > .custom-checkbox')
        .should('exist')
        .each((el, index) => {
          if (!defaultScopeComplement.map(fc => fc.id - 1).includes(index)) {
            cy.wrap(el).should('have.class', 'checked');
          }
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
  });

  /**
   * Tests the buttons and their status depending on different inputs
   */
  context('When focussing on the buttons it...', () => {
    it('shows an alert message when clicked on cancel', () => {
      cy.get('[data-cy=audit-name-input]')
        .should('exist')
        .clear()
        .type(testAudit.name)
        .get('[data-cy=cancel-audit-data-form]')
        .should('exist')
        .click();
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
            endDate: null,
            contactPersons: [],
            scope: Array.from(Array(54), (_, i) => i + 1).filter(
              i => !defaultScopeComplement.map(fc => fc.id).includes(i),
            ),
            startDate: new Date(Date.now()).toISOString().slice(0, 10),
          });
        });
    });
    it('shows error message when malformed request received');
    it('shows error message when the network connection/requests failed');
  });
});
