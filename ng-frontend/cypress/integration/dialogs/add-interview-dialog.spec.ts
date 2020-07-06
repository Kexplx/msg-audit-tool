describe('AddInterviewDialog', () => {
  let testAudit;
  let testInterview;
  let testUrl;

  before(() => {
    cy.fixture('user-input-data/example-audit').then(json => {
      testAudit = json;
    });
    cy.fixture('user-input-data/example-interview').then(json => {
      testInterview = json;
    });
  });

  beforeEach(() => {
    cy.injectBackendMocks();
    cy.visit(Cypress.config().baseUrl);
    cy.get('[data-cy=audit-card]').first().click();
    // click on new interview button
    cy.get('[data-cy=new-interview]').click();
    // shows a dialog to input interview data
    cy.get('[data-cy=add-interview-form]').should('exist');
  });

  it('gives an inputable start date picker', () => {
    // pick a start date
    cy.get('[data-cy=interview-start-input]').click();
    cy.get('.today > .cell-content').first().click();
  });

  context('When focussing on the scope it...', () => {
    beforeEach(() => {
      cy.get('[data-cy=interview-scope-header]').click();
    });

    it('opens and closes accordeon body', () => {
      cy.get('[data-cy=interview-scope-body]').should('be.visible');
      cy.get('[data-cy=interview-scope-header]').click();
      cy.get('[data-cy=interview-scope-body]').should('not.be.visible');
    });

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
        cy.wrap(el).should('not.have.class', 'checked');
        cy.wrap(el).click();
        cy.wrap(el).should('have.class', 'checked');
      });
      cy.get('[data-cy=interview-scope-criteria] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('not.have.class', 'checked');
      });
    });

    it('is possible to select criteria without selecting the factor', () => {
      cy.get('[data-cy=interview-scope-criteria] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('not.have.class', 'checked');
        cy.wrap(el).click();
        cy.wrap(el).should('have.class', 'checked');
      });
      cy.get('[data-cy=interview-scope-factor] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('not.have.class', 'checked');
      });
    });

    it('shows an radio button to select all criteria of a factor (without the factor itself)', () => {
      cy.get('[data-cy=interview-scope-radio] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('not.have.class', 'checked');
        cy.wrap(el).click();
        cy.wrap(el).should('have.class', 'checked');
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
    // it('leaves all contact persons unchecked by default', () => {});

    it('can select one contact person', () => {
      // select contact
      cy.get('[data-cy=interview-contacts]').click();
      cy.get('[data-cy=interview-contact]').last().click();
      cy.get('[data-cy=interview-contacts]').click();
    });

    it('can add and remove multiple contact persons with their roles', () => {
      // select multiple names and roles
      cy.get('[data-cy=interview-contacts]').click();
      cy.get('[data-cy=interview-contact]').each(contact => {
        cy.wrap(contact).click();
      });
      cy.get('[data-cy=interview-contacts]').click();
      // remove added names except first
      cy.get('[data-cy=interview-contacts]').click();
      cy.get('[data-cy=interview-contact]').each(contact => {
        cy.wrap(contact).click();
      });
      cy.get('[data-cy=interview-contacts]').click();
    });
  });

  context('When focussing on the buttons', () => {
    it('does not show an confirmation dialog when clicking on cancel and no data was entered', () => {
      cy.get('[data-cy=cancel-interview-data-form]').click();
    });

    it('shows an confirmation dialog when clicking on cancel and data was entered', () => {
      // click on contact person
      cy.get('[data-cy=interview-contacts]').click();
      cy.get('[data-cy=interview-contacts]').click();
      // dialog pops up when clicking on cancel
      cy.get('[data-cy=cancel-interview-data-form]').click();
      cy.get('[data-cy=discard-back-dialog]').should('exist');
      // return to the interview form
      cy.get('[data-cy=back]').click();
      cy.get('[data-cy=add-interview-form]').should('exist');
      // dialog pops up when clicking on cancel
      cy.get('[data-cy=cancel-interview-data-form]').click();
      cy.get('[data-cy=discard-back-dialog]').should('exist');
      // agree to discard changes
      cy.get('[data-cy=discard]').click();
    });

    it('requires iso criteria to be submittable', () => {
      // submit button should be disabled by default
      cy.get('[data-cy=submit-interview-data-form]').should('have.attr', 'disabled');
      // choose an iso criteria
      cy.get('[data-cy=interview-scope-header]').click();
      cy.get('[data-cy=interview-scope-criteria] > .label > .custom-checkbox').each((el, index) => {
        cy.wrap(el).should('not.have.class', 'checked');
        cy.wrap(el).click();
        cy.wrap(el).should('have.class', 'checked');
      });
      cy.get('[data-cy=interview-scope-header]').click();
      // submit button should be enabled
      cy.get('[data-cy=submit-interview-data-form]').should('be.enabled');
      // submit button should be clickable, submit form
      cy.get('[data-cy=submit-interview-data-form]').click();
    });
  });

  context('When focussing on the network requests it ...', () => {
    it('builds a valid post request as form', () => {});
    it('shows error message when the network connection/requests failed', () => {});
  });
});
