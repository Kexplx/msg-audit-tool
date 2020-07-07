import { State, Action, StateContext, NgxsOnInit, createSelector, Selector } from '@ngxs/store';
import { patch, append, updateItem } from '@ngxs/store/operators';
import { Injectable } from '@angular/core';
import { Interview } from '../data/models/interview.model';
import { AddInterview, UpdateInterview, UpdateAnswer } from './actions/inteview.actions';
import { InterviewService } from '../http/interview.service';
import { Answer } from '../data/models/answer.model';

export interface InterviewStateModel {
  interviews: Interview[];
  answers: Answer[];
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
}
