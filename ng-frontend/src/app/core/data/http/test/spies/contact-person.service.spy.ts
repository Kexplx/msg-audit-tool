import { ContactPersonService } from '../../contact-person.service';
import { of } from 'rxjs';
import { CONTACTPERSONS } from '../dummies/app-models/contact-persons';

export const contactPersonServiceSpy: jasmine.SpyObj<ContactPersonService> = jasmine.createSpyObj(
  'ContactPersonService',
  ['putContactPerson', 'getContactPersons', 'getContactPerson', 'postContactPerson'],
);

contactPersonServiceSpy.getContactPersons.and.returnValue(of(CONTACTPERSONS));
contactPersonServiceSpy.getContactPerson.and.returnValue(of(CONTACTPERSONS[0]));
contactPersonServiceSpy.postContactPerson.and.returnValue(of(CONTACTPERSONS[0]));
contactPersonServiceSpy.putContactPerson.and.returnValue(of(CONTACTPERSONS[1]));
