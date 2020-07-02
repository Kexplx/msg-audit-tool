/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactPersonsListComponent } from './contact-persons-list.component';

describe('ContactPersonsListComponent', () => {
  let component: ContactPersonsListComponent;
  let fixture: ComponentFixture<ContactPersonsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ContactPersonsListComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContactPersonsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
