describe('AuditPage', () => {
  let baseUrl = Cypress.config().baseUrl;
  let testAudit;
  let testAuditUrl;
  let factors;

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
    cy.fixture('iso-constants/factors-criteria').then(f => {
      factors = f;
    });
  });

  beforeEach(() => {
    cy.server();
    cy.route(testAuditUrl);
  });

  it('generates a valid audit page with a unique id after adding an audit', () => {
    cy.url().should('contain', 'interviews');
  });

  /**
   * Tests the interviews overview of an audit
   */
  context('When in interview overview it ...', () => {
    before(() => {
      cy.server();
      cy.route(testAuditUrl);
    });

    //TODO
    // it('displays an overview with all chosen factors', () => {
    //   for (let i = 0; i < factors.length; i++) {
    //     let factor_obj = `:nth-child(${i + 1}) > nb-card-header`;
    //     cy.get(factor_obj).should('contain.text', factors[i].title);
    //   }
    // });

    it('shows message that no interview exists on creation', () => {
      cy.get('[data-cy=faccrit-body]').each(body => {
        cy.wrap(body).should('contain', 'Keine Interviews vorhanden');
      });
    });

    it('displays a button to new interviews', () => {
      cy.get('[data-cy=new-interview]').click();
      cy.get('[data-cy=add-interview-form]').should('exist');
      cy.get('[data-cy=cancel-interview-data-form]').click();
    });
  });

  context('When focussing on the sidebar it', () => {
    before(() => {
      cy.visit(baseUrl);
      cy.get('[data-cy=new-audit]').click();
      cy.inputAudit(testAudit);
      cy.get('[data-cy=audit-short-infos]').first().click();
    });

    it('opens sidebar on click', () => {
      cy.get('[data-cy=toggle-sidebar]').click();
      cy.get('[data-cy=toggle-sidebar]').should('not.have.class', 'collapsed');
      cy.get('[data-cy=toggle-sidebar]').click();
    });

    it('scrolls to the selected question when clicked', () => {
      cy.get('[data-cy=toggle-sidebar]').click();
      cy.get('.menu-title').each(item => {
        let questionLabel = item.text();
        cy.wrap(item).click();
        cy.get('[data-cy=factor-card]:visible').contains(questionLabel);
      });
    });
  });

  //   /**
  //    * Tests the audit info page
  //    */
  //   context('When on info page it ...', () => {
  //     before(() => {
  //       cy.server();
  //       cy.route(testAuditUrl);
  //       cy.contains('info').click();
  //     });

  //     it('generates a valid audit info page displaying audit information', () => {
  //       cy.testAuditInfoPage(testAudit);
  //     });

  //     it('shows a button to edit the audit information', () => {
  //       cy.get('[data-cy=audit-options]').click();
  //       cy.url().should('contain', 'edit');
  //       cy.get('[data-cy=cancel-audit-data-form]').click();
  //     });
  //   });

  //   context('When on either info page or interview page it ...', () => {
  //     function testTitlebar(testDate) {
  //       cy.get('[data-cy=audit-short-infos]').contains(testAudit.name);
  //       cy.get('[data-cy=audit-short-infos]').contains(testDate);
  //       cy.get('[data-cy=audit-short-infos]').contains('TBD');
  //     }

  //     it('displays a header with audit name and date', () => {
  //       const testDate = new Date(Date.now()).toLocaleDateString('de-DE', {
  //         year: 'numeric',
  //         month: '2-digit',
  //         day: '2-digit',
  //       });
  //       cy.route(testAuditUrl);
  //       testTitlebar(testDate);
  //       cy.contains('info').click();
  //       testTitlebar(testDate);
  //     });
  //   });
});
