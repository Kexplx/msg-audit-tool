import { NgModule, Optional, SkipSelf } from '@angular/core';
import { NgxsModule } from '@ngxs/store';
import { AuditState } from './ngxs/audit.state';
import { environment } from 'src/environments/environment';
import { NgxsReduxDevtoolsPluginModule } from '@ngxs/devtools-plugin';
import { NgxsLoggerPluginModule } from '@ngxs/logger-plugin';
import { ContactPersonState } from './ngxs/contact-people.state';
import { AnswerState } from './ngxs/answer.state';
import { AppRouterState } from './ngxs/app-router.state';
import { InterviewState } from './ngxs/interview.state';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    HttpClientModule,
    NgxsLoggerPluginModule.forRoot(),
    NgxsReduxDevtoolsPluginModule.forRoot(),
    NgxsModule.forRoot(
      [AuditState, InterviewState, ContactPersonState, AppRouterState, AnswerState],
      {
        developmentMode: !environment.production,
      },
    ),
  ],
  exports: [NgxsModule],
})
export class CoreModule {
  /**
   * Throws an error if a second instance of CoreModule is created
   */
  constructor(@Optional() @SkipSelf() coreModule: CoreModule) {
    if (coreModule) {
      throw new Error('CoreModule is already loaded. Import it in the AppModule only');
    }
  }
}
