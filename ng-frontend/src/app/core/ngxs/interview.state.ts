import { State, Action, StateContext, createSelector } from '@ngxs/store';
import { patch, append, updateItem } from '@ngxs/store/operators';
import { Injectable } from '@angular/core';
import * as shortid from 'shortid';

import { Interview } from '../data/models/interview.model';
import { AddInterview, UpdateInterview } from './actions/inteview.actions';
import { INTERVIEWS } from '../data/examples/interviews';
import { getId } from './audit.state';

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
export class InterviewState {
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
  addInterview({ setState }: StateContext<InterviewStateModel>, { interview }: AddInterview) {
    setState(
      patch({
        interviews: append<Interview>([{ ...interview, id: getId() }]),
      }),
    );
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
