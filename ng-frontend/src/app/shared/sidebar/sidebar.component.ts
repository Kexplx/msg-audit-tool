import { Component, OnInit } from '@angular/core';
import { Select } from '@ngxs/store';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent {
  @Select(AppRouterState.inInterview) inInterview$: Observable<boolean>;
  @Select(AppRouterState.inAuditOverview) inAuditOverview$: Observable<boolean>;
}
