describe('AuditsListPage', () => {
  let auditsUrl = Cypress.config().baseUrl + '/audits';

  beforeEach(() => {
    cy.injectBackendMocks();
    cy.visit(auditsUrl);
  });

  it('redirects to /audits when visiting the baseUrl', () => {
    cy.visit(Cypress.config().baseUrl).then(() => {
      cy.url().should('contain', '/audits');
    });
  });

  it('shows "Audits" heading', () => {
    cy.get('[data-cy=heading]').should('contain', 'Audits');
  });

  context('When focussing on the defaults it...', () => {
    beforeEach(() => {
      cy.route({
        method: 'GET',
        url: '/audits',
        response: [],
      });
      cy.visit(auditsUrl);
    });

    it('shows empty message in active audit view when clicked on active tab', () => {
      cy.get('[data-cy=no-active-audits]').should('exist');
    });

    it('shows empty message in archived audit view when clicked on archived tab', () => {
      cy.get('body').contains('Archiv').click();
      cy.get('[data-cy=no-archived-audits]').should('exist');
    });
  });

  context('When focussing on events it...', () => {
    beforeEach(() => {
      cy.injectBackendMocks();
      cy.visit(auditsUrl);
    });

    it('opens the window to add an audit with a click on the new audits button', () => {
      cy.get('[data-cy=new-audit]').click();
      cy.get('[data-cy=audit-data-form]').should('exist');
    });
  });

  context('When focussing on the audits ...', () => {
    beforeEach(() => {
      cy.injectBackendMocks();
    });

    it('shows all audit entries on the overview page received from the backend', () => {
      cy.fixture('backend-mock-data/audits.json').then(audits => {
        audit = audits[0];
        cy.route({
          method: 'GET',
          url: '/audits',
          response: [audit, audit, audit],
        });
        cy.visit(auditsUrl);
      });
      cy.get('[data-cy=audit-short-infos]').its('length').should('be', 3);
    });

    it('sorts the audits descending by creationDate', () => {
      let audit2;
      cy.fixture('backend-mock-data/audits.json')
        .then(audits => {
          const audit = audits[0];
          audit2 = { ...audit };
          audit2.name = audit2.name + '2';
          audit2.creationDate = new Date(new Date(audit2.creationDate).getTime() + 60000);
          cy.route({
            method: 'GET',
            url: '/audits',
            response: [audit, audit2],
          });
          cy.visit(auditsUrl);
        })
        .then(() => {
          cy.get('[data-cy=audit-short-infos]').first().should('contain', audit2.name);
        });
    });
  });

  context('When focussing on one audit card ...', () => {
    let audit;

    beforeEach(() => {
      cy.injectBackendMocks();
      cy.fixture('backend-mock-data/audits.json').then(audits => {
        audit = audits[0];
        cy.route({
          method: 'GET',
          url: '/audits',
          response: [audit],
        });
        cy.visit(auditsUrl);
      });
    });

    it('shows audit name', () => {
      cy.get('[data-cy=audit-short-infos]').first().should('contain.text', audit.name);
    });

    it('shows company name when a contact was chosen', () => {
      if (audit.contactPersons) {
        cy.get('[data-cy=audit-short-infos]')
          .first()
          .should('contain.text', audit.contactPersons[0].companyName);
      }
    });

    it('shows "Geplant" in popover when status is "OPEN"', () => {
      cy.get('[data-cy=audit-status]').first().should('have.attr', 'nbPopover');
      cy.get('[data-cy=audit-status]')
        .first()
        .invoke('attr', 'nbPopover')
        .should('contain', 'Geplant');
    });

    it('shows "In Bearbeitung" in popover when status is "ACTIVE"', () => {
      cy.fixture('backend-mock-data/audits.json').then(audits => {
        audit = audits[0];
        audit.status = 'ACTIVE';
        cy.route({
          method: 'GET',
          url: '/audits',
          response: [audit],
        });
        cy.visit(auditsUrl);
      });
      cy.get('[data-cy=audit-status]').first().should('have.attr', 'nbPopover');
      cy.get('[data-cy=audit-status]')
        .first()
        .invoke('attr', 'nbPopover')
        .should('contain', 'In Bearbeitung');
    });

    it('shows "Abgebrochen" in popover when status is "CANCELLED"', () => {
      cy.fixture('backend-mock-data/audits.json').then(audits => {
        audit = audits[0];
        audit.status = 'CANCELLED';
        cy.route({
          method: 'GET',
          url: '/audits',
          response: [audit],
        });
        cy.visit(auditsUrl);
      });
      cy.get('body').contains('Archiv').click();
      cy.get('[data-cy=audit-status]').first().should('have.attr', 'nbPopover');
      cy.get('[data-cy=audit-status]')
        .first()
        .invoke('attr', 'nbPopover')
        .should('contain', 'Abgebrochen');
    });

    it('shows "Abgeschlossen" in popover when status is "FINISHED"', () => {
      cy.fixture('backend-mock-data/audits.json').then(audits => {
        audit = audits[0];
        audit.status = 'FINISHED';
        cy.route({
          method: 'GET',
          url: '/audits',
          response: [audit],
        });
        cy.visit(auditsUrl);
      });
      cy.get('body').contains('Archiv').click();
      cy.get('[data-cy=audit-status]').first().should('have.attr', 'nbPopover');
      cy.get('[data-cy=audit-status]')
        .first()
        .invoke('attr', 'nbPopover')
        .should('contain', 'Abgeschlossen');
    });

    it('shows start date', () => {
      const startDate = new Date(audit.startDate).toLocaleDateString('de-DE', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
      });
      cy.get('[data-cy=audit-dates]').first().should('contain.text', startDate);
    });

    it('shows end date enddate is set', () => {
      const endDate = new Date(audit.endDate).toLocaleDateString('de-DE', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
      });
      cy.get('[data-cy=audit-dates]').first().should('contain.text', endDate);
    });

    it('shows tbd if end date was not set', () => {
      cy.fixture('backend-mock-data/audits.json').then(audits => {
        audit = audits[0];
        delete audit.endDate;
        cy.route({
          method: 'GET',
          url: '/audits',
          response: [audit],
        });
        cy.visit(auditsUrl);
      });
      cy.get('[data-cy=audit-dates]').first().should('contain.text', 'TBD');
    });

    it('redirects to an audit edit dialog when clicking on the edit button', () => {
      cy.get('[data-cy=edit-audit]').first().click();
      cy.url().should('be', `${auditsUrl}/${audit.id}/edit`);
    });

    it('redirects to audit page when clicking on an audit', () => {
      cy.get('[data-cy=audit-short-infos]').first().click();
      cy.url().should('be', `${auditsUrl}/${audit.id}/interviews`);
    });
  });
});
