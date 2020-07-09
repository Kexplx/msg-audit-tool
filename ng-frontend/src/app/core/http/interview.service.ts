import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { InterviewDto } from './dtos/interview.dto';
import { map, tap } from 'rxjs/operators';
import { Interview } from '../data/models/interview.model';
import { PostInterviewDto } from './dtos/post-interview.dto';
import { FacCrit } from '../data/models/faccrit.model';
import { Question } from '../data/models/question.model';
import { Answer } from '../data/models/answer.model';
import { PutInterviewDto } from './dtos/put-interview.dto';
import { parseTimestamp } from 'src/app/shared/helpers/date-helpers';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class InterviewService {
  constructor(private http: HttpClient) {}

  /**
   * Builds an observable for making a GET request to get an interview.
   *
   * @param id The interview's id.
   * @returns An Observable of the interview.
   */
  getInterview(id: number): Observable<Interview> {
    const url = environment.baseUrl + 'interviews/' + id;

    return this.http.get<InterviewDto>(url).pipe(
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

  /**
   * Builds an observable for making a GET request to get all interviews.
   *
   * @returns An Observable of the interviews.
   */
  getInterviews(): Observable<Interview[]> {
    const url = environment.baseUrl + 'interviews';

    return this.http.get<InterviewDto[]>(url).pipe(
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

  /**
   * Builds an observable for making a GET request to get all interviews by an audit id.
   *
   * @param id The audit's id.
   * @returns An Observable of the interviews.
   */
  getInterviewsByAuditId(auditId: number): Observable<Interview[]> {
    const url = environment.baseUrl + 'audits/' + auditId + '/interviews';

    return this.http.get<InterviewDto[]>(url).pipe(
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

  /**
   * Builds an observable for making a POST request to create an interview.
   *
   * @param interview The interview to create.
   * @param interviewScope The interviews scope (List of assigned fac crits).
   * @returns An Observable of the created interview.
   */
  postInterview(
    { contactPersons, startDate, auditId }: Interview,
    interviewScope: FacCrit[],
  ): Observable<Interview> {
    const url = environment.baseUrl + 'interviews';
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

    return this.http.post<InterviewDto>(url, postInterviewDto).pipe(
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

  /**
   * Builds an observable for making a PUT request to update an interview.
   *
   * @param interview The interview to update.
   * @returns An Observable of the updated interview.
   */
  putInterview({ startDate, endDate, status, goal, id }: Interview): Observable<Interview> {
    const url = environment.baseUrl + 'interviews/' + id;

    const putInterviewDto: PutInterviewDto = {
      endDate: parseTimestamp(endDate),
      startDate: parseTimestamp(startDate),
      goal,
      status,
    };

    return this.http.put<InterviewDto>(url, putInterviewDto).pipe(
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

  /**
   * Builds an observable for making a GET request to get a question.
   *
   * @param id The question's id.
   * @returns An Observable of the question.
   */
  getQuestion(id: number): Observable<Question> {
    const url = environment.baseUrl + 'questions/' + id;

    return this.http.get<Question>(url).pipe(tap(q => console.log(q)));
  }

  /**
   * Builds an observable for making a GET request to get answers by their interview id.
   *
   * @param id The interviews's id.
   * @returns An Observable of the answers.
   */
  getAnswersByInterviewId(interviewId: number): Observable<Answer[]> {
    const url = environment.baseUrl + 'answers/' + 'interview/' + interviewId;
    return this.http.get<Answer[]>(url);
  }

  /**
   * Builds an observable for making a POST request to update an answer.
   *
   * @param answer The updated answer.
   * @returns An Observable of the answers.
   */
  putAnswer(answer: Answer): Observable<Answer> {
    const url =
      environment.baseUrl +
      'answers/' +
      'interview/' +
      answer.interviewId +
      '/question/' +
      answer.questionId;

    return this.http.put<Answer>(url, answer);
  }
}
