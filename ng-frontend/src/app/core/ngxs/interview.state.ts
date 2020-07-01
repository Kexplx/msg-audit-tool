import { State, Action, StateContext, createSelector, NgxsOnInit } from '@ngxs/store';
import { patch, append, updateItem } from '@ngxs/store/operators';
import { Injectable } from '@angular/core';

import { Interview } from '../data/models/interview.model';
import { AddInterview, UpdateInterview } from './actions/inteview.actions';
import { getId } from './audit.state';
import { InterviewService } from '../http/interview.service';

export interface InterviewStateModel {
  interviews: Interview[];
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
      patchState({ interviews });
    });
  }

  static interviewsByAuditId(auditId: number) {
    return createSelector([InterviewState], (state: InterviewStateModel) => {
      return state.interviews.filter(x => x.auditId === auditId);
    });
  }

  static interview(id: number) {
    return createSelector([InterviewState], (state: InterviewStateModel) => {
      return state.interviews.find(x => x.id === id);
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
        }),
      );
    });
  }

  @Action(UpdateInterview)
  updateInterview(ctx: StateContext<InterviewStateModel>, { id, interview }: UpdateInterview) {
    const i = ctx.getState().interviews.find(x => x.id === id);
    ctx.setState(
      patch({
        interviews: updateItem<Interview>(x => x === i, { ...interview }),
      }),
    );
  }
}
