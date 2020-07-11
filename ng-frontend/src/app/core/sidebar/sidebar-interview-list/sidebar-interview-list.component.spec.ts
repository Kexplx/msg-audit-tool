import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarInterviewListComponent } from './sidebar-interview-list.component';
import { CoreModule } from 'src/app/core/core.module';
import { FactorsPipe } from '../../../shared/pipes/factors.pipe';
import { CriteriaByFactorIdPipe } from '../../../features/audit-overview/interview-list/criteria-by-factor-id.pipe';
import { SharedModule } from '../../../shared/shared.module';
import { RouterModule } from '@angular/router';

describe('SidebarInterviewListComponent', () => {
  let component: SidebarInterviewListComponent;
  let fixture: ComponentFixture<SidebarInterviewListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SidebarInterviewListComponent],
      imports: [CoreModule, SharedModule, RouterModule.forRoot([])],
      providers: [FactorsPipe, CriteriaByFactorIdPipe],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarInterviewListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
