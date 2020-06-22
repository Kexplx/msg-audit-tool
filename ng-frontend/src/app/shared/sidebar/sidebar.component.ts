import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
  title: string;
  display: string;
  url: string;

  constructor(private router: Router) {}

  ngOnInit() {
    const interviewList = /\/audits\/.*\/interviews$/;
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: any) => {
        if (interviewList.test(event.url)) {
          this.display = 'INTERVIEWLIST';
        } else {
          this.display = 'DEFAULT';
        }

        this.url = event.url;
      });
  }
}
