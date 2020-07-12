import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarContactPersonsComponent } from './sidebar-contact-persons.component';
import { SharedModule } from 'src/app/shared/shared.module';

describe('SidebarContactPersonsComponent', () => {
  let component: SidebarContactPersonsComponent;
  let fixture: ComponentFixture<SidebarContactPersonsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SidebarContactPersonsComponent],
      imports: [SharedModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarContactPersonsComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
