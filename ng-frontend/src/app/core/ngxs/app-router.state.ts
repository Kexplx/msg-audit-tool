import { State, Selector, Action, StateContext, NgxsOnInit } from '@ngxs/store';
import { Injectable, NgZone } from '@angular/core';
import { Navigate } from './actions/router.actions';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

export interface AppRouterStateModel {
  auditId: string;
  interviewId: string;
  contactPersonId: string;
  facCritId: string;

  inAuditList: boolean;
  inAuditListEdit: boolean;
  inAuditOverview: boolean;
  inInterview: boolean;
  inContactPeopleList: boolean;
  inContactPeopleListEdit: boolean;
}

/**
 * State for handling the routing of the application.
 *
 * Has:
 * Action handler to navigate by url.
 * Static selectors to select route param id's of audits, interviews, contactPerson and facCrit.
 */
@State<AppRouterStateModel>({
  name: 'routerState',
  defaults: {
    auditId: null,
    contactPersonId: null,
    facCritId: null,
    interviewId: null,

    inAuditList: false,
    inAuditListEdit: false,
    inAuditOverview: false,
    inInterview: false,
    inContactPeopleList: false,
    inContactPeopleListEdit: false,
  },
})
@Injectable()
export class AppRouterState implements NgxsOnInit {
  constructor(private router: Router, private ngZone: NgZone) {}

  /**
   * Subscribes to the router's completed navigation events and retrieves
   * the query parameters of Audit, ContactPerson, FacCrit and Interview ID's
   * from the current url.
   */
  ngxsOnInit({ patchState }: StateContext<AppRouterStateModel>) {
    const auditListRegex = /^\/audits$/;
    const auditListEditRegex = /^\/audits\/([^\/]*)\/edit$/;
    const auditOverviewRegex = /^\/audits\/([^\/]*)\/interviews(\/new){0,1}$/;
    const interviewRegex = /^\/audits\/([^\/]*)\/interviews\/([^\/]*)\/([^\/]*)$/;
    const contactPeopleRegex = /^\/contact-people$/;
    const contactPersonEdit = /^\/contact-people\/([^\/]*)\/edit$/;

    this.router.events
      .pipe(filter(obj => obj instanceof NavigationEnd))
      .subscribe(({ url }: NavigationEnd) => {
        const params = url.split('/');

        if (params[1] === 'audits') {
          patchState({
            auditId: params[2] ?? null,
            interviewId: params[4] ?? null,
            facCritId: params[5] ?? null,
            contactPersonId: null,
          });
        } else if (params[1] === 'contact-people') {
          patchState({
            contactPersonId: params[2],
            auditId: null,
            interviewId: null,
            facCritId: null,
          });
        }

        patchState({
          inAuditList: auditListRegex.test(url),
          inAuditListEdit: auditListEditRegex.test(url),
          inAuditOverview: auditOverviewRegex.test(url),
          inInterview: interviewRegex.test(url),
          inContactPeopleList: contactPeopleRegex.test(url),
          inContactPeopleListEdit: contactPersonEdit.test(url),
        });
      });
  }

  @Selector()
  static auditId(state: AppRouterStateModel) {
    return state.auditId;
  }

  @Selector()
  static contactPersonId(state: AppRouterStateModel) {
    return state.contactPersonId;
  }

  @Selector()
  static interviewId(state: AppRouterStateModel) {
    return state.interviewId;
  }

  @Selector()
  static facCritId(state: AppRouterStateModel) {
    return state.facCritId;
  }

  @Selector()
  static inAuditList(state: AppRouterStateModel) {
    return state.inAuditList;
  }

  @Selector()
  static inAuditListEdit(state: AppRouterStateModel) {
    return state.inAuditListEdit;
  }

  @Selector()
  static inAuditOverview(state: AppRouterStateModel) {
    return state.inAuditOverview;
  }

  @Selector()
  static inInterview(state: AppRouterStateModel) {
    return state.inInterview;
  }

  @Selector()
  static inContactPeopleList(state: AppRouterStateModel) {
    return state.inContactPeopleList;
  }

  @Selector()
  static inContactPeopleListEdit(state: AppRouterStateModel) {
    return state.inContactPeopleListEdit;
  }

  /**
   * Navigates to the given route.
   */
  @Action(Navigate)
  navigate(_: StateContext<AppRouterStateModel>, { route }: Navigate) {
    this.ngZone.run(() => this.router.navigate([route]));
  }
}
