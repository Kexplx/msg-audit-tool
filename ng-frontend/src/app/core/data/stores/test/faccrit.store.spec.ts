import { FacCritStore } from '../faccrit.store';
import { FacCritService } from '../../http/facCrit.service';
import { FACCRITS } from '../../http/test/dummies/app-models/faccrits';
import { of } from 'rxjs';
import { FacCrit } from '../../models/faccrit.model';
import { filter } from 'rxjs/operators';

describe('FacCritStore', () => {
  let facCritStore: FacCritStore;
  let facCritServiceSpy: jasmine.SpyObj<FacCritService>;

  beforeEach(() => {
    facCritServiceSpy = jasmine.createSpyObj(['getFacCrits']);
    facCritServiceSpy.getFacCrits.and.returnValue(of(FACCRITS));

    facCritStore = new FacCritStore(facCritServiceSpy);
  });

  describe('loadFacCrits', () => {
    let facCritsResponse: FacCrit[];
    beforeEach(() => {
      facCritsResponse = FACCRITS;
    });

    it('should call #getFacCrits once', () => {
      facCritStore.loadFacCrits();

      expect(facCritServiceSpy.getFacCrits.calls.count()).toEqual(1);
    });

    it('should set _facCrits$.value to the response', () => {
      facCritStore.facCrits$.pipe(filter(facCrits => facCrits != null)).subscribe(facCrits => {
        expect(facCrits).toEqual(facCritsResponse);
      });

      facCritStore.loadFacCrits();
    });
  });
});
