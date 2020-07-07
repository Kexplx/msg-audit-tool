import { Interview } from '../../data/models/interview.model';
import { FacCrit } from '../../data/models/faccrit.model';
import { Answer } from '../../data/models/answer.model';

export class AddInterview {
  static readonly type = '[AuditOverview] Add Interview';
  constructor(public interview: Interview, public interviewScope: FacCrit[]) {}
}

export class UpdateInterview {
  static readonly type = '[Interview] Update Interview';
  constructor(public id: number, public interview: Partial<Interview>) {}
}

export class UpdateAnswer {
  static readonly type = '[Interview] Update Answer';
  constructor(public answer: Answer) {}
}
