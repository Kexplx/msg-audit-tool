import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { compileTimeSwitchedString } from '../connectionStrings';
import * as karma from 'karma-jasmine';
import { ContactPersonService } from '../contact-person.service';

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

  // get contact persons
  // get contact person
  // post contact person
  // put contact person
});
