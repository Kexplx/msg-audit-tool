import { TestBed } from '@angular/core/testing';
import { Store, NgxsModule } from '@ngxs/store';
import { of } from 'rxjs';
import { HttpClientModule } from '@angular/common/http';
import { ContactPersonService } from '../../http/contact-person.service';
import { CONTACTPERSON_DTO_DUMMY } from '../../http/test/dummies/contact-persons';
import { ContactPersonState } from '../contact-person.state';
import { AddContactPerson, UpdateContactPerson } from '../actions/contact-person.action';
import { ContactPerson } from '../../data/models/contact-person.model';

describe('ContactPersonState', () => {
  let store: Store;
  let contactPersonServiceSpy: jasmine.SpyObj<ContactPersonService>;

  beforeEach(() => {
    contactPersonServiceSpy = jasmine.createSpyObj('ContactPersonService', [
      'putContactPerson',
      'getContactPersons',
      'getContactPerson',
      'postContactPerson',
    ]);

    contactPersonServiceSpy.getContactPersons.and.returnValue(of(CONTACTPERSON_DTO_DUMMY));
    contactPersonServiceSpy.getContactPerson.and.returnValue(of(CONTACTPERSON_DTO_DUMMY[0]));
    contactPersonServiceSpy.postContactPerson.and.returnValue(of(CONTACTPERSON_DTO_DUMMY[0]));
    contactPersonServiceSpy.putContactPerson.and.returnValue(of(CONTACTPERSON_DTO_DUMMY[1]));

    TestBed.configureTestingModule({
      imports: [NgxsModule.forRoot([ContactPersonState]), HttpClientModule],
      providers: [{ provide: ContactPersonService, useValue: contactPersonServiceSpy }],
    });

    store = TestBed.inject(Store);
  });

  it('#ngxsOnInit calls #getContactPersons once and fills the state with the results', () => {
    // ngxsOnInit() called by framework...
    const contactPersons = store.selectSnapshot(ContactPersonState.contactPersons);

    expect(contactPersons).toEqual(CONTACTPERSON_DTO_DUMMY);
    expect(contactPersonServiceSpy.getContactPersons.calls.count()).toEqual(1);
  });

  it('#contactPerson returns a contactPerson by id', () => {
    const contactPersons = store.selectSnapshot(ContactPersonState.contactPersons);
    const contactPerson = store.selectSnapshot(
      ContactPersonState.contactPerson(contactPersons[0].id),
    );

    expect(contactPersons[0]).toEqual(contactPerson);
  });

  it('#addContactPerson calls #postContactPerson once and adds the new person to the state', () => {
    const contactPersonsOld = store.selectSnapshot(ContactPersonState.contactPersons);
    store.dispatch(new AddContactPerson({} as ContactPerson));
    const contactPersonsNew = store.selectSnapshot(ContactPersonState.contactPersons);

    expect(contactPersonsNew.length - contactPersonsOld.length).toEqual(1);
    expect(contactPersonsNew.slice(-1)[0]).toEqual(CONTACTPERSON_DTO_DUMMY[0]);

    expect(contactPersonServiceSpy.postContactPerson.calls.count()).toEqual(1);
  });

  it('#updateContactPerson calls #putContactPerson once and updates the contactPerson in the state', () => {
    const contactPersonsOld = store.selectSnapshot(ContactPersonState.contactPersons);
    store.dispatch(new UpdateContactPerson(0, {} as ContactPerson));
    const contactPersonsNew = store.selectSnapshot(ContactPersonState.contactPersons);

    expect(contactPersonsNew.length - contactPersonsOld.length).toEqual(0);
    expect(contactPersonsNew[1]).toEqual(CONTACTPERSON_DTO_DUMMY[1]);

    expect(contactPersonServiceSpy.putContactPerson.calls.count()).toEqual(1);
  });
});
