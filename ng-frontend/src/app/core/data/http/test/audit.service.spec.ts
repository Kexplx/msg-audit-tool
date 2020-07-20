import { AuditService } from '../audit.service';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuditStatus, Audit } from '../../models/audit.model';
import { AuditResponse } from '../response-models/audit.response';
import { AUDITS_RESPONSE } from './dummies/responses/audits-response';
import { environment } from 'src/environments/environment';

describe('AuditService', () => {
  let service: AuditService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuditService],
    });

    service = TestBed.inject(AuditService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('#getAudits should return a piped array of auditDtos', () => {
    const auditResponse0 = AUDITS_RESPONSE[0];
    const auditResponse1 = AUDITS_RESPONSE[1];

    service.getAudits().subscribe(audits => {
      verifyAuditContent(audits[0], auditResponse0);
      verifyAuditContent(audits[1], auditResponse1);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'audits');
    expect(req.request.method).toEqual('GET');

    req.flush([auditResponse0, auditResponse1]);

    httpMock.verify();
  });

  it('#getAudit should return a piped auditDto', () => {
    const auditResponse = AUDITS_RESPONSE[0];

    service.getAudit(auditResponse.id).subscribe(audit => {
      verifyAuditContent(audit, auditResponse);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'audits/' + auditResponse.id);
    expect(req.request.method).toEqual('GET');

    req.flush(auditResponse);

    httpMock.verify();
  });

  it('#postAudit should return a newly created audit', () => {
    const auditResponse: AuditResponse = AUDITS_RESPONSE[0];
    const auditPostRequest: Audit = {
      name: 'Test',
      status: AuditStatus.Active,
      scope: auditResponse.scope,
      startDate: new Date(auditResponse.startDate).getTime(),
      endDate: new Date(auditResponse.startDate).getTime(),
      contactPersons: auditResponse.contactPersons,
    };

    service.postAudit(auditPostRequest).subscribe(audit => {
      verifyAuditContent(audit, auditResponse);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'audits');
    expect(req.request.method).toEqual('POST');

    req.flush(auditResponse);

    httpMock.verify();
  });

  it('#putAudit should return an updated audit', () => {
    const auditResponse: AuditResponse = AUDITS_RESPONSE[0];
    const auditPutRequest: Audit = {
      name: 'Test',
      status: AuditStatus.Active,
      scope: auditResponse.scope,
      startDate: new Date(auditResponse.startDate).getTime(),
      endDate: new Date(auditResponse.startDate).getTime(),
      contactPersons: auditResponse.contactPersons,
    };

    service.putAudit(auditPutRequest, auditPutRequest).subscribe(audit => {
      verifyAuditContent(audit, auditResponse);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'audits/' + auditPutRequest.id);
    expect(req.request.method).toEqual('PUT');

    req.flush(auditResponse);
    httpMock.verify();
  });

  function verifyAuditContent(audit: Audit, auditDto: AuditResponse) {
    const { id, status, endDate, startDate, contactPersons, creationDate, scope } = audit;
    expect(id).toEqual(auditDto.id);
    expect(creationDate).toEqual(new Date(auditDto.creationDate).getTime());
    expect(endDate).toEqual(new Date(auditDto.endDate).getTime());
    expect(startDate).toEqual(new Date(auditDto.startDate).getTime());
    expect(status).toEqual(auditDto.status);
    expect(contactPersons).toEqual(auditDto.contactPersons);
    expect(scope).toEqual(auditDto.scope);
  }
});
