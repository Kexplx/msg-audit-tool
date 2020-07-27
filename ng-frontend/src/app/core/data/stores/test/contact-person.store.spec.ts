import { ContactPersonStore } from '../contact-person.store';
import { ContactPersonService } from '../../http/contact-person.service';
import { StoreActionService } from '../store-action.service';
import { CONTACTPERSONS } from '../../http/test/dummies/app-models/contact-persons';
import { of } from 'rxjs';
import { filter, skip } from 'rxjs/operators';
import { ContactPerson } from '../../models/contact-person.model';

describe('ContactPersonStore', () => {
  let contactPersonStore: ContactPersonStore;
  let contactPersonServiceSpy: jasmine.SpyObj<ContactPersonService>;
  let storeActionServiceSpy: jasmine.SpyObj<StoreActionService>;

  beforeEach(() => {
    contactPersonServiceSpy = jasmine.createSpyObj<ContactPersonService>([
      'getContactPersons',
      'postContactPerson',
      'putContactPerson',
    ]);
    storeActionServiceSpy = jasmine.createSpyObj<StoreActionService>([
      'notifyLoad',
      'notifyAdd',
      'notifyEdit',
    ]);

    contactPersonStore = new ContactPersonStore(contactPersonServiceSpy, storeActionServiceSpy);
  });

  describe('#loadContactPersons', () => {
    beforeEach(() => {
      contactPersonServiceSpy.getContactPersons.and.returnValue(of(CONTACTPERSONS));
    });

    it('should call #getContactPersons once', () => {
      contactPersonStore.loadContactPersons();

      expect(contactPersonServiceSpy.getContactPersons.calls.count()).toEqual(1);
    });

    it('should set _contactPersons$.value to the response', () => {
      contactPersonStore.contactPersons$
        .pipe(filter(contactPersons => contactPersons != null))
        .subscribe(contactPersons => {
          expect(contactPersons).toEqual(CONTACTPERSONS);
        });

      contactPersonStore.loadContactPersons();
    });
  });

  describe('#addContactPerson', () => {
    let contactPersonResponse: ContactPerson;
    beforeEach(() => {
      contactPersonResponse = CONTACTPERSONS[0];
      contactPersonStore['_contactPersons$'].next([]);
      contactPersonServiceSpy.postContactPerson.and.returnValue(of(contactPersonResponse));
    });

    it('should call #postContactPerson', () => {
      contactPersonStore.addContactPerson({} as ContactPerson);

      expect(contactPersonServiceSpy.postContactPerson.calls.count()).toEqual(1);
    });

    it('should add the added contactPerson to _contactPersons$.value', () => {
      contactPersonStore.contactPersons$.pipe(skip(1)).subscribe(contactPersons => {
        expect(contactPersons[0]).toEqual(contactPersonResponse);
      });

      contactPersonStore.addContactPerson({} as ContactPerson);
    });

    it('should call #notifyAdd once', () => {
      contactPersonStore.addContactPerson({} as ContactPerson);

      expect(storeActionServiceSpy.notifyAdd.calls.count()).toEqual(1);
    });
  });

  describe('#updateContactPerson', () => {
    let contactPersonResponse: ContactPerson;
    beforeEach(() => {
      contactPersonResponse = CONTACTPERSONS[0];
      contactPersonStore['_contactPersons$'].next([
        { ...contactPersonResponse, forename: 'OLD VALUE' },
      ]);

      contactPersonServiceSpy.putContactPerson.and.returnValue(of(contactPersonResponse));
    });

    it('should call #putContactPerson once', () => {
      contactPersonStore.updateContactPerson({} as ContactPerson);

      expect(contactPersonServiceSpy.putContactPerson.calls.count()).toEqual(1);
    });

    it('should update the contactPersons forename on index 0 ', () => {
      contactPersonStore.updateContactPerson({} as ContactPerson);

      contactPersonStore.contactPersons$
        .pipe(filter(cp => cp.length > 0))
        .subscribe(contactPersons => {
          expect(contactPersons[0].forename).not.toEqual('OLD VALUE');
        });
    });

    it('should call #notifyEdit once', () => {
      contactPersonStore.updateContactPerson({} as ContactPerson);

      expect(storeActionServiceSpy.notifyEdit.calls.count()).toEqual(1);
    });
  });
});
