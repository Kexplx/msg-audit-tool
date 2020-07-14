describe('EditAuditDialog', () => {
  const auditsUrl = Cypress.config().baseUrl + '/audits';
  let audits;
  let testAudit;
  let contactPersons;

  before(() => {
    cy.fixture('backend-mock-data/audits.json').then(json => {
      audits = json;
    });
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
  });

  it('does not show an edit dialog when audit id is invalid', () => {
    cy.visit(auditsUrl);
    cy.visit(auditsUrl + '/kaputt/edit');
    cy.get('[data-cy=audit-data-form]').should('not.exist');
    cy.visit(auditsUrl + '/kaputt/infos/edit');
    cy.get('[data-cy=audit-data-form]').should('not.exist');
  });

  context('When focussing on the existing form content it ...', () => {
    let audit;

    beforeEach(() => {
      audit = audits[0];
      cy.visit(auditsUrl).visit(`${auditsUrl}/${audit.id}/edit`);
      cy.wait('@getFacCrits').its('status').should('eq', 200);
      cy.wait('@getAudits').its('status').should('eq', 200);
      cy.wait('@getContactPersons').its('status').should('eq', 200);
      cy.wait('@getInterviews').its('status').should('eq', 200);
    });

    it('shows the correct audit name', () => {
      cy.get('[data-cy=audit-name-input]').should('exist').should('have.value', audit.name);
    });

    it('shows the correct start date', () => {
      cy.get('[data-cy=audit-start-input]')
        .should('exist')
        .invoke('val')
        .then(val => {
          assert.equal(
            new Date(val).toLocaleDateString(),
            new Date(audit.startDate).toLocaleDateString(),
          );
        });
    });

    it('shows the correct end date', () => {
      cy.get('[data-cy=audit-end-input]')
        .should('exist')
        .invoke('val')
        .then(val => {
          assert.equal(
            new Date(val).toLocaleDateString(),
            new Date(audit.endDate).toLocaleDateString(),
          );
        });
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

    it('shows the correct scope', () => {
      audit.scope.forEach(scope => {
        cy.get('[data-cy=facCrits]')
          .should('exist')
          .contains(scope.name)
          .then(a => {
            cy.wrap(a).get('.label > .custom-checkbox').should('have.class', 'checked');
          });
      });
    });
  });

  context('When focussing on the network request it ...', () => {
    let audit;

    beforeEach(() => {
      audit = audits[0];
      cy.visit(auditsUrl).visit(`${auditsUrl}/${audit.id}/edit`);
      cy.wait('@getFacCrits').its('status').should('eq', 200);
      cy.wait('@getAudits').its('status').should('eq', 200);
      cy.wait('@getContactPersons').its('status').should('eq', 200);
      cy.wait('@getInterviews').its('status').should('eq', 200);
    });

    it('builds a valid post request as form', () => {
      cy.inputAudit(testAudit);
      cy.wait('@putAudits').then(xhr => {
        assert.deepEqual(xhr.request.body, {
          name: testAudit.name,
          endDate: audit.endDate,
          startDate: audit.startDate,
        });
      });
    });

    it('shows error message when the network connection/requests failed');
  });
});
