import { AuditListComponent } from './audit-list.component';
import { TestBed, ComponentFixture } from '@angular/core/testing';
import { AppNebularModule } from '../app-nebular.module';
import { RouterModule } from '@angular/router';

describe('AuditListComponent', () => {
  let component: AuditListComponent;
  let fixture: ComponentFixture<AuditListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuditListComponent],
      imports: [AppNebularModule, RouterModule.forRoot([])],
    });

    fixture = TestBed.createComponent(AuditListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
