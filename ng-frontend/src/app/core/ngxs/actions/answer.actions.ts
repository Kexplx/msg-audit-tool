import { Answer } from '../../data/models/answer.model';

export class AddAnswer {
  static readonly type = '[Interview] Add Answer';
  constructor(public answer: Answer) {}
}

export class UpdateAnswer {
  static readonly type = '[Interview] Update Answer';
  constructor(public answer: Answer) {}
}
