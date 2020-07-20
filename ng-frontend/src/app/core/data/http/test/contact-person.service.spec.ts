import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ContactPersonService } from '../contact-person.service';
import { CONTACTPERSONS } from './dummies/app-models/contact-persons';
import { ContactPerson } from '../../models/contact-person.model';
import { environment } from 'src/environments/environment';

describe('ContactPersonService', () => {
  let service: ContactPersonService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ContactPersonService],
    });

    service = TestBed.inject(ContactPersonService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('#getContactPersons should return an observable of contact persons', () => {
    const contactPersonsResponse = CONTACTPERSONS;
    service.getContactPersons().subscribe(contactPersons => {
      for (const [i, contactPerson] of contactPersons.entries()) {
        verifyContactPerson(contactPerson, contactPersonsResponse[i]);
      }
    });

    const req = httpMock.expectOne(environment.baseUrl + 'contactpersons');
    expect(req.request.method).toEqual('GET');

    req.flush(contactPersonsResponse);
    httpMock.verify();
  });

  it('#getContactPerson should return an observable of a contact person', () => {
    const contactPersonResponse = CONTACTPERSONS[0];
    service.getContactPerson(1).subscribe(contactPerson => {
      verifyContactPerson(contactPerson, contactPersonResponse);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'contactpersons/' + 1);
    expect(req.request.method).toEqual('GET');

    req.flush(contactPersonResponse);
    httpMock.verify();
  });

  it('#postContactPerson should return an observable of a contact person', () => {
    const contactPersonResponse = CONTACTPERSONS[0];
    service.postContactPerson({} as ContactPerson).subscribe(contactPerson => {
      verifyContactPerson(contactPerson, contactPersonResponse);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'contactpersons');
    expect(req.request.method).toEqual('POST');

    req.flush(contactPersonResponse);
    httpMock.verify();
  });

  it('#putContactPerson should return an observable of a contact person', () => {
    const contactPersonResponse = CONTACTPERSONS[0];
    service.putContactPerson({ id: 1 } as ContactPerson).subscribe(contactPerson => {
      verifyContactPerson(contactPerson, contactPersonResponse);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'contactpersons/' + 1);
    expect(req.request.method).toEqual('PUT');

    req.flush(contactPersonResponse);
    httpMock.verify();
  });

  function verifyContactPerson(contactPerson: ContactPerson, contactPersonDto: ContactPerson) {
    expect(contactPerson).toEqual(contactPersonDto);
  }
});
