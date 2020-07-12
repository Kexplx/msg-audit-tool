import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterviewComponent } from './interview.component';
import { CoreModule } from 'src/app/core/core.module';
import { RouterModule } from '@angular/router';

describe('InterviewComponent', () => {
  let component: InterviewComponent;
  let fixture: ComponentFixture<InterviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InterviewComponent],
      imports: [CoreModule, RouterModule.forRoot([])],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
