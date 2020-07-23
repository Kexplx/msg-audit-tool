import { Injectable } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { filter } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class IdService {
  auditId$ = new BehaviorSubject<number>(null);
  facCritId$ = new BehaviorSubject<number>(null);
  interviewId$ = new BehaviorSubject<number>(null);
  contactPersonId$ = new BehaviorSubject<number>(null);

  constructor(private router: Router) {}

  subscribeToNavigation(): void {
    this.router.events
      ?.pipe(filter(obj => obj instanceof NavigationEnd))
      .subscribe(({ url }: NavigationEnd) => {
        const ids = this.getIdsFromUrl(url);

        this.auditId$.next(ids[0]);
        this.contactPersonId$.next(ids[0]);
        this.interviewId$.next(ids[1]);
        this.facCritId$.next(ids[2]);
      });
  }

  private getIdsFromUrl(url: string): number[] {
    const idRegex = /(\d*)/gm;
    const ids: number[] = [];

    let match: RegExpExecArray;
    while ((match = idRegex.exec(url)) !== null) {
      if (match.index === idRegex.lastIndex) {
        idRegex.lastIndex++;
      }

      if (match[1]) ids.push(+match[1]);
    }

    return ids;
  }
}
