import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarInterviewComponent } from './sidebar-interview.component';
import { CoreModule } from 'src/app/core/core.module';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../../../shared.module';

describe('SidebarInterviewComponent', () => {
  let component: SidebarInterviewComponent;
  let fixture: ComponentFixture<SidebarInterviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SidebarInterviewComponent],
      imports: [CoreModule, RouterModule.forRoot([]), SharedModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarInterviewComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
