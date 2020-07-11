import { Component } from '@angular/core';
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
  @Select(AppRouterState.inInterviewsList) inInterviewsList$: Observable<boolean>;
  @Select(AppRouterState.inAuditList) inAuditList$: Observable<boolean>;
  @Select(AppRouterState.inContactPersonsList) inContactPersonsList$: Observable<boolean>;
}
