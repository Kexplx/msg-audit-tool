describe('InterviewQuestionsListPage', () => {
  let audit;
  let questionsUrl;
  let interview;
  let questions = [];

  before(() => {
    cy.fixture('backend-mock-data/interviews.json').then(interviews => {
      interview = interviews[0];
    });
    cy.fixture('backend-mock-data/questions-1.json').then(q => {
      questions.push(q);
    });
    cy.fixture('backend-mock-data/questions-2.json').then(q => {
      questions.push(q);
    });
    cy.fixture('backend-mock-data/audits.json')
      .then(audits => {
        audit = audits[0];
      })
      .then(() => {
        questionsUrl = `${Cypress.config().baseUrl}/audits/${audit.id}/interviews/${interview.id}/${
          interview.answers[0].questionId
        }`;
      });
  });

  beforeEach(() => {
    cy.injectBackendMocks();
    cy.visit(questionsUrl);
  });

  it('shows "Interview" and startdate as heading', () => {
    const testDate = new Date(interview.startDate).toLocaleDateString('de-DE', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
    });
    cy.get('[data-cy=heading').should('contain', 'Interview').should('contain', testDate);
  });

  it('shows interviewed people as subheading', () => {
    interview.interviewedContactPersons.forEach((contact, index) => {
      cy.get('[data-cy=subheading')
        .eq(index)
        .should('contain', contact.forename)
        .should('contain', contact.surname);
    });
  });

  it('shows a notes textfield that sticks on top when scrolled', () => {
    const dummyText = 'Dummy Goal';
    cy.get('[data-cy=faccrit-notes]').click();
    cy.get('[data-cy=notes]').should('be.visible').clear().type(dummyText);
    cy.scrollTo('center');
    cy.get('[data-cy=notes]').should('be.visible').should('have.value', dummyText);
    cy.scrollTo('bottom');
    cy.get('[data-cy=notes]').should('be.visible').should('have.value', dummyText);
    cy.scrollTo('top');
    cy.get('[data-cy=notes]')
      .should('be.visible')
      .should('have.value', dummyText)
      .clear()
      .should('not.have.value', dummyText);
  });

  it('shows a goal that sticks on top when scrolled');

  context('When focussing on the questions ...', () => {
    it('shows the questions given from the backend', () => {
      interview.answers.forEach(answer => {
        const text = questions.filter(el => {
          return el.id === answer.questionId;
        });
        assert(text.length === 1);
        cy.get('[data-cy=question-card-header]').should('contain', text[0].textDe);
      });
    });
  });

  context('When focussing on the question card it ...', () => {
    it('shows a question form with result, responsible, documentation, strategy buttons', () => {
      cy.get('[data-cy=question-form]').should('exist');
      cy.get('[data-cy=question-result]').should('exist');
      cy.get('[data-cy=question-responsible]').should('exist');
      cy.get('[data-cy=question-documentation]').should('exist');
      cy.get('[data-cy=question-strategy]').should('exist');
    });
    it('shows textfield for reason, note, certificate', () => {
      cy.get('[data-cy=question-reason]').should('exist');
      cy.get('[data-cy=question-note]').should('exist');
      cy.get('[data-cy=question-certificate]').should('exist');
    });
    it('has open accordeons on default', () => {
      cy.get('[data-cy="question-accordeon-item"]').each(acc => {
        cy.wrap(acc).should('have.attr', 'expanded');
      });
    });
  });

  context('When focussing on the events it ...', () => {
    it('gives stepper which shows the current faccrit and can skip to the next');

    it('saves inputs in question card when clicking on save', () => {
      const dummyText = 'Dummy';
      cy.route({
        method: 'PUT',
        url: '/answers/interview/1/question/1',
        response: [],
      }).as('putAnswers1');
      cy.visit(questionsUrl);

      cy.scrollTo('top')
        .get('[data-cy=question-reason]')
        .first()
        .clear()
        .type(dummyText)
        .get('[data-cy=question-note]')
        .first()
        .clear()
        .type(dummyText)
        .get('[data-cy=question-certificate]')
        .first()
        .clear()
        .type(dummyText);
      cy.get('[data-cy=question-responsible] > .label > .custom-checkbox')
        .first()
        .click()
        .get('[data-cy=question-documentation] > .label > .custom-checkbox')
        .first()
        .click()
        .get('[data-cy=question-strategy] > .label > .custom-checkbox')
        .first()
        .click()
        .get('[data-cy=question-result] > .label > .custom-checkbox')
        .first()
        .click();
      cy.get('[data-cy=save]').click();

      cy.wait('@putAnswers1').then(xhr => {
        assert.deepEqual(xhr.request.body, {
          annotation: dummyText,
          documentation: true,
          faccritId: 1,
          interviewId: 1,
          procedure: true,
          proof: dummyText,
          questionId: 1,
          reason: dummyText,
          responsible: true,
          result: true,
        });
      });
    });
    it('sets the status to finished and saves the input when clicking on finish button');
    it('returns to the interview view when clicking on the "Interviews" button');
  });

  context('When focussing on the sidebar it', () => {
    beforeEach(() => {
      cy.get('[data-cy=toggle-sidebar]').click();
    });

    it('opens sidebar on click', () => {
      cy.get('[data-cy=toggle-sidebar]').should('not.have.class', 'collapsed');
      cy.get('[data-cy=toggle-sidebar]').click();
    });

    it('scrolls to the selected question when clicked');
  });
});
