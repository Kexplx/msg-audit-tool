'use strict';


customElements.define('compodoc-menu', class extends HTMLElement {
    constructor() {
        super();
        this.isNormalMode = this.getAttribute('mode') === 'normal';
    }

    connectedCallback() {
        this.render(this.isNormalMode);
    }

    render(isNormalMode) {
        let tp = lithtml.html(`
        <nav>
            <ul class="list">
                <li class="title">
                    <a href="index.html" data-type="index-link">MSG Audit Tool Documentation</a>
                </li>

                <li class="divider"></li>
                ${ isNormalMode ? `<div id="book-search-input" role="search"><input type="text" placeholder="Type to search"></div>` : '' }
                <li class="chapter">
                    <a data-type="chapter-link" href="index.html"><span class="icon ion-ios-home"></span>Getting started</a>
                    <ul class="links">
                        <li class="link">
                            <a href="index.html" data-type="chapter-link">
                                <span class="icon ion-ios-keypad"></span>Overview
                            </a>
                        </li>
                                <li class="link">
                                    <a href="dependencies.html" data-type="chapter-link">
                                        <span class="icon ion-ios-list"></span>Dependencies
                                    </a>
                                </li>
                    </ul>
                </li>
                    <li class="chapter modules">
                        <a data-type="chapter-link" href="modules.html">
                            <div class="menu-toggler linked" data-toggle="collapse" ${ isNormalMode ?
                                'data-target="#modules-links"' : 'data-target="#xs-modules-links"' }>
                                <span class="icon ion-ios-archive"></span>
                                <span class="link-name">Modules</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                        </a>
                        <ul class="links collapse " ${ isNormalMode ? 'id="modules-links"' : 'id="xs-modules-links"' }>
                            <li class="link">
                                <a href="modules/AppModule.html" data-type="entity-link">AppModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-AppModule-91a000f657816f6139588be6a2f21bce"' : 'data-target="#xs-components-links-module-AppModule-91a000f657816f6139588be6a2f21bce"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AppModule-91a000f657816f6139588be6a2f21bce"' :
                                            'id="xs-components-links-module-AppModule-91a000f657816f6139588be6a2f21bce"' }>
                                            <li class="link">
                                                <a href="components/AppComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AppComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/AppRouterModule.html" data-type="entity-link">AppRouterModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/AuditListModule.html" data-type="entity-link">AuditListModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-AuditListModule-1bcf2202529fc6148bb47189ecb87913"' : 'data-target="#xs-components-links-module-AuditListModule-1bcf2202529fc6148bb47189ecb87913"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AuditListModule-1bcf2202529fc6148bb47189ecb87913"' :
                                            'id="xs-components-links-module-AuditListModule-1bcf2202529fc6148bb47189ecb87913"' }>
                                            <li class="link">
                                                <a href="components/AuditCardComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AuditCardComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/AuditListComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AuditListComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/AuditListRoutingModule.html" data-type="entity-link">AuditListRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/AuditOverviewModule.html" data-type="entity-link">AuditOverviewModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-AuditOverviewModule-70ac038fa919633b883ea5e48bee843e"' : 'data-target="#xs-components-links-module-AuditOverviewModule-70ac038fa919633b883ea5e48bee843e"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AuditOverviewModule-70ac038fa919633b883ea5e48bee843e"' :
                                            'id="xs-components-links-module-AuditOverviewModule-70ac038fa919633b883ea5e48bee843e"' }>
                                            <li class="link">
                                                <a href="components/AuditInfoComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AuditInfoComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/AuditOverviewComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AuditOverviewComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/InterviewCardComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">InterviewCardComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/InterviewFormComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">InterviewFormComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/InterviewListComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">InterviewListComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NewInterviewDialogComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">NewInterviewDialogComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#pipes-links-module-AuditOverviewModule-70ac038fa919633b883ea5e48bee843e"' : 'data-target="#xs-pipes-links-module-AuditOverviewModule-70ac038fa919633b883ea5e48bee843e"' }>
                                            <span class="icon ion-md-add"></span>
                                            <span>Pipes</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="pipes-links-module-AuditOverviewModule-70ac038fa919633b883ea5e48bee843e"' :
                                            'id="xs-pipes-links-module-AuditOverviewModule-70ac038fa919633b883ea5e48bee843e"' }>
                                            <li class="link">
                                                <a href="pipes/FilterInterviewsPipe.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">FilterInterviewsPipe</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/AuditOverviewRoutingModule.html" data-type="entity-link">AuditOverviewRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/ContactPeopleModule.html" data-type="entity-link">ContactPeopleModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-ContactPeopleModule-868ba40dac9ff29bf7b70c932cb30a5c"' : 'data-target="#xs-components-links-module-ContactPeopleModule-868ba40dac9ff29bf7b70c932cb30a5c"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-ContactPeopleModule-868ba40dac9ff29bf7b70c932cb30a5c"' :
                                            'id="xs-components-links-module-ContactPeopleModule-868ba40dac9ff29bf7b70c932cb30a5c"' }>
                                            <li class="link">
                                                <a href="components/ContactPeopleListComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ContactPeopleListComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ContactPersonCardComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ContactPersonCardComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/ContactPeopleRoutingModule.html" data-type="entity-link">ContactPeopleRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/CoreModule.html" data-type="entity-link">CoreModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/NebularModule.html" data-type="entity-link">NebularModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/SharedModule.html" data-type="entity-link">SharedModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-SharedModule-11ce17253cac1e5da0c090434422f770"' : 'data-target="#xs-components-links-module-SharedModule-11ce17253cac1e5da0c090434422f770"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-SharedModule-11ce17253cac1e5da0c090434422f770"' :
                                            'id="xs-components-links-module-SharedModule-11ce17253cac1e5da0c090434422f770"' }>
                                            <li class="link">
                                                <a href="components/AddAuditDialogComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AddAuditDialogComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/AddContactPersonDialogComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AddContactPersonDialogComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/AuditDataFormComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AuditDataFormComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ConfirmDiscardDialogComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ConfirmDiscardDialogComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ContactPersonFormComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ContactPersonFormComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/EditAuditDialogComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">EditAuditDialogComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NavbarComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">NavbarComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NotFoundComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">NotFoundComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#directives-links-module-SharedModule-11ce17253cac1e5da0c090434422f770"' : 'data-target="#xs-directives-links-module-SharedModule-11ce17253cac1e5da0c090434422f770"' }>
                                        <span class="icon ion-md-code-working"></span>
                                        <span>Directives</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="directives-links-module-SharedModule-11ce17253cac1e5da0c090434422f770"' :
                                        'id="xs-directives-links-module-SharedModule-11ce17253cac1e5da0c090434422f770"' }>
                                        <li class="link">
                                            <a href="directives/ActionListenerDirective.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules">ActionListenerDirective</a>
                                        </li>
                                    </ul>
                                </li>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#pipes-links-module-SharedModule-11ce17253cac1e5da0c090434422f770"' : 'data-target="#xs-pipes-links-module-SharedModule-11ce17253cac1e5da0c090434422f770"' }>
                                            <span class="icon ion-md-add"></span>
                                            <span>Pipes</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="pipes-links-module-SharedModule-11ce17253cac1e5da0c090434422f770"' :
                                            'id="xs-pipes-links-module-SharedModule-11ce17253cac1e5da0c090434422f770"' }>
                                            <li class="link">
                                                <a href="pipes/SortAuditPipe.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">SortAuditPipe</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                </ul>
                </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#classes-links"' :
                            'data-target="#xs-classes-links"' }>
                            <span class="icon ion-ios-paper"></span>
                            <span>Classes</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="classes-links"' : 'id="xs-classes-links"' }>
                            <li class="link">
                                <a href="classes/AddAudit.html" data-type="entity-link">AddAudit</a>
                            </li>
                            <li class="link">
                                <a href="classes/AddContactPerson.html" data-type="entity-link">AddContactPerson</a>
                            </li>
                            <li class="link">
                                <a href="classes/AddInterview.html" data-type="entity-link">AddInterview</a>
                            </li>
                            <li class="link">
                                <a href="classes/DeleteAudit.html" data-type="entity-link">DeleteAudit</a>
                            </li>
                            <li class="link">
                                <a href="classes/UpdateAudit.html" data-type="entity-link">UpdateAudit</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#injectables-links"' :
                                'data-target="#xs-injectables-links"' }>
                                <span class="icon ion-md-arrow-round-down"></span>
                                <span>Injectables</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse " ${ isNormalMode ? 'id="injectables-links"' : 'id="xs-injectables-links"' }>
                                <li class="link">
                                    <a href="injectables/AuditRegistryState.html" data-type="entity-link">AuditRegistryState</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#interfaces-links"' :
                            'data-target="#xs-interfaces-links"' }>
                            <span class="icon ion-md-information-circle-outline"></span>
                            <span>Interfaces</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? ' id="interfaces-links"' : 'id="xs-interfaces-links"' }>
                            <li class="link">
                                <a href="interfaces/Audit.html" data-type="entity-link">Audit</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/AuditRegistryStateModel.html" data-type="entity-link">AuditRegistryStateModel</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/ContactPerson.html" data-type="entity-link">ContactPerson</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/FacCrit.html" data-type="entity-link">FacCrit</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Interview.html" data-type="entity-link">Interview</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#miscellaneous-links"'
                            : 'data-target="#xs-miscellaneous-links"' }>
                            <span class="icon ion-ios-cube"></span>
                            <span>Miscellaneous</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="miscellaneous-links"' : 'id="xs-miscellaneous-links"' }>
                            <li class="link">
                                <a href="miscellaneous/enumerations.html" data-type="entity-link">Enums</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/variables.html" data-type="entity-link">Variables</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <a data-type="chapter-link" href="routes.html"><span class="icon ion-ios-git-branch"></span>Routes</a>
                        </li>
                    <li class="chapter">
                        <a data-type="chapter-link" href="coverage.html"><span class="icon ion-ios-stats"></span>Documentation coverage</a>
                    </li>
                    <li class="divider"></li>
                    <li class="copyright">
                        Documentation generated using <a href="https://compodoc.app/" target="_blank">
                            <img data-src="images/compodoc-vectorise.png" class="img-responsive" data-type="compodoc-logo">
                        </a>
                    </li>
            </ul>
        </nav>
        `);
        this.innerHTML = tp.strings;
    }
});