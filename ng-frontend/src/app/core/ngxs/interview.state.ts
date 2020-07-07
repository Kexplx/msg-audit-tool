import { State, Action, StateContext, NgxsOnInit, createSelector, Selector } from '@ngxs/store';
import { patch, append, updateItem } from '@ngxs/store/operators';
import { Injectable } from '@angular/core';
import { Interview } from '../data/models/interview.model';
import {
  AddInterview,
  UpdateInterview,
  UpdateAnswer,
  LoadQuestion,
} from './actions/inteview.actions';
import { InterviewService } from '../http/interview.service';
import { Answer } from '../data/models/answer.model';
import { Question } from '../data/models/question.model';
import { tap } from 'rxjs/operators';

export interface InterviewStateModel {
  interviews: Interview[];
  answers: Answer[];
  questions: Question[];
}

/**
 * State for managing the interviews of the application.
 *
 * Has: Action handlers to read, write, update and delete a contact person.
 * Static and dynamic selectors to select interviews.
 */
@State<InterviewStateModel>({
  name: 'interviewState',
})
@Injectable()
export class InterviewState implements NgxsOnInit {
  constructor(private interviewService: InterviewService) {}

  ngxsOnInit({ patchState }: StateContext<InterviewStateModel>) {
    this.interviewService.getInterviews().subscribe(interviews => {
      console.log('HELLO WORLD');

      patchState({
        interviews,
        answers: [].concat.apply(
          [],
          interviews.map(i => i.answers),
        ),
      });
    });
  }

  @Selector()
  static answers(state: InterviewStateModel) {
    return state.answers ?? [];
  }

  @Selector()
  static questions(state: InterviewStateModel) {
    return state.questions ?? [];
  }

  static question(id: number) {
    return createSelector([InterviewState], (state: InterviewStateModel) => {
      const result = state.questions.find(x => x.id === id);
      console.log('returning', result);
      return result;
    });
  }

  static interview(id: number) {
    return createSelector([InterviewState], (state: InterviewStateModel) => {
      return state.interviews.find(x => x.id === id);
    });
  }

  static interviewsByAuditId(auditId: number) {
    return createSelector([InterviewState], (state: InterviewStateModel) => {
      return state.interviews.filter(x => x.auditId === auditId);
    });
  }

  @Action(AddInterview)
  addInterview(
    { setState }: StateContext<InterviewStateModel>,
    { interview, interviewScope }: AddInterview,
  ) {
    this.interviewService.postInterview(interview, interviewScope).subscribe(interview => {
      setState(
        patch({
          interviews: append<Interview>([interview]),
          answers: append<Answer>([...interview.answers]),
        }),
      );
    });
  }

  @Action(UpdateInterview)
  updateInterview(
    { getState, setState }: StateContext<InterviewStateModel>,
    { id, interview }: UpdateInterview,
  ) {
    const i = getState().interviews.find(x => x.id === id);
    this.interviewService.putInterview({ ...i, ...interview }).subscribe(interview => {
      setState(
        patch({
          interviews: updateItem<Interview>(x => x.id === id, { ...i, ...interview }),
        }),
      );
    });
  }

  @Action(UpdateAnswer)
  updateAnswer({ setState }: StateContext<InterviewStateModel>, { answer }: UpdateAnswer) {
    this.interviewService.putAnswer(answer).subscribe(answer => {
      setState(
        patch({
          answers: updateItem<Answer>(
            a =>
              a.questionId === answer.questionId &&
              a.faccritId === answer.faccritId &&
              a.interviewId === answer.interviewId,
            answer,
          ),
        }),
      );
    });
  }

  @Action(LoadQuestion)
  loadQuestion({ setState, getState }: StateContext<InterviewStateModel>, { id }: LoadQuestion) {
    const question = getState().questions?.find(q => q.id === id);
    if (!question) {
      this.interviewService.getQuestion(id).subscribe(question => {
        setState(
          patch({
            questions: append<Question>([question]),
          }),
        );
      });
    }
  }
}
