import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarAuditsComponent } from './sidebar-audits.component';

describe('SidebarAuditsComponent', () => {
  let component: SidebarAuditsComponent;
  let fixture: ComponentFixture<SidebarAuditsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SidebarAuditsComponent ]
    })
    .compileComponents();
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
