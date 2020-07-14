describe('AddInterviewDialog', () => {
  let audits;
  let testAudit;
  let testInterview;
  let interviewsUrl;
  let interviewDialogUrl;
  let contactPersons;

  before(() => {
    cy.fixture('user-input-data/example-audit').then(json => {
      testAudit = json;
    });
    cy.fixture('user-input-data/example-interview').then(json => {
      testInterview = json;
    });
    cy.fixture('backend-mock-data/audits.json').then(json => {
      audits = json;
      interviewsUrl = `${Cypress.config().baseUrl}/audits/${audits[0].id}/interviews`;
      interviewDialogUrl = interviewsUrl + '/new';
    });
    cy.fixture('backend-mock-data/contactPersons').then(json => {
      contactPersons = json;
    });
  });

  beforeEach(() => {
    cy.injectBackendMocks();
    cy.visit(interviewsUrl).get('[data-cy=new-interview]').click();
  });

  it('gives an inputable start date picker', () => {
    cy.get('[data-cy=interview-start-input]').click().get('.today > .cell-content').should('exist');
  });

  context('When focussing on the scope it...', () => {
    beforeEach(() => {
      cy.get('[data-cy=interview-scope-header]').click();
    });

    it('opens and closes accordeon body', () => {
      cy.get('[data-cy=interview-scope-body]').should('be.visible');
      cy.get('[data-cy=interview-scope-header]')
        .click()
        .get('[data-cy=interview-scope-body]')
        .should('not.be.visible');
    });

    it('shows all factors and criteria given of an audit');

    it('leaves all checkboxes unchecked by default', () => {
      cy.get('[data-cy=interview-scope-criteria] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('not.have.class', 'checked');
      });
      cy.get('[data-cy=interview-scope-factor] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('not.have.class', 'checked');
      });
    });

    it('is possible to select a factor without its criteria', () => {
      cy.get('[data-cy=interview-scope-factor] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('not.have.class', 'checked').click().should('have.class', 'checked');
      });
      cy.get('[data-cy=interview-scope-criteria] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('not.have.class', 'checked');
      });
    });
    it('is possible to select criteria without selecting the factor', () => {
      cy.get('[data-cy=interview-scope-criteria] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('not.have.class', 'checked').click().should('have.class', 'checked');
      });
      cy.get('[data-cy=interview-scope-factor] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('not.have.class', 'checked');
      });
    });

    it('shows an radio button to select all criteria of a factor (without the factor itself)', () => {
      cy.get('[data-cy=interview-scope-radio] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('not.have.class', 'checked').click().should('have.class', 'checked');
      });
      cy.get('[data-cy=interview-scope-factor] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('not.have.class', 'checked');
      });
      cy.get('[data-cy=interview-scope-criteria] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('have.class', 'checked');
      });
    });

    // it('unchecking a criteria should uncheck radio button', () => {});
  });

  context('When focussing on the contact persons it...', () => {
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

  context('When focussing on the buttons', () => {
    it('does not show an confirmation dialog when clicking on cancel and no data was entered', () => {
      cy.get('[data-cy=cancel-interview-data-form]').click();
    });

    it('shows an confirmation dialog when clicking on cancel and data was entered', () => {
      cy.get('[data-cy=interview-start-input]')
        .click()
        .get('[data-cy=cancel-interview-data-form]')
        .click();
      cy.get('[data-cy=discard-back-dialog]').should('exist');
    });

    it('requires iso criteria to be submittable', () => {
      cy.get('[data-cy=submit-interview-data-form]').should('have.attr', 'disabled');
      cy.get('[data-cy=interview-scope-header]')
        .click()
        .get('[data-cy=interview-scope-criteria] > .label > .custom-checkbox')
        .each((el, index) => {
          cy.wrap(el).should('not.have.class', 'checked').click().should('have.class', 'checked');
        })
        .get('[data-cy=interview-scope-header]')
        .click();
      cy.get('[data-cy=submit-interview-data-form]').should('be.enabled');
    });
  });

  context('When focussing on the network requests it ...', () => {
    it('builds a valid post request as form', () => {});
    it('shows error message when malformed request received');
    it('shows error message when the network connection/requests failed');
  });
});
