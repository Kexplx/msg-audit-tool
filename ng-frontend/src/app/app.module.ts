import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { AppRouterModule } from './app-routing.module';

@NgModule({
  declarations: [AppComponent],
  imports: [SharedModule, CoreModule, AppRouterModule],
  bootstrap: [AppComponent],
})
export class AppModule {}
