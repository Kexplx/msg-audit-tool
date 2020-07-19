import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { RoutesService } from '../routes.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
  inInterviewList$: Observable<boolean>;
  inInterview$: Observable<boolean>;
  constructor(private routesService: RoutesService) {}

  ngOnInit() {
    this.inInterviewList$ = this.routesService.inInterviewsList$;
    this.inInterview$ = this.routesService.inInterview$;
  }
}
