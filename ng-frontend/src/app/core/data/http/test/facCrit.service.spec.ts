import { TestBed } from '@angular/core/testing';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { FacCrit } from '../../models/faccrit.model';
import { FACCRITS } from './dummies/app-models/faccrits';
import { environment } from 'src/environments/environment';
import { FacCritService } from '../facCrit.service';
import * as jasmine from 'karma-jasmine';

describe('FacCritService', () => {
  let service: FacCritService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [FacCritService],
    });

    service = TestBed.inject(FacCritService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('#getFacCrits should return facCrits', () => {
    const facCritsDto: FacCrit[] = FACCRITS;

    service.getFacCrits().subscribe(facCrits => {
      expect(facCrits.length).toBe(facCritsDto.length);

      for (const [i, facCrit] of facCrits.entries()) {
        expect(facCrit).toEqual(facCritsDto[i]);
      }
    });

    const req = httpMock.expectOne(environment.baseUrl + 'faccrits');
    expect(req.request.method).toEqual('GET');

    req.flush(facCritsDto);
    httpMock.verify();
  });

  it('#getFacCritsByInterviewId should return facCrits', () => {
    const facCritsDto: FacCrit[] = FACCRITS.slice(0, 20);

    service.getFacCritsByInterviewId(21).subscribe(facCrits => {
      expect(facCrits.length).toBe(facCritsDto.length);

      for (const [i, facCrit] of facCrits.entries()) {
        expect(facCrit).toEqual(facCritsDto[i]);
      }
    });

    const req = httpMock.expectOne(environment.baseUrl + 'faccrits/interview/' + 21);
    expect(req.request.method).toEqual('GET');

    req.flush(facCritsDto);
    httpMock.verify();
  });
});
