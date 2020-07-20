import { NgModule, Optional, SkipSelf } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { SidebarInterviewComponent } from './components/sidebar/sidebar-interview/sidebar-interview.component';
import { SidebarInterviewListComponent } from './components/sidebar/sidebar-interview-list/sidebar-interview-list.component';
import { SharedModule } from '../shared/shared.module';
import { NotFoundComponent } from './components/not-found/not-found.component';

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
