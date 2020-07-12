import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as karma from 'karma-jasmine';
import { ContactPersonService } from '../contact-person.service';
import { CONTACTPERSON_DTO_DUMMY } from './dummies/contact-persons';
import { ContactPerson } from '../../data/models/contact-person.model';
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
    const contactPersonsDto = CONTACTPERSON_DTO_DUMMY;
    service.getContactPersons().subscribe(contactPersons => {
      for (const [i, contactPerson] of contactPersons.entries()) {
        verifyContactPerson(contactPerson, contactPersonsDto[i]);
      }
    });

    const req = httpMock.expectOne(environment.baseUrl + 'contactpersons');
    expect(req.request.method).toEqual('GET');

    req.flush(contactPersonsDto);
    httpMock.verify();
  });

  it('#getContactPerson should return an observable of a contact person', () => {
    const contactPersonDto = CONTACTPERSON_DTO_DUMMY[0];
    service.getContactPerson(1).subscribe(contactPerson => {
      verifyContactPerson(contactPerson, contactPersonDto);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'contactpersons/' + 1);
    expect(req.request.method).toEqual('GET');

    req.flush(contactPersonDto);
    httpMock.verify();
  });

  it('#postContactPerson should return an observable of a contact person', () => {
    const contactPersonDto = CONTACTPERSON_DTO_DUMMY[0];
    service.postContactPerson({} as ContactPerson).subscribe(contactPerson => {
      verifyContactPerson(contactPerson, contactPersonDto);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'contactpersons');
    expect(req.request.method).toEqual('POST');

    req.flush(contactPersonDto);
    httpMock.verify();
  });

  it('#putContactPerson should return an observable of a contact person', () => {
    const contactPersonDto = CONTACTPERSON_DTO_DUMMY[0];
    service.putContactPerson({ id: 1 } as ContactPerson).subscribe(contactPerson => {
      verifyContactPerson(contactPerson, contactPersonDto);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'contactpersons/' + 1);
    expect(req.request.method).toEqual('PUT');

    req.flush(contactPersonDto);
    httpMock.verify();
  });

  function verifyContactPerson(contactPerson: ContactPerson, contactPersonDto: ContactPerson) {
    expect(contactPerson).toEqual(contactPersonDto);
  }
});
