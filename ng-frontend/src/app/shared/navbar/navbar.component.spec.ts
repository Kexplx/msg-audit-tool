import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarComponent } from './navbar.component';
import { NbSidebarService } from '@nebular/theme';
import { DebugElement } from '@angular/core';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;
  let debugElement: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarComponent],
      providers: [NbSidebarService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should render navbar container', () => {
    const el = debugElement.nativeElement.querySelector('nav.navbar');
    expect(el).toBeDefined();
  });
});
