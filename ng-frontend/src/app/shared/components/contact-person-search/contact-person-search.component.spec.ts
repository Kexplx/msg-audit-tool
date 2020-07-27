import { ContactPersonSearchComponent } from './contact-person-search.component';
import { of } from 'rxjs';
import { CONTACTPERSONS } from 'src/app/core/data/http/test/dummies/app-models/contact-persons';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { EventEmitter } from '@angular/core';

describe('ContactPersonSearchComponent', () => {
  let component: ContactPersonSearchComponent;
  let contactPersons$Spy: jasmine.Spy;
  let contactPersonStoreMock: any;
  let allContactPersons: ContactPerson[];

  beforeEach(() => {
    allContactPersons = CONTACTPERSONS;

    // this is necessary to create spyProperty on line 27.
    contactPersonStoreMock = {
      get contactPersons$() {
        return of(true);
      },
    };

    component = new ContactPersonSearchComponent(contactPersonStoreMock);
  });

  describe('#ngOnInit', () => {
    beforeEach(() => {
      contactPersons$Spy = spyOnProperty(
        contactPersonStoreMock,
        'contactPersons$',
        'get',
      ).and.returnValue(of(allContactPersons));
    });

    it('subscribes to contactPersons$ and assigns contactPersons and filteredContactPersons$', () => {
      component.ngOnInit();

      component.filteredContactPersons$.subscribe(contactPersons => {
        expect(contactPersons).toEqual(allContactPersons);
      });

      expect(contactPersons$Spy.calls.count()).toEqual(1);
    });
  });

  describe('#onContactPersonSelect', () => {
    let emitterSpy: jasmine.SpyObj<EventEmitter<ContactPerson>>;
    beforeEach(() => {
      emitterSpy = jasmine.createSpyObj(['emit']);
      component.contactPersonSelected = emitterSpy;
    });

    it('should call #emit on contactPersonSelected event emitter', () => {
      const contactPerson = allContactPersons[0];
      component.onContactPersonSelect(contactPerson);

      expect(emitterSpy.emit.calls.count()).toEqual(1);
      expect(emitterSpy.emit).toHaveBeenCalledWith(contactPerson);
    });
  });

  describe('#onInput', () => {
    it('assigns filtered #contactPersons to #filteredContactPersons', () => {
      // Fill private property to simulate #ngOnInit.
      (component as any).contactPersons = allContactPersons;

      const contactPerson = allContactPersons[0];
      const forenameSnippet = contactPerson.forename.slice(0, 3); // first three characters.

      component.onInput(forenameSnippet);

      component.filteredContactPersons$.subscribe(contactPersons => {
        const contactPersonsWithForenameSnippet = allContactPersons.filter(cp =>
          cp.forename.toLocaleLowerCase().startsWith(forenameSnippet),
        );

        expect(contactPersons).toEqual(contactPersonsWithForenameSnippet);
      });
    });
  });
});
