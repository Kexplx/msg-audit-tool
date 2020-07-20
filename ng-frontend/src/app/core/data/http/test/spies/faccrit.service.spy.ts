import { FacCritService } from '../../facCrit.service';
import { of } from 'rxjs';
import { FACCRITS } from '../dummies/app-models/faccrits';

export const facCritServiceSpy: jasmine.SpyObj<FacCritService> = jasmine.createSpyObj(
  'FacCritService',
  ['getFacCrits', 'getFacCritsByInterviewId'],
);

facCritServiceSpy.getFacCrits.and.returnValue(of(FACCRITS));
facCritServiceSpy.getFacCritsByInterviewId.and.returnValue(of(FACCRITS));
