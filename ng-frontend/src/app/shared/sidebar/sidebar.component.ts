import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

enum SidebarState {
  InterviewList,
  Interview,
  Default,
}

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
  url: string;
  sidebarState: SidebarState;

  constructor(private router: Router) {}

  ngOnInit() {
    const interviewListRegex = /\/audits\/.*\/interviews$/;
    const interviewRegex = /\/audits\/[^\/]*\/interviews\/[^\/]*\/[^\/]*$/;

    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: any) => {
        const { url } = event;

        if (interviewListRegex.test(url)) {
          this.sidebarState = SidebarState.InterviewList;
        } else if (interviewRegex.test(url)) {
          this.sidebarState = SidebarState.Interview;
        } else {
          this.sidebarState = SidebarState.Default;
        }

        this.url = url;
      });
  }
}
