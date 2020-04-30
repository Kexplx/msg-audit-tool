import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAuditDialogComponent } from './add-audit-dialog.component';
import { NbDialogService } from '@nebular/theme';

describe('AddAuditProjectDialogComponent', () => {
  let component: AddAuditDialogComponent;
  let fixture: ComponentFixture<AddAuditDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddAuditDialogComponent],
      providers: [NbDialogService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAuditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
