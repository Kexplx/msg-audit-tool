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
    const nullFiller: AppRouterStateModel = {
      auditId: null,
      contactPersonId: null,
      facCritId: null,
      interviewId: null,
    };

    const auditListRegex = /^\/audits$/;
    const auditListEditRegex = /^\/audits\/([^\/]*)\/edit$/;
    const auditOverviewRegex = /^\/audits\/([^\/]*)\/interviews$/;
    const interviewRegex = /^\/audits\/([^\/]*)\/interviews\/([^\/]*)\/([^\/]*)$/;
    const contactPeopleRegex = /^\/contact-people$/;
    const contactPersonEdit = /^\/contact-people\/([^\/]*)\/edit$/;

    this.router.events
      .pipe(filter(obj => obj instanceof NavigationEnd))
      .subscribe(({ url }: NavigationEnd) => {
        let match: RegExpExecArray;

        match = auditListRegex.exec(url);
        if (match) return patchState({ ...nullFiller });

        match = auditListEditRegex.exec(url) ?? auditOverviewRegex.exec(url);
        if (match) return patchState({ ...nullFiller, auditId: match[1] });

        match = interviewRegex.exec(url);
        if (match)
          return patchState({
            ...nullFiller,
            auditId: match[1],
            interviewId: match[2],
            facCritId: match[3],
          });

        match = contactPeopleRegex.exec(url);
        if (match) return patchState({ ...nullFiller });

        match = contactPersonEdit.exec(url);
        if (match) return patchState({ ...nullFiller, contactPersonId: match[1] });

        return patchState({ ...nullFiller });
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

  /**
   * Navigates to the given route.
   */
  @Action(Navigate)
  navigate(_: StateContext<AppRouterStateModel>, { route }: Navigate) {
    this.ngZone.run(() => this.router.navigate([route]));
  }
}
