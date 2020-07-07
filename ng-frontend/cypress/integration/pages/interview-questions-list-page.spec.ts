describe('InterviewQuestionsListPage', () => {
  // let baseUrl = Cypress.config().baseUrl;
  // let testAudit;
  // let testInterview;
  // let audits;
  // let interviewsUrl;
  // let interviewDialogUrl;
  // before(() => {
  //   // import testAudit that does not contain startdate nor enddate
  //   cy.fixture('user-input-data/example-audit').then(json => {
  //     testAudit = json;
  //   });
  //   cy.fixture('user-input-data/example-interview').then(f => {
  //     testInterview = f;
  //   });
  //   cy.fixture('backend-mock-data/audits.json').then(json => {
  //     audits = json;
  //     //questionsUrl = `${Cypress.config().baseUrl}/audits/${audits[0].id}/interviews/${questions[0]}/1`;
  //   });
  // });
  // beforeEach(() => {
  //   cy.injectBackendMocks();
  // });
  // it('shows faccrit name as heading', () => {
  //   cy.get('[data-cy=faccrit]').should('exist');
  // });
  // it('shows startdate as heading', () => {
  //   const testDate = new Date(Date.now()).toLocaleDateString('de-DE', {
  //     year: 'numeric',
  //     month: '2-digit',
  //     day: '2-digit',
  //   });
  //   cy.get('[data-cy=audit-short-infos] > .label').should('contain', testDate);
  // });
  // it('shows a goal textfield that sticks on top when scrolled', () => {
  //   const dummyText = 'Dummy Goal';
  //   cy.get('[data-cy=goals-input]').should('be.visible');
  //   cy.get('[data-cy=goals-input]').type(dummyText);
  //   cy.scrollTo('center');
  //   cy.get('[data-cy=goals-input]').should('be.visible');
  //   cy.get('[data-cy=goals-input]').should('have.value', dummyText);
  //   cy.scrollTo('bottom');
  //   cy.get('[data-cy=goals-input]').should('be.visible');
  //   cy.get('[data-cy=goals-input]').should('have.value', dummyText);
  //   cy.scrollTo('top');
  //   cy.get('[data-cy=goals-input]').should('be.visible');
  //   cy.get('[data-cy=goals-input]').should('have.value', dummyText);
  //   cy.get('[data-cy=goals-input]').clear();
  //   cy.get('[data-cy=goals-input]').should('not.have.value', dummyText);
  // });
  // context('When focussing on the questions ...', () => {
  //   it('shows seven questions', () => {
  //     cy.get('[data-cy=question-card-header]').its('length').should('be', 7);
  //   });
  // });
  // /**
  //  * Tests the questions card
  //  */
  // context('When focussing on the question card it ...', () => {
  //   it('shows a question form with result, responsible, documentation, strategy buttons', () => {
  //     cy.get('[data-cy=question-form]').should('exist');
  //     cy.get('[data-cy=question-result]').should('exist');
  //     cy.get('[data-cy=question-responsible]').should('exist');
  //     cy.get('[data-cy=question-documentation]').should('exist');
  //     cy.get('[data-cy=question-strategy]').should('exist');
  //   });
  //   it('shows textfield for reason, note, certificate', () => {
  //     cy.get('[data-cy=question-reason]').should('exist');
  //     cy.get('[data-cy=question-note]').should('exist');
  //     cy.get('[data-cy=question-certificate]').should('exist');
  //   });
  //   it('has open accordeons on default', () => {
  //     cy.get('[data-cy="question-accordeon-item"]').each(acc => {
  //       cy.wrap(acc).should('have.attr', 'expanded');
  //     });
  //   });
  // });
  // context('When focussing on the events it ...', () => {
  //   it('saves inputs in question card when clicking on save', () => {
  //     //TODO add dummy interview result as fixture
  //     const dummyText = 'Dummy';
  //     cy.scrollTo('top');
  //     cy.get('[data-cy=question-reason]').first().type(dummyText);
  //     cy.get('[data-cy=question-note]').first().type(dummyText);
  //     cy.get('[data-cy=question-certificate]').first().type(dummyText);
  //     cy.get('[data-cy=question-responsible] > .label > .custom-checkbox').first().click();
  //     cy.get('[data-cy=question-documentation] > .label > .custom-checkbox').first().click();
  //     cy.get('[data-cy=question-strategy] > .label > .custom-checkbox').first().click();
  //     cy.get('[data-cy=question-result] > .label > .custom-checkbox').first().click();
  //     cy.get('[data-cy=save]').click();
  //     cy.get('[data-cy=home]').click();
  //     cy.get('[data-cy=audit-short-infos]').first().click();
  //     cy.get('[data-cy=interview-contact-persons]').first().click();
  //     cy.scrollTo('top');
  //     cy.get('[data-cy=question-reason]').first().should('have.value', dummyText);
  //     cy.get('[data-cy=question-note]').first().should('have.value', dummyText);
  //     cy.get('[data-cy=question-certificate]').first().should('have.value', dummyText);
  //     cy.get('[data-cy=question-responsible] > .label > .custom-checkbox')
  //       .first()
  //       .should('have.class', 'checked');
  //     cy.get('[data-cy=question-documentation] > .label > .custom-checkbox')
  //       .first()
  //       .should('have.class', 'checked');
  //     cy.get('[data-cy=question-strategy] > .label > .custom-checkbox')
  //       .first()
  //       .should('have.class', 'checked');
  //     cy.get('[data-cy=question-result] > .label > .custom-checkbox')
  //       .first()
  //       .should('have.class', 'checked');
  //   });
  //   it('sets the status to finished and saves the input when clicking on finish button');
  // });
  // context('When focussing on the sidebar it', () => {
  //   it('opens sidebar on click', () => {
  //     cy.get('[data-cy=toggle-sidebar]').click();
  //     cy.get('[data-cy=toggle-sidebar]').should('not.have.class', 'collapsed');
  //     cy.get('[data-cy=toggle-sidebar]').click();
  //   });
  //   it('scrolls to the selected question when clicked', () => {
  //     cy.get('[data-cy=toggle-sidebar]').click();
  //     cy.get('.menu-title').each(item => {
  //       let questionLabel = item.text();
  //       cy.wrap(item).click();
  //       cy.get('[data-cy=question-card-header]:visible').contains(questionLabel);
  //     });
  //   });
  // });
  // context('When focussing on the people it...', () => {
  //   it('shows interviewed people');
  //   it('removes people by clicking button next to people');
  //   it('shows a button to add interviewed people');
  // });
});
