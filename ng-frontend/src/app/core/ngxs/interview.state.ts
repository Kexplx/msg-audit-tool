import { State, Action, StateContext, NgxsOnInit, createSelector, Selector } from '@ngxs/store';
import { patch, append, updateItem } from '@ngxs/store/operators';
import { Injectable } from '@angular/core';
import { Interview } from '../data/models/interview.model';
import { AddInterview, UpdateInterview, UpdateAnswer } from './actions/inteview.actions';
import { InterviewService } from '../http/interview.service';
import { Answer } from '../data/models/answer.model';
import { Question } from '../data/models/question.model';
import { forkJoin } from 'rxjs';

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
      const answers: Answer[] = [].concat.apply(
        [],
        interviews.map(i => i.answers),
      );

      const questions$ = [];
      for (const answer of answers) {
        questions$.push(this.interviewService.getQuestion(answer.questionId));
      }

      forkJoin([...questions$]).subscribe((questions: Question[]) => {
        const distinctQuestions: Question[] = [];

        for (const question of questions) {
          const included = distinctQuestions.find(q => q.id === question.id);
          if (!included) {
            distinctQuestions.push(question);
          }
        }

        patchState({
          interviews,
          answers,
          questions: distinctQuestions,
        });
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
    { setState, getState }: StateContext<InterviewStateModel>,
    { interview, interviewScope }: AddInterview,
  ) {
    this.interviewService.postInterview(interview, interviewScope).subscribe(interview => {
      setState(
        patch({
          interviews: append<Interview>([interview]),
          answers: append<Answer>([...interview.answers]),
        }),
      );

      const questions = getState().questions;

      for (const answer of interview.answers) {
        if (!questions?.find(q => q.id === answer.questionId)) {
          this.interviewService.getQuestion(answer.questionId).subscribe(question => {
            setState(
              patch({
                questions: append<Question>([question]),
              }),
            );
          });
        }
      }
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
