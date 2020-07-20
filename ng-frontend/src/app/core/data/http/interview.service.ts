import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { InterviewResponse } from './response-models/interview.response';
import { map } from 'rxjs/operators';
import { Interview } from '../models/interview.model';
import { InterviewPostRequest } from './request-models/interview-post.request';
import { FacCrit } from '../models/faccrit.model';
import { InterviewPutRequest } from './request-models/interview-put.request';
import { parseTimestamp } from 'src/app/core/data/helpers/date-helpers';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class InterviewService {
  constructor(private http: HttpClient) {}

  /**
   * Builds an observable for making a GET request to get all interviews.
   *
   * @returns An Observable of the interviews.
   */
  getInterviews(): Observable<Interview[]> {
    const url = environment.baseUrl + 'interviews';

    return this.http.get<InterviewResponse[]>(url).pipe(
      map<InterviewResponse[], Interview[]>(interviewDtos => {
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

    return this.http.get<InterviewResponse[]>(url).pipe(
      map<InterviewResponse[], Interview[]>(interviewDtos => {
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
   * Builds an observable for making a GET request to get an interview.
   *
   * @param id The interview's id.
   * @returns An Observable of the interview.
   */
  getInterview(id: number): Observable<Interview> {
    const url = environment.baseUrl + 'interviews/' + id;

    return this.http.get<InterviewResponse>(url).pipe(
      map<InterviewResponse, Interview>(interviewDto => {
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

    const postInterviewDto: InterviewPostRequest = {
      auditId,
      note: '',
      startDate: parseTimestamp(startDate),
      interviewScope: interviewScopeDto,
      interviewedContactPersons: interviewedContactPersonsDto,
    };

    return this.http.post<InterviewResponse>(url, postInterviewDto).pipe(
      map<InterviewResponse, Interview>(interviewDto => {
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
  putInterview({ startDate, endDate, status, note, id }: Interview): Observable<Interview> {
    const url = environment.baseUrl + 'interviews/' + id;

    const putInterviewDto: InterviewPutRequest = {
      endDate: parseTimestamp(endDate),
      startDate: parseTimestamp(startDate),
      note,
      status,
    };

    return this.http.put<InterviewResponse>(url, putInterviewDto).pipe(
      map<InterviewResponse, Interview>(interviewDto => {
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
}
