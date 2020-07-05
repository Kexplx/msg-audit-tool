import { State, Action, StateContext, NgxsOnInit, createSelector } from '@ngxs/store';
import { patch, append, updateItem } from '@ngxs/store/operators';
import { Injectable } from '@angular/core';
import { Interview } from '../data/models/interview.model';
import { AddInterview, UpdateInterview } from './actions/inteview.actions';
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

  static interview(id: number) {
    return createSelector([InterviewState], (state: InterviewStateModel) => {
      return state.interviews.find(x => x.interviewId === id);
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
        }),
      );
    });
  }

  @Action(UpdateInterview)
  updateInterview(
    { getState, setState }: StateContext<InterviewStateModel>,
    { id, interview }: UpdateInterview,
  ) {
    const i = getState().interviews.find(x => x.interviewId === id);
    this.interviewService.putInterview({ ...i, ...interview }).subscribe(interview => {
      setState(
        patch({
          interviews: updateItem<Interview>(x => x.interviewId === id, { ...i, ...interview }),
        }),
      );
    });
  }
}
