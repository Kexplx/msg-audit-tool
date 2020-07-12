import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarAuditsComponent } from './sidebar-audits.component';
import { CoreModule } from '../../core.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { RouterModule } from '@angular/router';

describe('SidebarAuditsComponent', () => {
  let component: SidebarAuditsComponent;
  let fixture: ComponentFixture<SidebarAuditsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SidebarAuditsComponent],
      imports: [CoreModule, SharedModule, RouterModule.forRoot([])],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarAuditsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
