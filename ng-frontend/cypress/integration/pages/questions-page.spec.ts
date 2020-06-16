describe('QuestionsPage', () => {
  let baseUrl = Cypress.config().baseUrl;
  let testAudit;
  let testAuditUrl;
  let testInterview;

  before(() => {
    // import testAudit that does not contain startdate nor enddate
    cy.fixture('audits/example-audit')
      .then(json => {
        testAudit = json;
      })
      .then(() => {
        cy.visit(baseUrl);
        cy.get('[data-cy=new-audit]').click();
        cy.inputAudit(testAudit);
        cy.get('[data-cy=audit-short-infos]').first().click();
        cy.url().then(url => {
          testAuditUrl = url;
        });
      });
    cy.fixture('interviews/example-interview').then(f => {
      testInterview = f;
    });
  });

  beforeEach(() => {
    cy.server();
    cy.route(testAuditUrl);
  });

  /**
   * Tests the interviews overview of an audit
   */
  context('When in question card it ...', () => {
    before(() => {
      cy.server();
      cy.route(testAuditUrl);
      cy.addInterview(testInterview);
      cy.get('[data-cy=interview-contact-persons]').first().click();
    });

    it('shows a question form with result, responsible, documentation, strategy buttons', () => {
      cy.get('[data-cy=question-form]').should('exist');
      cy.get('[data-cy=question-result]').should('exist');
      cy.get('[data-cy=question-responsible]').should('exist');
      cy.get('[data-cy=question-documentation]').should('exist');
      cy.get('[data-cy=question-strategy]').should('exist');
      //TODO
    });

    it('shows textfield for reason, note, certificate', () => {
      cy.get('[data-cy=question-reason]').should('exist');
      cy.get('[data-cy=question-note]').should('exist');
      cy.get('[data-cy=question-certificate]').should('exist');
      //TODO
    });

    it('shows faccrit', () => {
      cy.get('[data-cy=faccrit]').should('exist');
      //TODO
    });

    it('shows startdate', () => {
      //TODO
    });

    it('shows seven questions', () => {
      cy.get('[data-cy=question-card-header]').its('length').should('be', 7);
      //TODO
    });

    it('shows save button', () => {
      cy.get('[data-cy=save]').should('exist');
      //TODO
    });

    it('shows finish button', () => {
      cy.get('[data-cy=finish]').should('exist');
      //TODO
    });

    it('opens accordeons on click', () => {
      cy.get('[data-cy=question-card-header]').each(el => {
        cy.wrap(el).click();
        cy.get('[data-cy=question-form]').should('be.visible');
      });
    });
  });

  //TODO
  context('When focussing on the people', () => {
    it('shows interviewed people', () => {});
    //it('removes people by clicking button next to people', () => {});
    //it('shows a button to add interviewed people', () => {});
  });
});
