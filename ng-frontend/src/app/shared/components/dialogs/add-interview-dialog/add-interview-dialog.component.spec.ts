import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddInterviewDialogComponent } from './add-interview-dialog.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';
import { Router } from '@angular/router';

describe('NewInterviewDialogComponent', () => {
  let component: AddInterviewDialogComponent;
  let fixture: ComponentFixture<AddInterviewDialogComponent>;

  beforeEach(async(() => {
    const routerStub = { url: '/audits/123/interviews' };
    TestBed.configureTestingModule({
      declarations: [AddInterviewDialogComponent],
      imports: [SharedModule, CoreModule],
      providers: [{ provide: Router, useValue: routerStub }],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddInterviewDialogComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
