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
