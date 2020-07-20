import { NgModule, Optional, SkipSelf } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SidebarComponent } from './sidebar/sidebar.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { SidebarInterviewComponent } from './sidebar/sidebar-interview/sidebar-interview.component';
import { SidebarInterviewListComponent } from './sidebar/sidebar-interview-list/sidebar-interview-list.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    SidebarComponent,
    NotFoundComponent,
    SidebarInterviewComponent,
    SidebarInterviewListComponent,
  ],
  imports: [HttpClientModule, BrowserModule, SharedModule, BrowserAnimationsModule],
  exports: [NotFoundComponent, SidebarComponent],
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
