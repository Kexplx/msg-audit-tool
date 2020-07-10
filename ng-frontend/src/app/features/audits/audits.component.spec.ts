import { AuditsComponent } from './audits.component';
import { TestBed, ComponentFixture } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { AppModule } from 'src/app/app.module';
import { SharedModule } from 'src/app/shared/shared.module';

describe('AuditListComponent', () => {
  let component: AuditsComponent;
  let fixture: ComponentFixture<AuditsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuditsComponent],
      imports: [RouterModule.forRoot([]), SharedModule, AppModule],
    });

    fixture = TestBed.createComponent(AuditsComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
