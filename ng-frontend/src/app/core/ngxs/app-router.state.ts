import { State, Selector, Action, StateContext, NgxsOnInit } from '@ngxs/store';
import { Injectable, NgZone } from '@angular/core';
import { Navigate } from './actions/router.actions';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

export interface AppRouterStateModel {
  auditId: number;
  interviewId: number;
  contactPersonId: number;
  facCritId: number;

  inAuditList: boolean;
  inAuditListEdit: boolean;
  inAuditOverview: boolean;
  inInterview: boolean;
  inContactPersonsList: boolean;
  inContactPersonsListEdit: boolean;
}

/**
 * State for handling the application's routing and routes.
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
    inContactPersonsList: false,
    inContactPersonsListEdit: false,
  },
})
@Injectable()
export class AppRouterState implements NgxsOnInit {
  constructor(private router: Router, private ngZone: NgZone) {}

  /**
   * Subscribes to the router's completed navigation events and retrieves
   * the current route and it's query parameters from the url.
   */
  ngxsOnInit({ patchState }: StateContext<AppRouterStateModel>) {
    const auditListRegex = /^\/audits$/;
    const contactPersonsRegex = /^\/contact-persons$/;
    const auditListEditRegex = /^\/audits\/[^\/]*\/edit$/;
    const contactPersonEdit = /^\/contact-persons\/[^\/]*\/edit$/;
    const auditOverviewRegex = /^\/audits\/[^\/]*\/interviews(\/new){0,1}$/;
    const interviewRegex = /^\/audits\/[^\/]*\/interviews\/[^\/]*\/[^\/]*$/;

    this.router.events
      ?.pipe(filter(obj => obj instanceof NavigationEnd))
      .subscribe(({ url }: NavigationEnd) => {
        const params = url.split('/');

        if (params[1] === 'audits') {
          patchState({
            auditId: isNaN(+params[2]) ? null : +params[2],
            interviewId: isNaN(+params[4]) ? null : +params[4],
            facCritId: isNaN(+params[5]) ? null : +params[5],
            contactPersonId: null,
          });
        } else {
          patchState({
            contactPersonId: isNaN(+params[2]) ? null : +params[2],
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
          inContactPersonsList: contactPersonsRegex.test(url),
          inContactPersonsListEdit: contactPersonEdit.test(url),
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
  static inContactPersonsList(state: AppRouterStateModel) {
    return state.inContactPersonsList;
  }

  @Selector()
  static inContactPersonsListEdit(state: AppRouterStateModel) {
    return state.inContactPersonsListEdit;
  }

  @Action(Navigate)
  navigate(_: StateContext<AppRouterStateModel>, { route }: Navigate) {
    this.ngZone.run(() => this.router.navigate([route]));
  }
}
