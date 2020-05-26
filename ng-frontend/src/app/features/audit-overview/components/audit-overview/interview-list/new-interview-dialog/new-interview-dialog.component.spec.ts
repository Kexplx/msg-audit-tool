import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewInterviewDialogComponent } from './new-interview-dialog.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';
import { Router } from '@angular/router';

describe('NewInterviewDialogComponent', () => {
  let component: NewInterviewDialogComponent;
  let fixture: ComponentFixture<NewInterviewDialogComponent>;

  beforeEach(async(() => {
    const routerStub = { url: '/audits/123/interviews' };
    TestBed.configureTestingModule({
      declarations: [NewInterviewDialogComponent],
      imports: [SharedModule, CoreModule],
      providers: [{ provide: Router, useValue: routerStub }],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewInterviewDialogComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
