import { State, Action, StateContext, NgxsOnInit, createSelector, Selector } from '@ngxs/store';
import { patch, append, updateItem } from '@ngxs/store/operators';
import { Injectable } from '@angular/core';
import { Interview } from '../data/models/interview.model';
import { AddInterview, UpdateInterview, UpdateAnswer } from './actions/inteview.actions';
import { InterviewService } from '../http/interview.service';
import { QuestionService } from '../http/question.service';
import { Answer } from '../data/models/answer.model';
import { Question } from '../data/models/question.model';
import { forkJoin } from 'rxjs';
import { AnswerService } from '../http/answer.service';

export interface InterviewStateModel {
  interviews: Interview[];
  answers: Answer[];
  questions: Question[];
}

/**
 * State for managing interviews with their answers and questions.
 *
 * A AnswerState or QuestionState wasn't created since the answers and questions
 * are contained in the response of every request to ../interviews.
 */
@State<InterviewStateModel>({
  name: 'interviewState',
})
@Injectable()
export class InterviewState implements NgxsOnInit {
  constructor(
    private interviewService: InterviewService,
    private questionService: QuestionService,
    private answerService: AnswerService,
  ) {}

  /**
   * Calls #getInterviews to load all interviews.
   * Calls #getQuestion for every distinct question id in the interviews answers.
   * Patches the state with interviews, answers and questions.
   */
  ngxsOnInit({ patchState }: StateContext<InterviewStateModel>) {
    this.interviewService.getInterviews().subscribe(interviews => {
      // Turn [Answer[]] into Answer[].
      const answers: Answer[] = [].concat.apply(
        [],
        interviews.map(i => i.answers),
      );

      // Get distinct question ids.
      const distinctQuestionIds: number[] = [];
      for (const id of answers.map(a => a.questionId)) {
        const isDistinct = !distinctQuestionIds.find(q => q === id);
        if (isDistinct) {
          distinctQuestionIds.push(id);
        }
      }

      // Push observable of all distinct questions into array.
      const questions$ = [];
      for (const id of distinctQuestionIds) {
        questions$.push(this.questionService.getQuestion(id));
      }

      // Wait until all questions emit and subscribe to result.
      forkJoin([...questions$]).subscribe((questions: Question[]) => {
        patchState({
          interviews,
          answers,
          questions,
        });
      });
    });
  }

  @Selector()
  static interviews(state: InterviewStateModel) {
    return state.interviews ?? [];
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
      return state.interviews.filter(i => i.auditId === auditId);
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
          this.questionService.getQuestion(answer.questionId).subscribe(question => {
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
    this.answerService.putAnswer(answer).subscribe(answer => {
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
