import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditFormComponent } from './audit-form.component';
import { FormBuilder } from '@angular/forms';
import { NbDialogService } from '@nebular/theme';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from 'src/app/shared/shared.module';

describe('AuditDataComponent', () => {
  let component: AuditFormComponent;
  let fixture: ComponentFixture<AuditFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuditFormComponent],
      imports: [SharedModule, BrowserAnimationsModule],
      providers: [NbDialogService, FormBuilder],
    });

    fixture = TestBed.createComponent(AuditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
