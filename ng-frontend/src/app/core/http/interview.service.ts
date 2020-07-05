import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { compileTimeSwitchedString } from './connectionStrings';
import { InterviewDto } from './dtos/interview.dto';
import { map } from 'rxjs/operators';
import { Interview } from '../data/models/interview.model';
import { PostInterviewDto } from './dtos/post-interview.dto';
import { FacCrit } from '../data/models/faccrit.model';
import { parseTimestamp } from './helpers';
import { Question } from '../data/models/question.model';
import { Answer } from '../data/models/answer.model';
import { PutInterviewDto } from './dtos/put-interview.dto';

@Injectable({
  providedIn: 'root',
})
export class InterviewService {
  constructor(private http: HttpClient) {}

  getInterview(id: number) {
    return this.http.get<InterviewDto>(compileTimeSwitchedString + 'interviews/' + id).pipe(
      map<InterviewDto, Interview>(interviewDto => {
        const { endDate, startDate } = interviewDto;
        return {
          ...interviewDto,
          contactPersons: interviewDto.interviewedContactPersons,
          endDate: new Date(endDate).getTime(),
          startDate: new Date(startDate).getTime(),
        };
      }),
    );
  }

  getInterviews() {
    return this.http.get<InterviewDto[]>(compileTimeSwitchedString + 'interviews').pipe(
      map<InterviewDto[], Interview[]>(interviewDtos => {
        return interviewDtos.map(interviewDto => {
          const { endDate, startDate } = interviewDto;
          return {
            ...interviewDto,
            contactPersons: interviewDto.interviewedContactPersons,
            endDate: new Date(endDate).getTime(),
            startDate: new Date(startDate).getTime(),
          };
        });
      }),
    );
  }

  getInterviewsByAuditId(auditId: number) {
    return this.http
      .get<InterviewDto[]>(compileTimeSwitchedString + 'audits/' + auditId + '/interviews')
      .pipe(
        map<InterviewDto[], Interview[]>(interviewDtos => {
          return interviewDtos.map(interviewDto => {
            const { endDate, startDate } = interviewDto;
            return {
              ...interviewDto,
              contactPersons: interviewDto.interviewedContactPersons,
              endDate: new Date(endDate).getTime(),
              startDate: new Date(startDate).getTime(),
            };
          });
        }),
      );
  }

  postInterview({ contactPersons, startDate, auditId }: Interview, interviewScope: FacCrit[]) {
    const interviewedContactPersonsDto: { id: number; role: string }[] = [];

    for (const iterator of contactPersons ?? []) {
      interviewedContactPersonsDto.push({ id: iterator.id, role: 'Default Role' });
    }

    const interviewScopeDto = interviewScope?.map(facCrit => facCrit.id);

    const postInterviewDto: PostInterviewDto = {
      auditId,
      goal: '',
      startDate: parseTimestamp(startDate),
      interviewScope: interviewScopeDto,
      interviewedContactPersons: interviewedContactPersonsDto,
    };

    return this.http
      .post<InterviewDto>(compileTimeSwitchedString + 'interviews', postInterviewDto)
      .pipe(
        map<InterviewDto, Interview>(interviewDto => {
          const { endDate, startDate } = interviewDto;

          return {
            ...interviewDto,
            contactPersons: interviewDto.interviewedContactPersons,
            endDate: new Date(endDate).getTime(),
            startDate: new Date(startDate).getTime(),
          };
        }),
      );
  }

  putInterview({ startDate, endDate, status, goal, interviewId }: Interview) {
    const putInterviewDto: PutInterviewDto = {
      endDate: parseTimestamp(endDate),
      startDate: parseTimestamp(startDate),
      goal,
      status,
    };

    return this.http
      .put<InterviewDto>(compileTimeSwitchedString + 'interviews/' + interviewId, putInterviewDto)
      .pipe(
        map<InterviewDto, Interview>(interviewDto => {
          const { endDate, startDate } = interviewDto;

          return {
            ...interviewDto,
            contactPersons: interviewDto.interviewedContactPersons,
            endDate: new Date(endDate).getTime(),
            startDate: new Date(startDate).getTime(),
          };
        }),
      );
  }

  getQuestion(id: number) {
    return this.http.get<Question>(compileTimeSwitchedString + 'questions/' + id);
  }

  getAnswersByInterviewId(interviewId: number) {
    return this.http.get<Answer[]>(
      compileTimeSwitchedString + 'answers/' + 'interview/' + interviewId,
    );
  }

  putAnswer(answer: Answer) {
    const url =
      compileTimeSwitchedString +
      'answers/' +
      'interview/' +
      answer.interviewId +
      '/question/' +
      answer.questionId;

    return this.http.put<Answer>(url, answer);
  }
}
