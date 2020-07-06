import { CoreService } from '../core.service';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AUDITS_DTO_DUMMY } from './dummies';
import { AuditStatus, Audit } from '../../data/models/audit.model';
import { AuditDto } from '../dtos/audit.dto';

const BASE_URL = 'http://localhost:8080/';

fdescribe('CoreService', () => {
  let service: CoreService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CoreService],
    });

    service = TestBed.inject(CoreService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('#getAudits should return a piped array of auditDtos', () => {
    const auditDto0 = AUDITS_DTO_DUMMY[0];
    const auditDto1 = AUDITS_DTO_DUMMY[1];

    service.getAudits().subscribe(audits => {
      verifyAuditContent(audits[0], auditDto0);
      verifyAuditContent(audits[1], auditDto1);
    });

    const req = httpMock.expectOne(BASE_URL + 'audits');
    expect(req.request.method).toEqual('GET');

    req.flush([auditDto0, auditDto1]);

    httpMock.verify();
  });

  it('#getAudit should return a piped auditDto', () => {
    const auditDto = AUDITS_DTO_DUMMY[0];

    service.getAudit(auditDto.id).subscribe(audit => {
      verifyAuditContent(audit, auditDto);
    });

    const req = httpMock.expectOne(BASE_URL + 'audits/' + auditDto.id);
    expect(req.request.method).toEqual('GET');

    req.flush(auditDto);

    httpMock.verify();
  });

  it('#postAudit should return a newly created audit', () => {
    const auditDto: AuditDto = AUDITS_DTO_DUMMY[0];
    const auditPost: Audit = {
      name: 'Test',
      status: AuditStatus.Active,
      scope: auditDto.scope,
      startDate: new Date(auditDto.startDate).getTime(),
      endDate: new Date(auditDto.startDate).getTime(),
      contactPersons: auditDto.contactPersons,
    };

    service.postAudit(auditPost).subscribe(audit => {
      verifyAuditContent(audit, auditDto);
    });

    const req = httpMock.expectOne(BASE_URL + 'audits');
    expect(req.request.method).toEqual('POST');

    req.flush(auditDto);

    httpMock.verify();
  });

  function verifyAuditContent(audit: Audit, auditDto: AuditDto) {
    const { id, status, endDate, startDate, contactPersons, creationDate, scope } = audit;
    expect(id).toEqual(id);
    expect(creationDate).toEqual(new Date(auditDto.creationDate).getTime());
    expect(endDate).toEqual(new Date(auditDto.endDate).getTime());
    expect(startDate).toEqual(new Date(auditDto.startDate).getTime());
    expect(status).toEqual(AuditStatus.Active);
    expect(contactPersons).toEqual(auditDto.contactPersons);
    expect(scope).toEqual(auditDto.scope);
  }
});
