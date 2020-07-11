import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarContactPersonsComponent } from './sidebar-contact-persons.component';

describe('SidebarContactPersonsComponent', () => {
  let component: SidebarContactPersonsComponent;
  let fixture: ComponentFixture<SidebarContactPersonsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SidebarContactPersonsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarContactPersonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
