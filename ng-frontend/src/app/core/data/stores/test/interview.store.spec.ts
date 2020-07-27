import { of } from 'rxjs';
import { filter, skip } from 'rxjs/operators';
import { StoreActionService } from '../store-action.service';
import { InterviewStore } from '../interview.store';
import { InterviewService } from '../../http/interview.service';
import { INTERVIEWS } from '../../http/test/dummies/app-models/interviews';
import { Interview } from '../../models/interview.model';

describe('InterviewStore', () => {
  let interviewStore: InterviewStore;
  let interviewServiceSpy: jasmine.SpyObj<InterviewService>;
  let storeActionServiceSpy: jasmine.SpyObj<StoreActionService>;

  beforeEach(() => {
    interviewServiceSpy = jasmine.createSpyObj<InterviewService>([
      'getInterviewsByAuditId',
      'postInterview',
      'putInterview',
    ]);
    storeActionServiceSpy = jasmine.createSpyObj<StoreActionService>([
      'notifyLoad',
      'notifyAdd',
      'notifyEdit',
    ]);

    interviewStore = new InterviewStore(interviewServiceSpy, storeActionServiceSpy);
  });

  describe('#loadAudits', () => {
    beforeEach(() => {
      interviewServiceSpy.getInterviewsByAuditId.and.returnValue(of(INTERVIEWS));
    });

    it('should call #getInterviewsByAuditId once', () => {
      interviewStore.loadInterviewsByAuditId(1);

      expect(interviewServiceSpy.getInterviewsByAuditId.calls.count()).toEqual(1);
    });

    it('should set _interviews$.value to the response', () => {
      interviewStore.interviews$.pipe(filter(audits => audits != null)).subscribe(audits => {
        expect(audits).toEqual(INTERVIEWS);
      });

      interviewStore.loadInterviewsByAuditId(1);
    });

    it('should call #notifyLoad once', () => {
      interviewStore.loadInterviewsByAuditId(1);

      expect(storeActionServiceSpy.notifyLoad.calls.count()).toEqual(1);
    });
  });

  describe('#addInterview', () => {
    let interviewResponse: Interview;

    beforeEach(() => {
      interviewResponse = INTERVIEWS[0];
      interviewServiceSpy.postInterview.and.returnValue(of(interviewResponse));
    });

    it('should call #postAudit', () => {
      interviewStore.addInterview({} as Interview, []);

      expect(interviewServiceSpy.postInterview.calls.count()).toEqual(1);
    });

    it('should add the added audit to _audits$.value', () => {
      interviewStore.interviews$
        .pipe(filter(interviews => interviews != null))
        .subscribe(interviews => {
          expect(interviews[0]).toEqual(interviewResponse);
        });

      interviewStore.addInterview({} as Interview, []);
    });

    it('should call #notifyAdd once', () => {
      interviewStore.addInterview({} as Interview, []);

      expect(storeActionServiceSpy.notifyAdd.calls.count()).toEqual(1);
    });
  });

  describe('#updateInterview', () => {
    let interviewResponse: Interview;

    beforeEach(() => {
      interviewResponse = INTERVIEWS[0];

      interviewStore['_interviews$'].next([{ ...interviewResponse, note: 'OLD VALUE' }]);
      interviewServiceSpy.putInterview.and.returnValue(of(interviewResponse));
    });

    it('should call #postAudit once', () => {
      interviewStore.updateInterview({} as Interview);

      expect(interviewServiceSpy.putInterview.calls.count()).toEqual(1);
    });

    it("should update the audit's name at index 0", () => {
      interviewStore.interviews$.pipe(skip(1)).subscribe(interviews => {
        expect(interviews[0].note).not.toEqual('OLD VALUE');
      });

      interviewStore.updateInterview({} as Interview);
    });

    it('should call #notifyEdit once', () => {
      interviewStore.updateInterview({} as Interview);

      expect(storeActionServiceSpy.notifyEdit.calls.count()).toEqual(1);
    });
  });
});
