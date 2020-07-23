import { Injectable } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RoutesService {
  inInterviewsList$ = new Subject<boolean>();
  inInterview$ = new Subject<boolean>();

  constructor(private router: Router) {}

  subscribeToNavigation(): void {
    const inInterviewListRegex = /^\/audits\/[^\/]*\/interviews(\/new){0,1}$/;
    const inInterviewRegex = /^\/audits\/[^\/]*\/interviews\/[^\/]*\/[^\/]*$/;

    this.router.events
      ?.pipe(filter(obj => obj instanceof NavigationEnd))
      .subscribe(({ url }: NavigationEnd) => {
        this.inInterviewsList$.next(inInterviewListRegex.test(url));
        this.inInterview$.next(inInterviewRegex.test(url));
      });
  }
}
