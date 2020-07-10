/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactPersonsComponent } from './contact-persons.component';

describe('ContactPersonsListComponent', () => {
  let component: ContactPersonsComponent;
  let fixture: ComponentFixture<ContactPersonsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ContactPersonsComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContactPersonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
