import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditFormComponent } from './audit-form.component';
import { FormBuilder } from '@angular/forms';
import { NbDialogService } from '@nebular/theme';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';

describe('AuditDataComponent', () => {
  let component: AuditFormComponent;
  let fixture: ComponentFixture<AuditFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuditFormComponent],
      imports: [SharedModule, BrowserAnimationsModule, CoreModule],
      providers: [NbDialogService, FormBuilder],
    });

    fixture = TestBed.createComponent(AuditFormComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
