import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarInterviewListComponent } from './sidebar-interview-list.component';
import { CoreModule } from 'src/app/core/core.module';
import { FactorsPipe } from '../../pipes/factors.pipe';
import { CriteriaByFactorPipe } from '../../pipes/facCritByFactor.pipe';
import { SharedModule } from '../../shared.module';

describe('SidebarInterviewListComponent', () => {
  let component: SidebarInterviewListComponent;
  let fixture: ComponentFixture<SidebarInterviewListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SidebarInterviewListComponent],
      imports: [CoreModule, SharedModule],
      providers: [FactorsPipe, CriteriaByFactorPipe],
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
