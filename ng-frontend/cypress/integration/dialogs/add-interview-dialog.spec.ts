describe('AddInterviewDialog', () => {
  let testAudit;
  let testInterview;

  before(() => {
    cy.fixture('audits/example-audit')
      .then(json => {
        testAudit = json;
      })
      .then(() => {
        cy.visit(Cypress.config().baseUrl);
        cy.addAudit(testAudit);
        // TODO add Person
        // go to specific audit interview page
        cy.get('[data-cy=audit-card]').first().click();
      });
    cy.fixture('interviews/example-interview').then(json => {
      testInterview = json;
    });
  });

  beforeEach(() => {
    // click on new interview button
    cy.get('[data-cy=new-interview]').click();
    // shows a dialog to input interview data
    cy.get('[data-cy=add-interview-form]').should('exist');
  });

  afterEach(() => {
    // does not show interview dialog anymore
    cy.get('[data-cy=add-interview-form]').should('not.exist');
    cy.url().should('not.contain', '/new');
  });

  it('can input and submit start date, end date, one contact person, role, iso criteria', () => {
    // pick a start date
    cy.get('[data-cy=interview-start-input]').click();
    cy.get('.today > .cell-content').first().click();
    // select contact
    cy.get('[data-cy=interview-contacts]').click();
    cy.get('[data-cy=interview-contact]').last().click();
    cy.get('[data-cy=interview-contacts]').click();
    // choose an iso criteria
    cy.get('[data-cy=interview-category-input]').click();
    cy.get('[data-cy=interview-category-option]').first().click();
    // submit form
    cy.get('[data-cy=submit-interview-data-form]').click();
  });

  // TODO Does this constraint still apply?
  //   it('requires iso criteria to be submittable', () => {
  //     // submit button should be disabled by default
  //     cy.get('[data-cy=submit-interview-data-form]').should('have.attr', 'disabled');
  //     // choose an iso criteria
  //     cy.get('[data-cy=interview-category-input]').click();
  //     cy.get('[data-cy=interview-category-option]').first().click();
  //     // submit button should be enabled
  //     cy.get('[data-cy=submit-interview-data-form]').should('be.enabled');
  //     // submit button should be clickable, submit form
  //     cy.get('[data-cy=submit-interview-data-form]').click();
  //   });

  it('can add and remove multiple contact persons with their roles', () => {
    // submit button should be disabled by default
    cy.get('[data-cy=submit-interview-data-form]').should('have.attr', 'disabled');
    // enable submit button
    cy.get('[data-cy=interview-category-input]').click();
    cy.get('[data-cy=interview-category-option]').first().click();
    // select multiple names and roles
    cy.get('[data-cy=interview-contacts]').click();
    cy.get('[data-cy=interview-contact]').each(contact => {
      cy.wrap(contact).click();
    });
    cy.get('[data-cy=interview-contacts]').click();
    // submit button should still be enabled
    cy.get('[data-cy=submit-interview-data-form]').should('be.enabled');
    // remove added names except first
    cy.get('[data-cy=interview-contacts]').click();
    cy.get('[data-cy=interview-contact]').each(contact => {
      cy.wrap(contact).click();
    });
    cy.get('[data-cy=interview-contacts]').click();
    // submit form
    cy.get('[data-cy=interview-contacts]').click();
    cy.get('[data-cy=interview-contact]').each(contact => {
      cy.wrap(contact).click();
    });
    cy.get('[data-cy=interview-contacts]').click();
    cy.get('[data-cy=submit-interview-data-form]').should('be.enabled');
    cy.get('[data-cy=submit-interview-data-form]').click();
  });

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

  // TODO Fix command.js if constraints are more settled and uncomment
  //   // Test consistency of added interview information
  //   context('When an interview was added it ...', () => {
  //     beforeEach(() => {
  //       cy.inputInterview(testInterview);
  //     });

  //     it('shows up on the interview overview', () => {
  //       cy.testInterviewListEntry(testInterview);
  //     });
  //   });
});
