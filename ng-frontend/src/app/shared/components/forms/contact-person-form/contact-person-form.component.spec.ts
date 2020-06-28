/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactPersonFormComponent } from './contact-person-form.component';
import { FormBuilder } from '@angular/forms';
import { SharedModule } from 'src/app/shared/shared.module';

describe('ContactPersonFormComponent', () => {
  let component: ContactPersonFormComponent;
  let fixture: ComponentFixture<ContactPersonFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ContactPersonFormComponent],
      providers: [FormBuilder],
      imports: [SharedModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContactPersonFormComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
