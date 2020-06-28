import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterviewComponent } from './interview.component';
import { RouterModule } from '@angular/router';
import { CoreModule } from 'src/app/core/core.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

describe('InterviewComponent', () => {
  let component: InterviewComponent;
  let fixture: ComponentFixture<InterviewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InterviewComponent],
      imports: [RouterModule.forRoot([]), CoreModule, FormsModule, ReactiveFormsModule],
    }).compileComponents();

    fixture = TestBed.createComponent(InterviewComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
