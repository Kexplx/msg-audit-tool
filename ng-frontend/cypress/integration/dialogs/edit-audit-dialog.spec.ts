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
      cy.visit(auditsUrl);
      cy.visit(`${auditsUrl}/${audit.id}/edit`);
    });

    it('shows the correct audit name', () => {
      cy.get('[data-cy=audit-name-input]').should('have.value', audit.name);
    });

    it('shows the correct start date', () => {
      cy.get('[data-cy=audit-start-input]')
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
        .invoke('val')
        .then(val => {
          assert.equal(
            new Date(val).toLocaleDateString(),
            new Date(audit.endDate).toLocaleDateString(),
          );
        });
    });

    it('shows the correct contact person(s)', () => {
      cy.get('[data-cy=audit-contacts]').click();
      cy.get('[data-cy=audit-contact]').each((contact, i) => {
        cy.wrap(contact).should('contain', contactPersons[i].forename);
        cy.wrap(contact).should('contain', contactPersons[i].surname);
      });
      cy.get('[data-cy=audit-contacts]').click();
    });

    it('shows the correct scope', () => {
      audit.scope.forEach(scope => {
        cy.get('[data-cy=facCrits]')
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
      cy.visit(auditsUrl);
      cy.visit(`${auditsUrl}/${audit.id}/edit`);
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
