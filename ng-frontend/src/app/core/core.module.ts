import { NgModule, Optional, SkipSelf } from '@angular/core';
import { NgxsModule } from '@ngxs/store';
import { AuditState } from './ngxs/audit.state';
import { environment } from 'src/environments/environment';
import { NgxsReduxDevtoolsPluginModule } from '@ngxs/devtools-plugin';
import { NgxsLoggerPluginModule } from '@ngxs/logger-plugin';
import { ContactPersonState } from './ngxs/contact-person.state';
import { AppRouterState } from './ngxs/app-router.state';
import { InterviewState } from './ngxs/interview.state';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActionListenerDirective } from './ngxs/action-listener.directive';
import { SidebarComponent } from './sidebar/sidebar.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { SidebarInterviewComponent } from './sidebar/sidebar-interview/sidebar-interview.component';
import { SidebarInterviewListComponent } from './sidebar/sidebar-interview-list/sidebar-interview-list.component';
import { SharedModule } from '../shared/shared.module';
import { IdService } from './id.service';

@NgModule({
  declarations: [
    ActionListenerDirective,
    SidebarComponent,
    NotFoundComponent,
    SidebarInterviewComponent,
    SidebarInterviewListComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    SharedModule,
    BrowserAnimationsModule,
    NgxsLoggerPluginModule.forRoot(),
    NgxsReduxDevtoolsPluginModule.forRoot(),
    NgxsModule.forRoot([AuditState, InterviewState, ContactPersonState, AppRouterState], {
      developmentMode: !environment.production,
    }),
  ],
  exports: [NgxsModule, ActionListenerDirective, NotFoundComponent, SidebarComponent],
})
export class CoreModule {
  /**
   * Throws an error if a second instance of CoreModule is created
   */
  constructor(@Optional() @SkipSelf() coreModule: CoreModule, private r: IdService) {
    if (coreModule) {
      throw new Error('CoreModule is already loaded. Import it in the AppModule only');
    }
  }
}
