import { Interview } from '../../data/models/interview.model';
import { FacCrit } from '../../data/models/faccrit.model';

export class AddInterview {
  static readonly type = '[AuditOverview] Add Interview';
  constructor(public interview: Interview, public interviewScope: FacCrit[]) {}
}

export class UpdateInterview {
  static readonly type = '[AuditOverview] Update Interview';
  constructor(public id: number, public interview: Partial<Interview>) {}
}
